package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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