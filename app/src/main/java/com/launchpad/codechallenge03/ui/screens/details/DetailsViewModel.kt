package com.launchpad.codechallenge03.ui.screens.details

import androidx.lifecycle.viewModelScope
import com.launchpad.codechallenge03.repo.DB
import com.vog.triggerworld.core.ActionViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class DetailsViewModel(id: String) : ActionViewModel<DetailsViewState, DetailsAction>(DetailsViewState()) {
    private val db: DB by inject(DB::class.java)

    override fun collectAction(action: DetailsAction) {
        when (action) {
            DetailsAction.NavigateBack -> {}
            DetailsAction.DeleteAnimal -> deleteAnimal()
        }
    }

    init {
        viewModelScope.launch {
            val animal = db.animalDao().get(id)
            launchSetState { copy(animal = animal) }
        }
    }

    private fun deleteAnimal(){
        state.value.animal?.let{animal ->
            CoroutineScope(Dispatchers.IO).launch {
                db.animalDao().delete(animal.id)
                launchSetState { copy(isDeleted = true) }
            }
        }
    }

}