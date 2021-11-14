package com.launchpad.codechallenge03.ui.screens.addAnimal

import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.ui.screens.details.DetailsAction

sealed class AddAnimalAction {
    object NavigateBack: AddAnimalAction()
    data class InsertAnimal(
        val name: String,
        val type: Animal.Type,
        val size: Animal.Size,
        val description: String,
        val timeStamp: Long
    ): AddAnimalAction()
}