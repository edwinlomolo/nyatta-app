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
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.property.Caretaker
import com.example.nyatta.compose.property.CaretakerDestination
import com.example.nyatta.compose.property.PropertyDescription
import com.example.nyatta.compose.property.PropertyDescriptionDestination
import com.example.nyatta.compose.property.PropertyThumbnailDestination
import com.example.nyatta.compose.property.Thumbnail
import com.example.nyatta.viewmodels.PropertyData
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyOnboarding: Navigation {
    override val route = "onboarding/property"
    override val title = "Setup property"
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.propertyOnboardingGraph(
    modifier: Modifier,
    navController: NavHostController,
    propertyViewModel: PropertyViewModel,
    propertyUiState: PropertyData
) {
    navigation(
        startDestination = PropertyDescriptionDestination.route,
        route = PropertyOnboarding.route
    ) {
        val emptyState = propertyUiState.description.isEmpty()
        val thumbnailValidToProceed = propertyUiState.validToProceed.thumbnail
        val isCaretaker = propertyUiState.isCaretaker
        val caretaker = propertyUiState.caretaker
        val validToProceed: Boolean = caretaker.firstName.isNotEmpty() &&
                caretaker.lastName.isNotEmpty() &&
                caretaker.phone.isNotEmpty() &&
                caretaker.image.isNotEmpty()

        composable(route = PropertyDescriptionDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = stringResource(id = R.string.property_name)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = !emptyState,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            if (!emptyState) navController.navigate(PropertyThumbnailDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.add_thumbnail),
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
                    PropertyDescription(
                        propertyViewModel = propertyViewModel
                    )
                }
            }
        }
        composable(route = PropertyThumbnailDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = stringResource(id = R.string.property_thumbnail)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = thumbnailValidToProceed,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            if (!emptyState) navController.navigate(CaretakerDestination.route)
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.add_caretaker),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    )
                }
            ) { innerPadding ->
                Surface (
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Thumbnail(
                        propertyViewModel = propertyViewModel
                    )
                }
            }
        }
        composable(route = CaretakerDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = stringResource(id = R.string.caretaker)
                    )
                },
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = if (!isCaretaker) validToProceed else true,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            if (!isCaretaker && validToProceed) {
                                navController.navigate(LocationGraph.route)
                            } else {
                                navController.navigate(LocationGraph.route)
                            }
                        },
                        actionButtonText = {
                            Text(
                                text = stringResource(R.string.confirm_location),
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
                    Caretaker(
                        propertyViewModel = propertyViewModel
                    )
                }
            }
        }
    }
}