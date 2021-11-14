package com.launchpad.codechallenge03.ui.screens.home

import com.launchpad.codechallenge03.models.Animal

sealed class HomeAction {
    data class UpdateQuery(val query: String): HomeAction()
    data class NavigateDetails(val id: String): HomeAction()
    data class UpdateFilterTypeAir(val apply: Boolean): HomeAction()
    data class UpdateFilterTypeLand(val apply: Boolean): HomeAction()
    data class UpdateFilterTypeSea(val apply: Boolean): HomeAction()
    data class UpdateFilterSizeSmall(val apply: Boolean): HomeAction()
    data class UpdateFilterSizeMedium(val apply: Boolean): HomeAction()
    data class UpdateFilterSizeLarge(val apply: Boolean): HomeAction()

    object NavigateAddAnimal: HomeAction()
}