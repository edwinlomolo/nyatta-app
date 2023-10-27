package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.apartment.Amenities
import com.example.nyatta.ui.screens.apartment.ApartmentAmenitiesDestination
import com.example.nyatta.ui.screens.apartment.ApartmentBathsDestination
import com.example.nyatta.ui.screens.apartment.ApartmentBedroomsDestination
import com.example.nyatta.ui.screens.apartment.ApartmentDescription
import com.example.nyatta.ui.screens.apartment.ApartmentDescriptionDestination
import com.example.nyatta.ui.screens.apartment.ApartmentPriceDestination
import com.example.nyatta.ui.screens.apartment.ApartmentStateDestination
import com.example.nyatta.ui.screens.apartment.Bath
import com.example.nyatta.ui.screens.apartment.Bedroom
import com.example.nyatta.ui.screens.apartment.Price
import com.example.nyatta.ui.screens.apartment.SelectProperty
import com.example.nyatta.ui.screens.apartment.SelectPropertyDestination
import com.example.nyatta.ui.screens.apartment.Unit
import com.example.nyatta.ui.screens.apartment.UnitState
import com.example.nyatta.ui.screens.apartment.UnitTypeDestination
import com.example.nyatta.ui.screens.uploads.Uploads
import com.example.nyatta.ui.screens.uploads.UploadsDestination

object ApartmentOnboarding: Navigation {
    override val route = "onboarding/apartment"
    override val title = "Setup apartment"
}

fun NavGraphBuilder.apartmentGraph(navController: NavHostController) {
    navigation(
        startDestination = ApartmentDescriptionDestination.route,
        route = ApartmentOnboarding.route
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