package com.example.nyatta.compose.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.startpropertyonboarding.Type
import com.example.nyatta.compose.user.SignUp
import com.example.nyatta.compose.user.UserSignUpDestination
import com.example.nyatta.data.model.User
import com.example.nyatta.viewmodels.Auth
import com.example.nyatta.viewmodels.OnboardingUiState


object StartPropertyOnboardingGraph: Navigation {
    override val route = "onboarding/start"
    override val title = null
}

@OptIn(ExperimentalMaterial3Api::class)
fun NavGraphBuilder.startPropertyOnboarding(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onboardingViewModel: OnboardingViewModel,
    user: Auth,
    onboardingUiState: OnboardingUiState
) {
    val validToProceed = onboardingUiState.validToProceed.type
    val isAuthenticated = user.token.token.isNotEmpty()
    val propertyType = onboardingUiState.type

    navigation(
        startDestination = if (!isAuthenticated) UserSignUpDestination.route else StartOnboardingDestination.route,
        route = StartPropertyOnboardingGraph.route
    ) {
        composable(UserSignUpDestination.route) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = stringResource(id = R.string.start_your_setup)
                    )
                },
                bottomBar = {
                    NavigationBar {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentDestination = navBackStackEntry?.destination

                        navigationItems.forEach { screen ->
                            NavigationBarItem(
                                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
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
                topBar = {
                    TopAppBar(
                        title = stringResource(id = R.string.what_to_add)
                    )
                },
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
                            val buttonText = when(propertyType) {
                                "Apartments Building" -> R.string.describe_this_property
                                "Unit" -> R.string.describe_apartment_unit
                                "Condo" -> R.string.setup_condo
                                else -> R.string.setup
                            }
                            Text(
                                text = stringResource(buttonText),
                                style = MaterialTheme.typography.labelSmall
                            )
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