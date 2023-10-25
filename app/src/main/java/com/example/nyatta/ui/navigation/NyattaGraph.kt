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
import com.example.nyatta.ui.screens.onboarding.apartment.Price
import com.example.nyatta.ui.screens.onboarding.apartment.SelectProperty
import com.example.nyatta.ui.screens.onboarding.apartment.SelectPropertyDestination
import com.example.nyatta.ui.screens.onboarding.apartment.UnitTypeDestination
import com.example.nyatta.ui.screens.onboarding.apartment.Unit
import com.example.nyatta.ui.screens.onboarding.apartment.UnitState
import com.example.nyatta.ui.screens.onboarding.apartment.Location as ApartmentLocation
import com.example.nyatta.ui.screens.onboarding.location.Location
import com.example.nyatta.ui.screens.onboarding.location.LocationDestination
import com.example.nyatta.ui.screens.onboarding.location.TownDestination
import com.example.nyatta.ui.screens.onboarding.location.Towns
import com.example.nyatta.ui.screens.onboarding.payment.Mpesa
import com.example.nyatta.ui.screens.onboarding.payment.PayDestination
import com.example.nyatta.ui.screens.onboarding.property.Caretaker
import com.example.nyatta.ui.screens.onboarding.property.CaretakerDestination
import com.example.nyatta.ui.screens.onboarding.property.PropertyDescription
import com.example.nyatta.ui.screens.onboarding.property.PropertyDestination
import com.example.nyatta.ui.screens.onboarding.property.StartOnboardingDestination
import com.example.nyatta.ui.screens.onboarding.property.Type
import com.example.nyatta.ui.screens.onboarding.uploads.Uploads
import com.example.nyatta.ui.screens.onboarding.uploads.UploadsDestination
import com.example.nyatta.ui.screens.onboarding.user.Names
import com.example.nyatta.ui.screens.onboarding.user.Phone
import com.example.nyatta.ui.screens.onboarding.user.SignUp
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingNameDestination
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingPhoneDestination
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingPhoneVerifyDestination
import com.example.nyatta.ui.screens.onboarding.user.UserSignUpDestination
import com.example.nyatta.ui.screens.onboarding.user.Verify

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
        composable(route = ApartmentDescriptionDestination.route) {
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
                navigateNext = { navController.navigate(it) },
                navigateUp = { navController.navigateUp() }
            )
        }
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
        composable(route = PayDestination.route) {
            Mpesa(
                navigateUp = { navController.navigateUp() }
            )
        }
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
            ApartmentLocation(
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
        composable(route = UserOnboardingNameDestination.route) {
            Names(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UserOnboardingPhoneDestination.route) {
            Phone(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = UserOnboardingPhoneVerifyDestination.route) {
            Verify(
                navigateUp = { navController.navigateUp() }
            )
        }
        composable(route = UserSignUpDestination.route) {
            SignUp(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
    }
}