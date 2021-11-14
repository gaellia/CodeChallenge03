package com.launchpad.codechallenge03.ui.screens.addAnimal

import com.launchpad.codechallenge03.models.Animal

data class AddAnimalViewState(
    val name: String = "",
    val type: Animal.Type = Animal.Type.AIR,
    val size: Animal.Size = Animal.Size.SMALL,
    val description: String = ""
)