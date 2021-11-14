package com.launchpad.codechallenge03.ui.screens.home

import com.launchpad.codechallenge03.models.Animal

data class HomeViewState(
    val allAnimals: List<Animal> = emptyList(),
    val visibleAnimals: List<Animal> = emptyList(),
    val query: String = "",
    val filterTypeAir: Boolean = false,
    val filterTypeLand: Boolean = false,
    val filterTypeSea: Boolean = false,
    val filterSizeSmall: Boolean = false,
    val filterSizeMedium: Boolean = false,
    val filterSizeLarge: Boolean = false
)