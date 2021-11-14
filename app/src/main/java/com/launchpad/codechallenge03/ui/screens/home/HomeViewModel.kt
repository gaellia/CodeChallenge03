package com.launchpad.codechallenge03.ui.screens.home

import androidx.lifecycle.viewModelScope
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.repo.DB
import com.launchpad.codechallenge03.core.ActionViewModel
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class HomeViewModel : ActionViewModel<HomeViewState, HomeAction>(HomeViewState()) {
    private val db: DB by inject(DB::class.java)

    override fun collectAction(action: HomeAction) {
        when (action) {
            is HomeAction.NavigateDetails -> {}
            is HomeAction.UpdateQuery -> updateQuery(action.query)
            is HomeAction.UpdateFilterSizeLarge -> updateFilterSize(createSizeSet(Animal.Size.LARGE, action.apply))
            is HomeAction.UpdateFilterSizeMedium -> updateFilterSize(createSizeSet(Animal.Size.MEDIUM, action.apply))
            is HomeAction.UpdateFilterSizeSmall -> updateFilterSize(createSizeSet(Animal.Size.SMALL, action.apply))
            is HomeAction.UpdateFilterTypeAir -> updateFilterType(createTypeSet(Animal.Type.AIR, action.apply))
            is HomeAction.UpdateFilterTypeLand -> updateFilterType(createTypeSet(Animal.Type.LAND, action.apply))
            is HomeAction.UpdateFilterTypeSea -> updateFilterType(createTypeSet(Animal.Type.SEA, action.apply))
            HomeAction.NavigateAddAnimal -> {}
        }
    }

    private fun createTypeSet(subject: Animal.Type? = null, apply: Boolean = false): Set<Animal.Type>{
        val hashSet = hashSetOf<Animal.Type>()
        if(subject != null && apply)hashSet.add(subject)
        if(subject != Animal.Type.AIR && state.value.filterTypeAir)hashSet.add(Animal.Type.AIR)
        if(subject != Animal.Type.LAND && state.value.filterTypeLand)hashSet.add(Animal.Type.LAND)
        if(subject != Animal.Type.SEA && state.value.filterTypeSea)hashSet.add(Animal.Type.SEA)
        return hashSet
    }

    private fun createSizeSet(subject: Animal.Size? = null, apply: Boolean = false): Set<Animal.Size>{
        val hashSet = hashSetOf<Animal.Size>()
        if(subject != null && apply)hashSet.add(subject)
        if(subject != Animal.Size.SMALL && state.value.filterSizeSmall)hashSet.add(Animal.Size.SMALL)
        if(subject != Animal.Size.MEDIUM && state.value.filterSizeMedium)hashSet.add(Animal.Size.MEDIUM)
        if(subject != Animal.Size.LARGE && state.value.filterSizeLarge)hashSet.add(Animal.Size.LARGE)
        return hashSet
    }

    fun refreshList() {
        viewModelScope.launch {
            val list = db.animalDao().getAll()
            launchSetState { copy(allAnimals = list) }
            applyFilters(state.value.query, createTypeSet(), createSizeSet())
        }
    }

    private fun updateQuery(v: String){
        viewModelScope.launchSetState { copy(query = v) }
        applyFilters(v, createTypeSet(), createSizeSet())
    }

    private fun updateFilterType(v: Set<Animal.Type>){
        viewModelScope.launchSetState { copy(
            filterTypeAir = v.contains(Animal.Type.AIR),
            filterTypeLand = v.contains(Animal.Type.LAND),
            filterTypeSea = v.contains(Animal.Type.SEA)
        ) }
        applyFilters(state.value.query, v, createSizeSet())
    }

    private fun updateFilterSize(v: Set<Animal.Size>){
        viewModelScope.launchSetState { copy(
            filterSizeSmall = v.contains(Animal.Size.SMALL),
            filterSizeMedium = v.contains(Animal.Size.MEDIUM),
            filterSizeLarge = v.contains(Animal.Size.LARGE)
        ) }
        applyFilters(state.value.query, createTypeSet(), v)
    }

    private fun applyFilters(
        query: String,
        filterType: Set<Animal.Type>,
        filterSize: Set<Animal.Size>
    ){
        viewModelScope.launchSetState { copy(
            visibleAnimals = allAnimals.filter { animal ->
                (query.isEmpty() || animal.name.lowercase().contains(query.lowercase())) &&
                        (filterType.isEmpty() || filterType.contains(animal.type)) &&
                        (filterSize.isEmpty() || filterSize.contains(animal.size))
            }
        ) }
    }
}