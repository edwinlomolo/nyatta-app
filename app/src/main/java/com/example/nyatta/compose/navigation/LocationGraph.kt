package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.location.Location
import com.example.nyatta.compose.location.LocationDestination
import com.example.nyatta.compose.location.TownDestination
import com.example.nyatta.compose.location.Towns
import com.example.nyatta.viewmodels.AccountViewModel
import com.example.nyatta.viewmodels.TownsViewModel
import com.example.nyatta.viewmodels.UserDetails
import com.google.android.gms.maps.model.LatLng

object LocationGraph: Navigation {
    override val route = "location_graph"
    override val title = "Location"
}
fun NavGraphBuilder.locationGraph(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel,
    townsViewModel: TownsViewModel,
    deviceLocation: LatLng,
    propertyType: String,
    navController: NavHostController
) {
    navigation(
        startDestination = LocationDestination.route,
        route = LocationGraph.route
    ) {
        composable(route = LocationDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            if (propertyType == "Apartments Building") navController.navigate(PaymentGraph.route)
                        },
                        actionButtonText = {
                            if (propertyType === "Apartments Building") {
                                Text(
                                    text = "Create property",
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
                        onboardingViewModel = onboardingViewModel
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