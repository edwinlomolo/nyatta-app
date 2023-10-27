package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.property.Caretaker
import com.example.nyatta.ui.screens.property.CaretakerDestination
import com.example.nyatta.ui.screens.property.PropertyDescription
import com.example.nyatta.ui.screens.property.PropertyDescriptionDestination

object PropertyOnboardingGraph: Navigation {
    override val route = "onboarding/property"
    override val title = "Setup property"
}

fun NavGraphBuilder.propertyGraph(navController: NavHostController) {
    navigation(
        startDestination = PropertyDescriptionDestination.route,
        route = PropertyOnboardingGraph.route
    ) {
        composable(route = PropertyDescriptionDestination.route) {
            PropertyDescription(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = CaretakerDestination.route) {
            Caretaker(
                navigateNext = { navController.navigate(it) },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}