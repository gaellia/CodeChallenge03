package com.launchpad.codechallenge03.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument

internal sealed class Destination(val route: String){

    open fun getFormatRoute() = route
    open fun navArguments() = emptyList<NamedNavArgument>()

    object Home: Destination("home")
    object Details: Destination("details/{id}"){
        val id = navArgument("id"){type = NavType.StringType}
        override fun getFormatRoute() = "details/%s"
        override fun navArguments() = listOf(id)
    }

}
