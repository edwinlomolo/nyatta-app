package com.example.nyatta.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.R
import com.example.nyatta.ui.screens.OnboardingViewModel
import com.example.nyatta.ui.screens.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.ui.screens.startpropertyonboarding.Type
import com.example.nyatta.ui.screens.user.SignUp
import com.example.nyatta.ui.screens.user.UserSignUpDestination

object StartPropertyOnboardingGraph: Navigation {
    override val route = "onboarding/start"
    override val title = null
}

fun NavGraphBuilder.startPropertyOnboarding(
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel
) {
    navigation(
        startDestination = UserSignUpDestination.route,
        route = StartPropertyOnboardingGraph.route
    ) {
        composable(UserSignUpDestination.route) {
            SignUp(
                text = {
                    Text(
                        text = stringResource(R.string.create_account_to_proceed),
                        modifier = Modifier.padding(4.dp)
                    )
                },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(route = StartOnboardingDestination.route) {
            Type(
                onboardingViewModel = onboardingViewModel,
                navigateToNext = { navController.navigate(it) }
            )
        }
    }
}