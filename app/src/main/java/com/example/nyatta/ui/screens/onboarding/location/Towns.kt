package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Towns(modifier: Modifier = Modifier) {
    val townsViewModel: TownsViewModel = viewModel(factory = TownsViewModel.Factory)
    val towns = townsViewModel.townSuggestions()

    when(val s = townsViewModel.townsUiState) {
        TownsUiState.Loading -> Text(text = "Loading", style = MaterialTheme.typography.titleMedium)
        is TownsUiState.Success -> Town(townsViewModel, towns!!, modifier)
        is TownsUiState.ApolloError -> Text(text = s.errors[0].message)
        is TownsUiState.ApplicationError -> Text(text = "${s.error}")
    }
}


@Composable
fun Town(townsViewModel: TownsViewModel, towns: List<GetTownsQuery.GetTown>, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            TownsTopBar(townsViewModel, towns)
        }
    ) {
        Surface(modifier = modifier.padding(it)) {}
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TownsTopBar(townsViewModel: TownsViewModel, towns: List<GetTownsQuery.GetTown>, modifier: Modifier = Modifier) {
    var active by rememberSaveable {
        mutableStateOf(false)
    }

    Box(
        modifier = modifier.fillMaxSize().semantics { isTraversalGroup = true }
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