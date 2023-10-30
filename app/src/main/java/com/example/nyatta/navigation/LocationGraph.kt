package com.example.nyatta.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.location.Location
import com.example.nyatta.compose.location.LocationDestination
import com.example.nyatta.compose.location.TownDestination
import com.example.nyatta.compose.location.Towns
import com.example.nyatta.viewmodels.TownsViewModel

object LocationGraph: Navigation {
    override val route = "location_graph"
    override val title = "Location"
}
fun NavGraphBuilder.locationGraph(
    onboardingViewModel: OnboardingViewModel,
    townsViewModel: TownsViewModel,
    navController: NavHostController
) {
    navigation(
        startDestination = LocationDestination.route,
        route = LocationGraph.route
    ) {
        composable(route = LocationDestination.route) {
            Location(
                navigateToNext = { navController.navigate(it) },
                navigateUp = { navController.navigateUp() },
                onboardingViewModel = onboardingViewModel
            )
        }
        composable(route = TownDestination.route) {
            Towns(
                townsViewModel = townsViewModel,
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
    }
}