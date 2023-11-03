package com.example.nyatta.compose.navigation

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.compose.uploads.Uploads
import com.example.nyatta.compose.uploads.UploadsDestination
import com.example.nyatta.viewmodels.ApartmentData
import com.example.nyatta.viewmodels.ApartmentDataValidity

object ApartmentOnboarding: Navigation {
    override val route = "onboarding/apartment"
    override val title = "Setup apartment"
}

fun NavGraphBuilder.apartmentOnboardingGraph(
    modifier: Modifier,
    navController: NavHostController,
    apartmentViewModel: ApartmentViewModel,
    apartmentData: ApartmentData,
    dataValidity: ApartmentDataValidity
) {
    val descriptionValidToProceed = dataValidity.description
    val amenitiesValidToProceed = apartmentData.selectedAmenities.isNotEmpty()
    val hasBedCount: Boolean = apartmentData.unitType != "Single room" && apartmentData.unitType != "Studio"

    navigation(
        startDestination = ApartmentDescriptionDestination.route,
        route = ApartmentOnboarding.route
    ) {
        composable(route = ApartmentDescriptionDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = descriptionValidToProceed,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(SelectPropertyDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.associate_with_property),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    ApartmentDescription(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = SelectPropertyDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(UnitTypeDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.describe_unit_type),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    SelectProperty(
                        apartmentViewModel = apartmentViewModel,
                    )
                }
            }
        }
        composable(route = UnitTypeDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick =
                        {
                            if (!hasBedCount)
                                navController.navigate(ApartmentAmenitiesDestination.route)
                            else
                                navController.navigate(ApartmentBedroomsDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = if (hasBedCount) stringResource(R.string.add_bedrooms) else stringResource(R.string.describe_amenities),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Unit(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = ApartmentBedroomsDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = amenitiesValidToProceed,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(ApartmentAmenitiesDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.describe_amenities),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Bedroom(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = ApartmentAmenitiesDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(ApartmentBathsDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.describe_bathrooms),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Amenities(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = ApartmentBathsDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(UploadsDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.add_images),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Bath(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = UploadsDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(ApartmentStateDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.describe_unit_state),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Uploads(
                        apartmentViewModel = apartmentViewModel,
                    )
                }
            }
        }
        composable(route = ApartmentStateDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(ApartmentPriceDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.add_unit_price),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    UnitState(
                        apartmentViewModel = apartmentViewModel,
                    )
                }
            }
        }
        composable(route = ApartmentPriceDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            navController.navigate(PaymentGraph.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.create_unit),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Price(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
    }
}