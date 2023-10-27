package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.user.Phone
import com.example.nyatta.ui.screens.user.SignUp
import com.example.nyatta.ui.screens.user.UserOnboardingPhoneDestination
import com.example.nyatta.ui.screens.user.UserOnboardingPhoneVerifyDestination
import com.example.nyatta.ui.screens.user.UserSignUpDestination
import com.example.nyatta.ui.screens.user.Verify

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
                navigateNext = { navController.navigate(it) }
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
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}