package com.example.nyatta.compose.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.GetTownsQuery
import com.example.nyatta.ui.components.Loading
import com.example.nyatta.ui.components.ActionButton
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.payment.PayDestination
import com.example.nyatta.ui.theme.NyattaTheme

object TownDestination: Navigation {
    override val route = "property/town"
    override val title = null
}

@Composable
fun Towns(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    townsViewModel: TownsViewModel = viewModel()
) {
    val towns = townsViewModel.townSuggestions()

    when(val s = townsViewModel.townsUiState) {
        TownsUiState.Loading -> Loading()
        is TownsUiState.Success -> Town(townsViewModel = townsViewModel, towns = towns!!, navigateNext = navigateNext, navigateUp = navigateUp, modifier = modifier)
        is TownsUiState.ApolloError -> Text(text = s.errors[0].message)
        is TownsUiState.ApplicationError -> Text(text = "${s.error}")
    }
}


@Composable
fun Town(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    towns: List<GetTownsQuery.GetTown>,
    townsViewModel: TownsViewModel
) {
    Scaffold(
        topBar = {
            TownsTopBar(
                townsViewModel = townsViewModel,
                towns = towns,
                navigateUp = navigateUp,
                navigateNext = navigateNext
            )
        }
    ) {
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(it)) {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TownsTopBar(
    modifier: Modifier = Modifier,
    towns: List<GetTownsQuery.GetTown>,
    navigateNext: (String) -> Unit = {},
    navigateUp: () -> Unit = {},
    townsViewModel: TownsViewModel
) {
    var active by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            placeholder = { Text("Search town") },
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = townsViewModel.searchQuery,
            onQueryChange = { townsViewModel.updateSearchQuery(it) },
            onSearch = { townsViewModel.updateSearchQuery(it) },
            active = true,
            leadingIcon = {
                IconButton(
                    onClick = navigateUp
                ) {
                    Icon(
                        Icons.Outlined.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            onActiveChange = { active = it }
        ) {
            LazyColumn {
                items(towns) { town ->
                    ListItem(
                        headlineContent = {
                            Text(text = town.town)
                        },
                        modifier = Modifier
                            .clickable {
                                townsViewModel.updateSearchQuery(town.town)
                                active = false
                            }
                            .fillMaxWidth()
                    )
                }
                if (towns.isEmpty()) item { Text("Can't find town", style = MaterialTheme.typography.titleSmall, modifier = Modifier.padding(8.dp)) }
            }
            if (towns.isNotEmpty())
                ActionButton(
                    onClick = { navigateNext(PayDestination.route) },
                    modifier = Modifier.padding(4.dp),
                    text = "Proceed"
                ) {
                    Icon(
                        Icons.TwoTone.ArrowForward,
                        contentDescription = "Proceed"
                    )
                }
        }
    }
}

@Preview
@Composable
fun TownsPreview() {
    NyattaTheme {
        Towns()
    }
}