package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ListItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.GetTownsQuery
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Towns(modifier: Modifier = Modifier) {
    val townsViewModel: TownsViewModel = viewModel(factory = TownsViewModel.Factory)

    when(val s = townsViewModel.townsUiState) {
        TownsUiState.Loading -> Text(text = "Loading", style = MaterialTheme.typography.titleMedium)
        is TownsUiState.Success -> Town(s.towns!!, modifier)
        is TownsUiState.ApolloError -> Text(text = s.errors[0].message)
        is TownsUiState.ApplicationError -> Text(text = "${s.error}")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Town(towns: List<GetTownsQuery.GetTown>, modifier: Modifier = Modifier) {
    Onboarding(modifier = modifier) {
        LazyColumn {
            items(towns) { town ->
                ListItem(
                    headlineText = {
                        Text(text = town.town)
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TownsPreview() {
    NyattaTheme {
        Towns()
    }
}