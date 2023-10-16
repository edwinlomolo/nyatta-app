package com.example.nyatta.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.ui.screens.home.Home
import com.example.nyatta.ui.screens.home.HomeUiState
import com.example.nyatta.ui.screens.home.HomeViewModel
import com.example.nyatta.ui.theme.NyattaTheme


@Composable
fun NyattaApp() {
    var selectedItem by remember { mutableIntStateOf(0) }
    val items = listOf("Home", "Favorite", "Add", "Account")
    val icons = listOf(Icons.Outlined.Home, Icons.Outlined.FavoriteBorder, Icons.Outlined.Add, Icons.Outlined.AccountCircle)
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        label = { Text(text = item) },
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

            when(val s = homeViewModel.homeUiState) {
                HomeUiState.Loading -> CircularProgressIndicator()
                is HomeUiState.ApolloError -> Text(text = s.errors[0].message)
                is HomeUiState.ApplicationError -> Text(text = "${s.error}")
                is HomeUiState.Success -> Home()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NyattaAppPreview() {
    NyattaTheme {
        NyattaApp()
    }
}