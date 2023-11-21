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
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.startpropertyonboarding.Type
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.viewmodels.Auth
import com.example.nyatta.viewmodels.OnboardingUiState


object StartPropertyOnboardingGraph: Navigation {
    override val route = "onboarding/start"
    override val title = null
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.startPropertyOnboarding(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    user: Auth,
    onboardingUiState: OnboardingUiState
) {
    val validToProceed = onboardingUiState.validToProceed.type
    val isAuthenticated = user.token.token.isNotEmpty()
    val propertyType = onboardingUiState.type

    navigation(
        startDestination = StartOnboardingDestination.route,
        route = StartPropertyOnboardingGraph.route
    ) {
        composable(route = StartOnboardingDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = if (isAuthenticated) stringResource(id = R.string.what_to_add) else stringResource(
                            id = R.string.create_account
                        ),
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() }
                    )
                },
                bottomBar = {
                    if (isAuthenticated) {
                        OnboardingBottomBar(
                            validToProceed = validToProceed,
                            navigateBack = { navController.popBackStack() },
                            onActionButtonClick = {
                                when (onboardingUiState.type) {
                                    "Apartments Building" -> navController.navigate(
                                        PropertyOnboarding.route
                                    )

                                    else -> navController.navigate(ApartmentOnboarding.route)
                                }
                            },
                            actionButtonText = {
                                val buttonText = when(propertyType) {
                                    "Apartments Building" -> R.string.describe_this_property
                                    "Unit" -> R.string.describe_apartment_unit
                                    "Condo" -> R.string.setup_condo
                                    else -> R.string.setup
                                }
                                Text(
                                    text = stringResource(buttonText),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    if (isAuthenticated) {
                        Type(
                            onboardingViewModel = onboardingViewModel
                        )
                    } else {
                        SignUp(
                            navigateNext = { navController.navigate(it) }
                        )
                    }
                }
            }
        }
    }
}