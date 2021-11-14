package com.launchpad.codechallenge03.ui.screens.addAnimal

import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.launchpad.codechallenge03.core.ActionViewModel
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.repo.DB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent

class AddAnimalViewModel :
    ActionViewModel<AddAnimalViewState, AddAnimalAction>(AddAnimalViewState()) {
    private val db: DB by KoinJavaComponent.inject(DB::class.java)

    override fun collectAction(action: AddAnimalAction) {
        when (action) {
            is AddAnimalAction.InsertAnimal -> upsertAnimal(
                name = action.name,
                type = action.type,
                size = action.size,
                description = action.description
            )
            AddAnimalAction.NavigateBack -> { }
            is AddAnimalAction.UpdateDescription -> updateDesc(action.desc)
            is AddAnimalAction.UpdateName -> updateName(action.name)
            is AddAnimalAction.UpdateSize -> updateSize(action.size)
            is AddAnimalAction.UpdateType -> updateType(action.type)
            AddAnimalAction.ToggleTypeMenu -> toggleTypeMenu()
            AddAnimalAction.ToggleSizeMenu -> toggleSizeMenu()
        }
    }

    private fun upsertAnimal(
        name: String,
        type: Animal.Type,
        size: Animal.Size,
        description: String,
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            db.animalDao().upsert(
                Animal(
                    id = "${System.currentTimeMillis()}",
                    name = name,
                    description = description,
                    timeStamp = System.currentTimeMillis(),
                    type = type,
                    size = size
                )
            )
        }
    }

    private fun toggleTypeMenu() {
        viewModelScope.launchSetState { copy(expandedTypeMenu = !expandedTypeMenu) }
    }

    private fun toggleSizeMenu() {
        viewModelScope.launchSetState { copy(expandedSizeMenu = !expandedSizeMenu) }
    }

    private fun updateName(v: String){
        viewModelScope.launchSetState { copy(name = v) }
    }
    private fun updateType(t: Animal.Type){
        viewModelScope.launchSetState { copy(type = t) }
    }
    private fun updateSize(s: Animal.Size){
        viewModelScope.launchSetState { copy(size = s) }
    }
    private fun updateDesc(d: String){
        viewModelScope.launchSetState { copy(description = d) }
    }
}