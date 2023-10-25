package com.example.nyatta.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.ui.screens.onboarding.payment.Mpesa
import com.example.nyatta.ui.screens.onboarding.payment.PayDestination

object PaymentGraph: Navigation {
    override val route = "pay_graph"
    override val title = "Pay"
}

fun NavGraphBuilder.paymentGraph(navController: NavHostController) {
    navigation(
        startDestination = PayDestination.route,
        route = PaymentGraph.route
    ) {
        composable(route = PayDestination.route) {
            Mpesa(
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}