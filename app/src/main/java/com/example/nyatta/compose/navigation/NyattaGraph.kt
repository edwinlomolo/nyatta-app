package com.example.nyatta.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.nyatta.viewmodels.AuthViewModel
import com.example.nyatta.viewmodels.NyattaViewModelProvider
import com.example.nyatta.R
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.viewmodels.AccountViewModel
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.compose.home.Home
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.viewmodels.HomeViewModel
import com.example.nyatta.compose.listing.ListingDetailsDestination
import com.example.nyatta.viewmodels.TownsViewModel
import com.example.nyatta.viewmodels.PropertyViewModel

sealed class Screen(
    val route: String,
    @StringRes val nameResourceId: Int,
    val icon: ImageVector
) {
    data object Home: Screen(
        "home",
        R.string.home,
        Icons.TwoTone.Home
    )
    data object Add: Screen(
        "onboarding/start",
        R.string.add,
        Icons.TwoTone.AddCircle
    )
    /*object Account: Screen(
        "signup_graph",
        R.string.account,
        Icons.TwoTone.AccountBox
    )*/
}

@Composable
fun NyattaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    onboardingViewModel: OnboardingViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    apartmentViewModel: ApartmentViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    townsViewModel: TownsViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    accountViewModel: AccountViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    authViewModel: AuthViewModel = viewModel(factory = NyattaViewModelProvider.Factory)
) {
    val authUiState by authViewModel.authUiState.collectAsState()
    val navigationItems = listOf(
        Screen.Home,
        Screen.Add,
        //Screen.Account
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                navigationItems.forEach { screen ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        label = { Text(stringResource(screen.nameResourceId)) },
                        icon = { Icon(screen.icon, contentDescription = stringResource(screen.nameResourceId)) },
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // re-selecting the same item
                                launchSingleTop = true
                                // Restore state when re-selecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(route = HomeDestination.route) {
                Home(
                    onNavigateToListing = {
                        navController.navigate("${ListingDetailsDestination.route}/${it}")
                    },
                    homeViewModel = homeViewModel
                )
            }
            startPropertyOnboarding(
                navController = navController,
                onboardingViewModel = onboardingViewModel,
                authUiState = authUiState
            )
            paymentGraph(navController)
            locationGraph(
                navController = navController,
                townsViewModel = townsViewModel,
                onboardingViewModel = onboardingViewModel
            )
            listingDetailsGraph(navController)
            propertyOnboardingGraph(
                propertyViewModel = propertyViewModel,
                navController = navController
            )
            apartmentOnboardingGraph(
                navController = navController,
                apartmentViewModel = apartmentViewModel
            )
            loginGraph(
                accountViewModel = accountViewModel,
                navController = navController
            )
        }
    }
}