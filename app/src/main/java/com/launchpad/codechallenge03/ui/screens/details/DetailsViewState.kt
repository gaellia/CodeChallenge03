package com.launchpad.codechallenge03.ui.screens.details

import com.launchpad.codechallenge03.models.Animal

data class DetailsViewState(
    val animal: Animal? = null,
    val isDeleted: Boolean = false
)