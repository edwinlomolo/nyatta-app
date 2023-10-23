package com.example.nyatta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.nyatta.ui.screens.account.Account
import com.example.nyatta.ui.screens.account.AccountDestination
import com.example.nyatta.ui.screens.home.Home
import com.example.nyatta.ui.screens.home.HomeDestination
import com.example.nyatta.ui.screens.listing.ListingDetailsDestination
import com.example.nyatta.ui.screens.listing.Listing
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentDescription
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentDestination
import com.example.nyatta.ui.screens.onboarding.location.Location
import com.example.nyatta.ui.screens.onboarding.location.LocationDestination
import com.example.nyatta.ui.screens.onboarding.property.Caretaker
import com.example.nyatta.ui.screens.onboarding.property.CaretakerDestination
import com.example.nyatta.ui.screens.onboarding.property.PropertyDescription
import com.example.nyatta.ui.screens.onboarding.property.PropertyDestination
import com.example.nyatta.ui.screens.onboarding.property.StartOnboardingDestination
import com.example.nyatta.ui.screens.onboarding.property.Type

@Composable
fun NyattaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            Home(
                onNavigateTo = { navController.navigate(it) },
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                onNavigateToListing = {
                    navController.navigate("${ListingDetailsDestination.route}/${it}")
                }
            )
        }
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
        composable(route = AccountDestination.route) {
            Account(
                onNavigateTo = { navController.navigate(it) },
                currentRoute = navController.currentBackStackEntry?.destination?.route
            )
        }
        composable(route = StartOnboardingDestination.route) {
            Type(
                onNavigateTo = { navController.navigate(it) },
                currentRoute = navController.currentBackStackEntry?.destination?.route,
                navigateToNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentDestination.route) {
            ApartmentDescription(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = PropertyDestination.route) {
            PropertyDescription(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = CaretakerDestination.route) {
            Caretaker(
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = LocationDestination.route) {
            Location()
        }
    }
}