package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.property.Property
import com.example.nyatta.compose.property.PropertyDetailsDestination

object PropertyGraph: Navigation {
    override val route = "property/details"
    override val title = null
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.propertyDetails(
    navController: NavHostController,
    modifier: Modifier
) {
    navigation(
        startDestination = PropertyDetailsDestination.route,
        route = PropertyGraph.route
    ) {
        composable(
            route = PropertyDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(PropertyDetailsDestination.propertyIdArg) {
                type = NavType.StringType
            })
        ) { stack: NavBackStackEntry ->
            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = { navController.popBackStack() }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Property(
                        propertyId = stack.arguments!!.getString(PropertyDetailsDestination.propertyIdArg)!!
                    )
                }
            }
        }
    }
}