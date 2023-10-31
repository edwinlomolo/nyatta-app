package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nyatta.R
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.payment.Mpesa
import com.example.nyatta.compose.payment.PayDestination
import com.example.nyatta.viewmodels.AccountViewModel

object PaymentGraph: Navigation {
    override val route = "pay_graph"
    override val title = "Pay"
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.paymentGraph(
    modifier: Modifier,
    navController: NavHostController,
    accViewModel: AccountViewModel
) {
    navigation(
        startDestination = PayDestination.route,
        route = PaymentGraph.route
    ) {
        composable(route = PayDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        title = stringResource(R.string.pay_monthly),
                        navigateUp = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Mpesa(
                        accViewModel = accViewModel,
                        navigateNext = { navController.navigate(it) }
                    )
                }
            }
        }
    }
}