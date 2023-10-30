package com.example.nyatta.compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.compose.apartment.Amenities
import com.example.nyatta.compose.apartment.ApartmentAmenitiesDestination
import com.example.nyatta.compose.apartment.ApartmentBathsDestination
import com.example.nyatta.compose.apartment.ApartmentBedroomsDestination
import com.example.nyatta.compose.apartment.ApartmentDescription
import com.example.nyatta.compose.apartment.ApartmentDescriptionDestination
import com.example.nyatta.compose.apartment.ApartmentPriceDestination
import com.example.nyatta.compose.apartment.ApartmentStateDestination
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.compose.apartment.Bath
import com.example.nyatta.compose.apartment.Bedroom
import com.example.nyatta.compose.apartment.Price
import com.example.nyatta.compose.apartment.SelectProperty
import com.example.nyatta.compose.apartment.SelectPropertyDestination
import com.example.nyatta.compose.apartment.Unit
import com.example.nyatta.compose.apartment.UnitState
import com.example.nyatta.compose.apartment.UnitTypeDestination
import com.example.nyatta.compose.uploads.Uploads
import com.example.nyatta.compose.uploads.UploadsDestination

object ApartmentOnboarding: Navigation {
    override val route = "onboarding/apartment"
    override val title = "Setup apartment"
}

fun NavGraphBuilder.apartmentOnboardingGraph(
    navController: NavHostController,
    apartmentViewModel: ApartmentViewModel
) {
    navigation(
        startDestination = ApartmentDescriptionDestination.route,
        route = ApartmentOnboarding.route
    ) {
        composable(route = ApartmentDescriptionDestination.route) {
            ApartmentDescription(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = SelectPropertyDestination.route) {
            SelectProperty(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UnitTypeDestination.route) {
            Unit(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentAmenitiesDestination.route) {
            Amenities(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentBedroomsDestination.route) {
            Bedroom(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentBathsDestination.route) {
            Bath(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UploadsDestination.route) {
            Uploads(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentStateDestination.route) {
            UnitState(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = ApartmentPriceDestination.route) {
            Price(
                apartmentViewModel = apartmentViewModel,
                navigateBack = { navController.popBackStack() },
            )
        }
    }
}