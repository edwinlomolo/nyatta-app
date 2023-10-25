package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.onboarding.user.Names
import com.example.nyatta.ui.screens.onboarding.user.Phone
import com.example.nyatta.ui.screens.onboarding.user.SignUp
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingNameDestination
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingPhoneDestination
import com.example.nyatta.ui.screens.onboarding.user.UserOnboardingPhoneVerifyDestination
import com.example.nyatta.ui.screens.onboarding.user.UserSignUpDestination
import com.example.nyatta.ui.screens.onboarding.user.Verify

object SignUpDestination: Navigation {
    override val route = "signup_graph"
    override val title = UserSignUpDestination.title
}

fun NavGraphBuilder.loginGraph(navController: NavHostController) {
    navigation(
        startDestination = UserSignUpDestination.route,
        route = SignUpDestination.route
    ) {
        composable(UserSignUpDestination.route) {
            SignUp(
                navigateUp = { navController.popBackStack() },
                navigateNext = { navController.navigate(it) },
                currentRoute = navController.currentBackStackEntry?.destination?.route
            )
        }
        composable(UserOnboardingPhoneDestination.route) {
            Phone(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(UserOnboardingPhoneVerifyDestination.route) {
            Verify(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
        composable(UserOnboardingNameDestination.route) {
            Names(
                navigateUp = { navController.navigateUp() },
                navigateNext = { navController.navigate(it) }
            )
        }
    }
}