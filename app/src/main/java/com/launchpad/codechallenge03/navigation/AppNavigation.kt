package com.launchpad.codechallenge03.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.launchpad.codechallenge03.ui.screens.addAnimal.AddAnimal
import com.launchpad.codechallenge03.ui.screens.details.Details
import com.launchpad.codechallenge03.ui.screens.home.Home

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Destination.Home.route){

        //HOME
        composable(route = Destination.Home.route){
            Home(
                navigateDetails = {id -> navController.navigate(Destination.Details.getFormatRoute().format(id))},
                navigateAddAnimal = {navController.navigate(Destination.AddAnimal.route)}
            )
        }

        //DETAILS
        composable(
            route = Destination.Details.route,
            arguments = Destination.Details.navArguments()
        ){
            Details(
                id = it.arguments?.getString(Destination.Details.id.name)?:"",
                navigateBack = {navController.popBackStack()}
            )
        }

        //ADD ANIMAL
        composable(route = Destination.AddAnimal.route) {
            AddAnimal(
                navigateBack = {navController.popBackStack()}
            )
        }

    }
}