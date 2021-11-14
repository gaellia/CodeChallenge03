package com.launchpad.codechallenge03.ui.screens.addAnimal

import com.launchpad.codechallenge03.core.ActionViewModel
import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.repo.DB
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
                description = action.description,
                timeStamp = action.timeStamp)
            AddAnimalAction.NavigateBack -> { }
        }
    }

    private fun upsertAnimal(
        name: String,
        type: Animal.Type,
        size: Animal.Size,
        description: String,
        timeStamp: Long
    ) {

    }
}