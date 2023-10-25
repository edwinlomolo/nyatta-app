package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.onboarding.location.Location
import com.example.nyatta.ui.screens.onboarding.location.LocationDestination
import com.example.nyatta.ui.screens.onboarding.location.TownDestination
import com.example.nyatta.ui.screens.onboarding.location.Towns

object LocationGraph: Navigation {
    override val route = "location_graph"
    override val title = "Location"
}
fun NavGraphBuilder.locationGraph(navController: NavHostController) {
    navigation(
        startDestination = LocationDestination.route,
        route = LocationGraph.route
    ) {
        composable(route = LocationDestination.route) {
            Location(
                navigateToNext = { navController.navigate(it) },
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = TownDestination.route) {
            Towns(
                navigateUp = { navController.navigateUp() },
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
    }
}