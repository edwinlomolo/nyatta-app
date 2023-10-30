package com.example.nyatta.navigation

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.viewmodels.AuthUiState
import com.example.nyatta.R
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.startpropertyonboarding.Type
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.compose.user.UserSignUpDestination

object StartPropertyOnboardingGraph: Navigation {
    override val route = "onboarding/start"
    override val title = null
}

fun NavGraphBuilder.startPropertyOnboarding(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    authUiState: AuthUiState
) {
    val sendToSignup = authUiState.user.isEmpty()

    navigation(
        startDestination = if (sendToSignup) UserSignUpDestination.route else StartOnboardingDestination.route,
        route = StartPropertyOnboardingGraph.route
    ) {
        composable(UserSignUpDestination.route) {
            SignUp(
                text = {
                    Text(
                        text = stringResource(R.string.create_account_to_proceed),
                        style = MaterialTheme.typography.bodyLarge
                    )
                },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = StartOnboardingDestination.route) {
            Type(
                navigateBack = { navController.popBackStack() },
                onboardingViewModel = onboardingViewModel,
                navigateToNext = { navController.navigate(it) }
            )
        }
    }
}