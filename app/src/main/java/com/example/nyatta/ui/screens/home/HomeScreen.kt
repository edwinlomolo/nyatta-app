package com.example.nyatta.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.components.Loading
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.listing.ListingCard
import com.example.nyatta.ui.screens.onboarding.property.StartOnboardingDestination
import com.example.nyatta.ui.screens.onboarding.user.UserSignUpDestination
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

object HomeDestination: Navigation {
    override val route = "home"
    override val title = "Vacant homes"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit = {},
    currentRoute: String? = null,
    onNavigateToListing: (Int) -> Unit = {}
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val s = homeViewModel.homeUiState
    val showBars = s !is HomeUiState.Loading
    val errored = (s is HomeUiState.ApolloError || s is HomeUiState.ApplicationError)

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (showBars && !errored) TopAppBar(scrollBehavior = scrollBehavior, listingsCount = 5, title = stringResource(R.string.vacant_homes))
        },
        bottomBar = {
            if (!errored) BottomBar(
                onNavigateTo = onNavigateTo,
                currentRoute = currentRoute
            )
        }
    ) { it ->
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .fillMaxWidth()
                .padding(it)
        ) {
            when(s) {
                HomeUiState.Loading -> Loading()
                is HomeUiState.ApolloError -> Text(text = s.errors[0].message)
                is HomeUiState.ApplicationError -> Text(text = "${s.error}")
                is HomeUiState.Success -> Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                    repeat(5) {
                        ListingCard(
                            modifier = Modifier
                                .clickable{ onNavigateToListing(it) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit = {},
    currentRoute: String? = null
) {
    NavigationBar(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.inverseOnSurface
    ) {
        NavigationBarItem(
            selected = currentRoute == HomeDestination.route,
            onClick = { onNavigateTo(HomeDestination.route) },
            icon = {
                Icon(
                    Icons.Outlined.Home,
                    modifier = Modifier.size(28.dp),
                    contentDescription = null
                )
            },
            label = { Text("Home") }
        )
        NavigationBarItem(
            selected = currentRoute == StartOnboardingDestination.route,
            onClick = { onNavigateTo(StartOnboardingDestination.route) },
            icon = {
                Icon(
                    painterResource(R.drawable.plus),
                    modifier = Modifier.size(40.dp),
                    contentDescription = null
                )
            },
        )
        NavigationBarItem(
            selected = currentRoute == UserSignUpDestination.route,
            // TODO auth flow here
            onClick = { onNavigateTo(UserSignUpDestination.route) },
            icon = {
                Icon(
                    Icons.Outlined.AccountCircle,
                    modifier = Modifier.size(28.dp),
                    contentDescription = null
                )
            },
            label = { Text("Account") }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    listingsCount: Int = 0,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        actions = actions,
        title = {
            if (title != null || listingsCount > 0) {
                Column {
                    if (title != null) {
                        Text(
                            text = title,
                            style = TextStyle(fontFamily = MabryFont, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        )
                    }
                    if (listingsCount > 0) {
                        Text(
                            text = stringResource(R.string.listings_in_your_area, listingsCount),
                            style = TextStyle(
                                fontFamily = MabryFont,
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }
            }
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        }
    )
}


@Preview
@Composable
fun HomePreview() {
    NyattaTheme {
        Home()
    }
}