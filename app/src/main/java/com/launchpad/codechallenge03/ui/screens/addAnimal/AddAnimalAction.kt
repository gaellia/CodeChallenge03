package com.launchpad.codechallenge03.ui.screens.addAnimal

import com.launchpad.codechallenge03.models.Animal
import com.launchpad.codechallenge03.ui.screens.details.DetailsAction

sealed class AddAnimalAction {
    object NavigateBack: AddAnimalAction()
    data class InsertAnimal(
        val name: String,
        val type: Animal.Type,
        val size: Animal.Size,
        val description: String
    ): AddAnimalAction()

    data class UpdateName(val name: String): AddAnimalAction()
    data class UpdateType(val type: Animal.Type): AddAnimalAction()
    data class UpdateSize(val size: Animal.Size): AddAnimalAction()
    data class UpdateDescription(val desc: String): AddAnimalAction()

    object ToggleSizeMenu: AddAnimalAction()
    object ToggleTypeMenu: AddAnimalAction()
}