package com.example.nyatta.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.compose.home.Home
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.viewmodels.HomeViewModel
import com.example.nyatta.compose.listing.ListingDetailsDestination
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.user.Account
import com.example.nyatta.compose.user.AccountDestination
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.viewmodels.TownsViewModel
import com.example.nyatta.viewmodels.PropertyViewModel
import com.google.android.gms.maps.model.LatLng

sealed class Screen(
    val route: String,
    @StringRes val nameResourceId: Int,
    val icon: ImageVector,
    val modifier: Modifier
) {
    data object Home: Screen(
        HomeDestination.route,
        R.string.explore,
        Icons.TwoTone.LocationOn,
        Modifier
    )
    data object Add: Screen(
        StartOnboardingDestination.route,
        R.string.add,
        Icons.TwoTone.AddCircle,
        modifier = Modifier.size(36.dp)
    )
    data object Account: Screen(
        AccountDestination.route,
        R.string.account,
        Icons.TwoTone.AccountCircle,
        Modifier
    )
}

val navigationItems = listOf(
    Screen.Home,
    Screen.Add,
    Screen.Account
)

@Composable
fun NyattaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    onboardingViewModel: OnboardingViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    apartmentViewModel: ApartmentViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    townsViewModel: TownsViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    authViewModel: AuthViewModel = viewModel(factory = NyattaViewModelProvider.Factory)
) {
    val authUiState by authViewModel.authUiState.collectAsState()
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val propertyUiState by propertyViewModel.uiState.collectAsState()
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    val user = authUiState.user
    val onboardingUiData = onboardingUiState
    val propertyUiData = propertyUiState
    val deviceLocation = LatLng(authUiState.user.lat, authUiState.user.lng)
    val dataValidity = apartmentUiState.dataValidity

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composable(route = HomeDestination.route) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        navigationItems.forEach { screen ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                icon = { Icon(screen.icon, modifier = screen.modifier, contentDescription = stringResource(screen.nameResourceId)) },
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
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Home(
                        onNavigateToListing = {
                            navController.navigate("${ListingDetailsDestination.route}/${it}")
                        },
                        homeViewModel = homeViewModel
                    )
                }
            }
        }
        composable(route = AccountDestination.route) {
            Scaffold(
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        navigationItems.forEach { screen ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                icon = { Icon(screen.icon, modifier = screen.modifier, contentDescription = stringResource(screen.nameResourceId)) },
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
            ) {innerPadding ->
                Surface(
                   modifier = modifier
                       .fillMaxSize()
                       .padding(innerPadding)
                ) {
                    if (user.token.isNotEmpty()) {
                        Account(
                            authViewModel = authViewModel
                        )
                    } else {
                        SignUp(
                            navigateNext = {
                                navController.navigate(it) {
                                    popUpTo(AccountDestination.route) {
                                        inclusive = true
                                        saveState = false
                                    }
                                }
                            },
                            text = {
                                Text(
                                    text = stringResource(id = R.string.create_account),
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        )
                    }
                }
            }
        }
        startPropertyOnboarding(
            modifier = modifier,
            navController = navController,
            onboardingViewModel = onboardingViewModel,
            user = user,
            onboardingUiState = onboardingUiData
        )
        paymentGraph(
            modifier = modifier,
            navController = navController,
            authViewModel = authViewModel,
            propertyViewModel = propertyViewModel
        )
        locationGraph(
            modifier = modifier,
            navController = navController,
            townsViewModel = townsViewModel,
            onboardingViewModel = onboardingViewModel,
            propertyViewModel = propertyViewModel,
            authViewModel = authViewModel,
            deviceLocation = deviceLocation,
            propertyType = onboardingUiData.type,
            user = user,
        )
        listingDetailsGraph(navController)
        propertyOnboardingGraph(
            modifier = modifier,
            propertyViewModel = propertyViewModel,
            navController = navController,
            propertyUiState = propertyUiData
        )
        apartmentOnboardingGraph(
            modifier = modifier,
            navController = navController,
            apartmentViewModel = apartmentViewModel,
            dataValidity = dataValidity,
            apartmentData = apartmentUiState,
            user = user,
            authViewModel = authViewModel,
            onboardingViewModel = onboardingViewModel
        )
        loginGraph(
            authViewModel = authViewModel,
            navController = navController
        )
    }
}