package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
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
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.compose.home.Home
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.viewmodels.HomeViewModel
import com.example.nyatta.compose.listing.ListingDetailsDestination
import com.example.nyatta.compose.user.Account
import com.example.nyatta.compose.user.AccountDestination
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.viewmodels.ListingViewModel
import com.example.nyatta.viewmodels.TownsViewModel
import com.example.nyatta.viewmodels.PropertyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NyattaNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    onboardingViewModel: OnboardingViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    apartmentViewModel: ApartmentViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    homeViewModel: HomeViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    townsViewModel: TownsViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    authViewModel: AuthViewModel = viewModel(factory = NyattaViewModelProvider.Factory),
    listingViewModel: ListingViewModel = viewModel(factory = NyattaViewModelProvider.Factory)
) {
    val authUiState by authViewModel.authUiState.collectAsState()
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val propertyUiState by propertyViewModel.uiState.collectAsState()
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()
    val deviceDetails by authViewModel.deviceDetails.collectAsState()

    val user = authUiState
    val onboardingUiData = onboardingUiState
    val propertyUiData = propertyUiState
    val deviceLocation = deviceDetails.gps
    val dataValidity = apartmentUiState.dataValidity
    val isAuthenticated = user.token.token.isNotEmpty()

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
                        deviceLocation = deviceLocation,
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
                topBar = {
                    if (!isAuthenticated) {
                        TopAppBar(
                            title = stringResource(id = R.string.create_account),
                            canNavigateBack = true,
                            navigateUp = { navController.popBackStack() }
                        )
                    } else if (isAuthenticated) {
                        TopAppBar(
                            title = stringResource(R.string.your_listings)
                        )
                    }
                },
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
                    if (isAuthenticated) {
                        Account(
                            authViewModel = authViewModel,
                            isLandlord = user.token.isLandlord
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
            authViewModel = authViewModel,
            apartmentViewModel = apartmentViewModel,
            propertyViewModel = propertyViewModel,
            user = user,
            onboardingUiState = onboardingUiData
        )
        paymentGraph(
            modifier = modifier,
            navController = navController,
            authViewModel = authViewModel
        )
        locationGraph(
            modifier = modifier,
            navController = navController,
            townsViewModel = townsViewModel,
            onboardingViewModel = onboardingViewModel,
            propertyViewModel = propertyViewModel,
            authViewModel = authViewModel,
            deviceLocation = deviceLocation,
            createPropertyState = propertyViewModel.createPropertyState,
            propertyType = onboardingUiData.type,
            user = user,
        )
        listingDetailsGraph(
            navController = navController,
            listingViewModel = listingViewModel
        )
        propertyOnboardingGraph(
            modifier = modifier,
            propertyViewModel = propertyViewModel,
            navController = navController,
            propertyType = onboardingUiData.type,
            propertyUiState = propertyUiData
        )
        apartmentOnboardingGraph(
            modifier = modifier,
            navController = navController,
            apartmentViewModel = apartmentViewModel,
            dataValidity = dataValidity,
            apartmentData = apartmentUiState,
            user = user.token,
            authViewModel = authViewModel,
            onboardingViewModel = onboardingViewModel,
            propertyData = propertyUiData,
            deviceLocation = deviceLocation,
            propertyType = onboardingUiState.type
        )
        loginGraph(
            authViewModel = authViewModel,
            navController = navController
        )
        propertyDetails(
            navController = navController
        )
    }
}