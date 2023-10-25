package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.listing.Listing
import com.example.nyatta.ui.screens.listing.ListingDetailsDestination

object ListingDetailsGraph: Navigation {
    override val route = "listing/details"
    override val title = "Listing"
}

fun NavGraphBuilder.listingDetailsGraph(navController: NavHostController) {
    navigation(
        startDestination = ListingDetailsDestination.route,
        route = ListingDetailsGraph.route
    ) {
        composable(
            route = ListingDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ListingDetailsDestination.listingIdArg) {
                type = NavType.IntType
            })
        ) {
            Listing(
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}