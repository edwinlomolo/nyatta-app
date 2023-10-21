package com.example.nyatta.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.nyatta.ui.screens.account.Account
import com.example.nyatta.ui.screens.account.AccountDestination
import com.example.nyatta.ui.screens.home.Home
import com.example.nyatta.ui.screens.home.HomeDestination

@Composable
fun NyattaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = HomeDestination.route) {
            Home(
                onNavigateTo = { navController.navigate(it)},
                currentRoute = navController.currentBackStackEntry?.destination?.route
            )
        }
        composable(route = AccountDestination.route) {
            Account(
                onNavigateTo = { navController.navigate(it) },
                currentRoute = navController.currentBackStackEntry?.destination?.route
            )
        }
    }
}