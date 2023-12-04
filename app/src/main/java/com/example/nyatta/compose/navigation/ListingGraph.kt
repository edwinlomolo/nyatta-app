package com.example.nyatta.compose.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nyatta.compose.listing.Listing
import com.example.nyatta.compose.listing.ListingDetailsDestination
import com.example.nyatta.viewmodels.ListingViewModel

object ListingDetailsGraph: Navigation {
    override val route = "listing/details"
    override val title = "Listing"
}

fun NavGraphBuilder.listingDetailsGraph(
    navController: NavHostController,
    listingViewModel: ListingViewModel
) {
    navigation(
        startDestination = ListingDetailsDestination.route,
        route = ListingDetailsGraph.route
    ) {
        composable(
            route = ListingDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ListingDetailsDestination.listingIdArg) {
                type = NavType.StringType
            })
        ) {stack: NavBackStackEntry ->
            Listing(
                unitId = stack.arguments!!.getString(ListingDetailsDestination.listingIdArg)!!,
                navigateUp = { navController.navigateUp() },
                listingViewModel = listingViewModel,
                onNavigateToPhotos = { navController.navigate(it) }
            )
        }
    }
}