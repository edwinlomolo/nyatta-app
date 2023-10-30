package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.compose.property.Caretaker
import com.example.nyatta.compose.property.CaretakerDestination
import com.example.nyatta.compose.property.PropertyDescription
import com.example.nyatta.compose.property.PropertyDescriptionDestination
import com.example.nyatta.viewmodels.PropertyData
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyOnboarding: Navigation {
    override val route = "onboarding/property"
    override val title = "Setup property"
}

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
        val isCaretaker = propertyUiState.isCaretaker
        val caretaker = propertyUiState.caretaker
        val validToProceed: Boolean = caretaker.firstName.isNotEmpty() &&
                caretaker.lastName.isNotEmpty() &&
                caretaker.phone.isNotEmpty() &&
                caretaker.image.isNotEmpty()

        composable(route = PropertyDescriptionDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = !emptyState,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            if (!emptyState) navController.navigate(CaretakerDestination.route)
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
        composable(route = CaretakerDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = if (!isCaretaker) validToProceed else true,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            if (!isCaretaker && validToProceed) {
                                navController.navigate(PaymentGraph.route)
                            } else if (isCaretaker) {
                                navController.navigate(PaymentGraph.route)
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
                    Caretaker(
                        propertyViewModel = propertyViewModel
                    )
                }
            }
        }
    }
}