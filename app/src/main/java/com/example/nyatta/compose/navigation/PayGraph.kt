package com.example.nyatta.compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.compose.payment.Mpesa
import com.example.nyatta.compose.payment.PayDestination
import com.example.nyatta.viewmodels.AccountViewModel

object PaymentGraph: Navigation {
    override val route = "pay_graph"
    override val title = "Pay"
}

fun NavGraphBuilder.paymentGraph(
    navController: NavHostController,
    accViewModel: AccountViewModel
) {
    navigation(
        startDestination = PayDestination.route,
        route = PaymentGraph.route
    ) {
        composable(route = PayDestination.route) {
            Mpesa(
                accViewModel = accViewModel,
                navigateNext = { navController.navigate(it) }
            )
        }
    }
}