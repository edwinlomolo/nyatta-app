package com.example.nyatta.compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nyatta.compose.listing.Listing
import com.example.nyatta.compose.listing.ListingDetailsDestination
import com.example.nyatta.compose.listing.ListingPhoto
import com.example.nyatta.compose.listing.ListingPhotosDestination

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
                navigateUp = { navController.navigateUp() },
                onNavigateToPhotos = { navController.navigate(it) }
            )
        }
        composable(route = ListingPhotosDestination.route) {
            ListingPhoto(
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}