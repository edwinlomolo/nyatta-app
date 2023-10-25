package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.account.Account
import com.example.nyatta.ui.screens.account.AccountDestination

object AccountGraph: Navigation {
    override val route = "account_graph"
    override val title = "Account"
}

fun NavGraphBuilder.accountGraph(navController: NavHostController) {
    navigation(
        startDestination = AccountDestination.route,
        route = AccountGraph.route
    ) {
        composable(route = AccountDestination.route) {
            Account()
        }
    }
}