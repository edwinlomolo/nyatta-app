package com.example.nyatta.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.compose.property.Caretaker
import com.example.nyatta.compose.property.CaretakerDestination
import com.example.nyatta.compose.property.PropertyDescription
import com.example.nyatta.compose.property.PropertyDescriptionDestination
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyOnboarding: Navigation {
    override val route = "onboarding/property"
    override val title = "Setup property"
}

fun NavGraphBuilder.propertyOnboardingGraph(
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
) {
    navigation(
        startDestination = PropertyDescriptionDestination.route,
        route = PropertyOnboarding.route
    ) {
        composable(route = PropertyDescriptionDestination.route) {
            PropertyDescription(
                propertyViewModel = propertyViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = CaretakerDestination.route) {
            Caretaker(
                propertyViewModel = propertyViewModel,
                navigateNext = { navController.navigate(it) },
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}