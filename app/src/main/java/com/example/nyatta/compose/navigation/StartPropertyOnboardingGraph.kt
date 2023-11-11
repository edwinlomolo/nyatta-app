package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navigation
import com.example.nyatta.R
import com.example.nyatta.compose.components.OnboardingBottomBar
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.startpropertyonboarding.Type
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.compose.user.UserSignUpDestination
import com.example.nyatta.viewmodels.AuthState
import com.example.nyatta.viewmodels.OnboardingUiState


object StartPropertyOnboardingGraph: Navigation {
    override val route = "onboarding/start"
    override val title = null
}

fun NavGraphBuilder.startPropertyOnboarding(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    authState: AuthState,
    onboardingUiState: OnboardingUiState
) {
    val validToProceed = onboardingUiState.validToProceed.type
    val isAuthenticated = authState.isAuthed
    val propertyType = onboardingUiState.type

    navigation(
        startDestination = if (!isAuthenticated) UserSignUpDestination.route else StartOnboardingDestination.route,
        route = StartPropertyOnboardingGraph.route
    ) {
        composable(UserSignUpDestination.route) {
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
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    SignUp(
                        navigateNext = { navController.navigate(it) }
                    )
                }
            }
        }
        composable(route = StartOnboardingDestination.route) {
            Scaffold(
                bottomBar = {
                    OnboardingBottomBar(
                        validToProceed = validToProceed,
                        navigateBack = { navController.popBackStack() },
                        onActionButtonClick = {
                            when (onboardingUiState.type) {
                                "Apartments Building" -> navController.navigate(
                                    PropertyOnboarding.route
                                )

                                else -> navController.navigate(ApartmentOnboarding.route)
                            }
                        },
                        actionButtonText = {
                            if (propertyType == "Apartments Building") {
                                Text(
                                    text = stringResource(R.string.describe_this_property),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            } else {
                                Text(
                                    text = stringResource(R.string.describe_apartment_unit),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    )
                }
            ) { innerPadding ->
                Surface(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    Type(
                        onboardingViewModel = onboardingViewModel
                    )
                }
            }
        }
    }
}