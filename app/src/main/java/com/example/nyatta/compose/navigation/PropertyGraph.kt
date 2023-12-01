package com.example.nyatta.compose.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.nyatta.compose.property.Property
import com.example.nyatta.compose.property.PropertyDetailsDestination

object PropertyGraph: Navigation {
    override val route = "property/details"
    override val title = null
}

fun NavGraphBuilder.propertyDetails(
    navController: NavHostController
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
        ) {
            Property(
                propertyId = it.arguments!!.getString(PropertyDetailsDestination.propertyIdArg)!!
            )
        }
    }
}