package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.onboarding.apartment.Amenities
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentAmenitiesDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentBathsDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentBedroomsDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentDescription
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentDescriptionDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentLocationDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentPriceDestination
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentStateDestination
import com.example.nyatta.ui.screens.onboarding.apartment.Bath
import com.example.nyatta.ui.screens.onboarding.apartment.Bedroom
import com.example.nyatta.ui.screens.onboarding.apartment.Location
import com.example.nyatta.ui.screens.onboarding.apartment.Price
import com.example.nyatta.ui.screens.onboarding.apartment.SelectProperty
import com.example.nyatta.ui.screens.onboarding.apartment.SelectPropertyDestination
import com.example.nyatta.ui.screens.onboarding.apartment.Unit
import com.example.nyatta.ui.screens.onboarding.apartment.UnitState
import com.example.nyatta.ui.screens.onboarding.apartment.UnitTypeDestination
import com.example.nyatta.ui.screens.onboarding.uploads.Uploads
import com.example.nyatta.ui.screens.onboarding.uploads.UploadsDestination

object ApartmentOnboardingGraph: Navigation {
    override val route = "onboarding/apartment"
    override val title = "Setup apartment"
}

fun NavGraphBuilder.apartmentGraph(navController: NavHostController) {
    navigation(
        startDestination = ApartmentDescriptionDestination.route,
        route = ApartmentOnboardingGraph.route
    ) {
        composable(route = ApartmentDescriptionDestination.route) {
            ApartmentDescription(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = SelectPropertyDestination.route) {
            SelectProperty(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentLocationDestination.route) {
            Location(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UnitTypeDestination.route) {
            Unit(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentAmenitiesDestination.route) {
            Amenities(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentBedroomsDestination.route) {
            Bedroom(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentBathsDestination.route) {
            Bath(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UploadsDestination.route) {
            Uploads(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentStateDestination.route) {
            UnitState(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentPriceDestination.route) {
            Price(
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}