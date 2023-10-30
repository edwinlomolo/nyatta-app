package com.example.nyatta.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.viewmodels.AccountViewModel
import com.example.nyatta.compose.user.Phone
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.compose.user.UserOnboardingPhoneDestination
import com.example.nyatta.compose.user.UserOnboardingPhoneVerifyDestination
import com.example.nyatta.compose.user.UserSignUpDestination
import com.example.nyatta.compose.user.Verify

object SignUpDestination: Navigation {
    override val route = "signup_graph"
    override val title = UserSignUpDestination.title
}

fun NavGraphBuilder.loginGraph(
    navController: NavHostController,
    accountViewModel: AccountViewModel
) {
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
                accountViewModel = accountViewModel,
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