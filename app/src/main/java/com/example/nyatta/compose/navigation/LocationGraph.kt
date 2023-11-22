package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.R
import com.example.nyatta.compose.apartment.UnitTypeDestination
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.location.Location
import com.example.nyatta.compose.location.LocationDestination
import com.example.nyatta.compose.location.TownDestination
import com.example.nyatta.compose.location.Towns
import com.example.nyatta.compose.property.CaretakerDestination
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.viewmodels.Auth
import com.example.nyatta.viewmodels.AuthViewModel
import com.example.nyatta.viewmodels.ICreateProperty
import com.example.nyatta.viewmodels.PropertyViewModel
import com.example.nyatta.viewmodels.TownsViewModel
import com.google.android.gms.maps.model.LatLng

object LocationGraph: Navigation {
    override val route = "location_graph"
    override val title = "Location"
}
@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.locationGraph(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel,
    propertyViewModel: PropertyViewModel,
    townsViewModel: TownsViewModel,
    authViewModel: AuthViewModel,
    deviceLocation: LatLng,
    propertyType: String,
    navController: NavHostController,
    createPropertyState: ICreateProperty,
    user: Auth
) {
    navigation(
        startDestination = LocationDestination.route,
        route = LocationGraph.route
    ) {
        composable(route = LocationDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.location)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            if (propertyType == "Apartments Building") {
                                if (user.token.isLandlord) {
                                    propertyViewModel.createProperty(propertyType, deviceLocation) {
                                        propertyViewModel.propertySubmitted(true)
                                        navController.navigate(StartOnboardingDestination.route) {
                                            popUpTo(StartOnboardingDestination.route) {
                                                saveState = false
                                                inclusive = true
                                            }
                                        }
                                    }
                                } else {
                                    navController.navigate(PaymentGraph.route) {
                                        launchSingleTop = true
                                    }
                                }
                            } else if (propertyType == "Unit") {
                                navController.navigate(UnitTypeDestination.route)
                            } else {
                                navController.navigate(CaretakerDestination.route)
                            }
                        },
                        isLoading = propertyViewModel.createPropertyState is ICreateProperty.Loading,
                        validToProceed = createPropertyState !is ICreateProperty.Loading && createPropertyState !is ICreateProperty.CreatePropertyError,
                        showNextIcon = propertyType != "Apartments Building",
                        actionButtonText = {
                            if (propertyType === "Apartments Building") {
                                Text(
                                    text = stringResource(R.string.create_property),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            } else {
                                Text(
                                    text = "Confirm location",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Location(
                        deviceLocation = deviceLocation,
                        onboardingViewModel = onboardingViewModel,
                        authViewModel = authViewModel
                    )
                }
            }
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