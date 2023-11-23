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
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.uploads.Uploads
import com.example.nyatta.compose.uploads.UploadsDestination
import com.example.nyatta.data.model.Token
import com.example.nyatta.viewmodels.ApartmentData
import com.example.nyatta.viewmodels.ApartmentDataValidity
import com.example.nyatta.viewmodels.AuthViewModel
import com.example.nyatta.viewmodels.ICreateUnit
import com.example.nyatta.viewmodels.ImageState
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.viewmodels.PropertyData
import com.google.android.gms.maps.model.LatLng

object ApartmentOnboarding: Navigation {
    override val route = "onboarding/apartment"
    override val title = "Setup apartment"
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.apartmentOnboardingGraph(
    modifier: Modifier,
    navController: NavHostController,
    apartmentViewModel: ApartmentViewModel,
    propertyData: PropertyData,
    deviceLocation: LatLng,
    authViewModel: AuthViewModel,
    onboardingViewModel: OnboardingViewModel,
    apartmentData: ApartmentData,
    dataValidity: ApartmentDataValidity,
    user: Token,
    propertyType: String
) {
    val descriptionValidToProceed = dataValidity.description
    val bathroomsValidToProceed = dataValidity.bathrooms
    val amenitiesValidToProceed = apartmentData.selectedAmenities.isNotEmpty()
    val stillUploading = apartmentData.images.values.flatten().any { it is ImageState.Loading }
    val priceValidToProceed = dataValidity.price
    val hasBedCount: Boolean = apartmentData.unitType != "Single room" && apartmentData.unitType != "Studio"
    val createUnitState: ICreateUnit = apartmentViewModel.createUnitState

    navigation(
        startDestination = ApartmentDescriptionDestination.route,
        route = ApartmentOnboarding.route
    ) {
        composable(route = ApartmentDescriptionDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.unit_name)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = descriptionValidToProceed,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            if (propertyType == "Unit") {
                                navController.navigate(SelectPropertyDestination.route)
                            } else {
                                navController.navigate(LocationGraph.route)
                            }
                        },
                        actionButtonText = {
                            val buttonText = if (propertyType == "Unit")
                                stringResource(id = R.string.associate_with_property)
                            else
                                stringResource(id = R.string.confirm_location)
                            Text(
                                text = buttonText,
                                style = MaterialTheme.typography.labelSmall,
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
                        onboardingViewModel = onboardingViewModel,
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = SelectPropertyDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.select_property)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        validToProceed = apartmentData.associatedToProperty != null,
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
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.unit_type)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        validToProceed = true,
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
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.bedroom_title)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        validToProceed = true,
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
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.select_amenities)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = amenitiesValidToProceed,
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
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.bath_label_text)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = bathroomsValidToProceed,
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
                    Bath(
                        apartmentViewModel = apartmentViewModel
                    )
                }
            }
        }
        composable(route = ApartmentStateDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.unit_state)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        navigateBack = {
                            navController.popBackStack()
                        },
                        validToProceed = true,
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
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.price)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = priceValidToProceed,
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
                    Price(
                        apartmentViewModel = apartmentViewModel,
                        authViewModel = authViewModel
                    )
                }
            }
        }
        composable(route = UploadsDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() },
                        title = stringResource(id = R.string.uploads)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = !stillUploading,
                        navigateBack = {
                            navController.popBackStack()
                        },
                        onActionButtonClick = {
                            // TODO call submission endpoint depending on unit type
                            if (!user.isLandlord) {
                                navController.navigate(PaymentGraph.route) {
                                    launchSingleTop = true
                                }
                            } else {
                                if (createUnitState !is ICreateUnit.Loading) {
                                    apartmentViewModel.createUnit(propertyType, deviceLocation, propertyData) {
                                        apartmentViewModel.unitSubmitted(true)
                                        navController.navigate(StartOnboardingDestination.route) {
                                            popUpTo(StartOnboardingDestination.route) {
                                                inclusive = true
                                                saveState = false
                                            }
                                        }
                                    }
                                }
                            }
                        },
                        isLoading = createUnitState is ICreateUnit.Loading,
                        showNextIcon = false,
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
                    Uploads(
                        apartmentViewModel = apartmentViewModel,
                        navigateNext = { navController.navigate(StartOnboardingDestination.route) {
                            popUpTo(UploadsDestination.route) {
                                inclusive = true
                                saveState = false
                            }
                        } }
                    )
                }
            }
        }
    }
}