package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.onboarding.start.StartOnboardingDestination
import com.example.nyatta.ui.screens.onboarding.start.Type

object ListingTypeGraph: Navigation {
    override val route = "listing_type"
    override val title = "Start setup"
}

fun NavGraphBuilder.listingTypeGraph(navController: NavHostController) {
    navigation(
        startDestination = StartOnboardingDestination.route,
        route = ListingTypeGraph.route
    ) {
        composable(route = StartOnboardingDestination.route) {
            Type(
                navigateToNext = { navController.navigate(it) }
            )
        }
    }
}