package com.example.nyatta.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.ui.screens.home.Home
import com.example.nyatta.ui.screens.home.HomeUiState
import com.example.nyatta.ui.screens.home.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NyattaApp() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Favorite", "Add")
    val icons = listOf(Icons.Filled.Home, Icons.Filled.Favorite, Icons.Filled.AccountCircle)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = { Icon(icons[index], contentDescription = item) }
                    )
                }
            }
        }
    ) {
        Surface(modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(it)
        ) {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)

            when(val s = homeViewModel.homeUiState) {
                HomeUiState.Loading -> CircularProgressIndicator()
                is HomeUiState.ApolloError -> Text(text = s.errors[0].message)
                is HomeUiState.ApplicationError -> Text(text = "${s.error}")
                is HomeUiState.Success -> Home()
            }
        }
    }
}