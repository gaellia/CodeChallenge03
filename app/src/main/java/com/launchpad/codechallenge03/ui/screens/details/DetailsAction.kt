package com.launchpad.codechallenge03.ui.screens.details

sealed class DetailsAction {
    object NavigateBack: DetailsAction()
    object DeleteAnimal: DetailsAction()
}