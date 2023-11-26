package com.example.nyatta.compose.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.GetNearByUnitsQuery
import com.example.nyatta.R
import com.example.nyatta.compose.components.Loading
import com.example.nyatta.compose.components.NetworkError
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.listing.ListingCard
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.HomeViewModel
import com.google.android.gms.maps.model.LatLng

object HomeDestination: Navigation {
    override val route = "home"
    override val title = "Vacant homes"
}

private sealed interface IGetNearByListings {
    data object Loading: IGetNearByListings
    data class Success(val listings: List<GetNearByUnitsQuery.GetNearByUnit>?): IGetNearByListings
    data class ApolloError(val message: String?): IGetNearByListings
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(
    modifier: Modifier = Modifier,
    onNavigateToListing: (String) -> Unit = {},
    homeViewModel: HomeViewModel = viewModel(),
    deviceLocation: LatLng = LatLng(0.0, 0.0)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    var state by remember { mutableStateOf<IGetNearByListings>(IGetNearByListings.Loading) }
    val showBars = state !is IGetNearByListings.Loading
    val errored = (state is IGetNearByListings.ApolloError)

    LaunchedEffect(key1 = deviceLocation.latitude, key2 = deviceLocation.longitude) {
        state = try {
            val res = homeViewModel.getNearByListings(deviceLocation)
            IGetNearByListings.Success(res.getNearByUnits)
        } catch(e: Throwable) {
            IGetNearByListings.ApolloError(e.localizedMessage)
        }
    }

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            val listingsCount = if (state is IGetNearByListings.Success && (state as IGetNearByListings.Success).listings != null) (state as IGetNearByListings.Success).listings!!.size else 0
            if (showBars && !errored) TopAppBar(scrollBehavior = scrollBehavior, listingsCount = listingsCount, title = stringResource(R.string.vacant_homes))
        },
    ) { it ->
        Surface(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .fillMaxWidth()
                .padding(it)
        ) {
            when(val s = state) {
                IGetNearByListings.Loading -> Loading()
                is IGetNearByListings.Success -> {
                    LazyColumn {
                        items(items  = s.listings!!, key = { it.id }) { unit ->
                            ListingCard(
                                unit = unit,
                                modifier = Modifier
                                    .clickable { onNavigateToListing(unit.id.toString()) }
                            )
                        }

                        item {
                            if (s.listings.isEmpty()) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ){
                                    Image(
                                        painter = painterResource(R.drawable.homestead_icon),
                                        contentDescription = stringResource(R.string.home),
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .size(20.dp)
                                    )
                                    Text(
                                        text = stringResource(R.string.no_listings),
                                        style = MaterialTheme.typography.bodyLarge,
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                            }
                        }
                    }
                }
                is IGetNearByListings.ApolloError -> NetworkError(errorString = s.message!!)
            }
        }
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
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    if (listingsCount > 0) {
                        Text(
                            text = stringResource(R.string.listings_in_your_area, listingsCount),
                            style = MaterialTheme.typography.bodyLarge
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