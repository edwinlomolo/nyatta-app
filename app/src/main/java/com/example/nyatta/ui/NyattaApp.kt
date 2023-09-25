package com.example.nyatta.ui

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.ui.screens.HomeScreen
import com.example.nyatta.ui.screens.HomeUiState
import com.example.nyatta.ui.screens.HomeViewModel
import com.example.nyatta.ui.screens.ListingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NyattaApp() {
    Scaffold {
        Surface(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {
            val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
            val listingsViewModel: ListingsViewModel = viewModel(factory = ListingsViewModel.Factory)
            val s = listingsViewModel.uiState

            Log.d("ListingsState", "${s}")

            when(val s = homeViewModel.homeUiState) {
                HomeUiState.Loading -> CircularProgressIndicator()
                is HomeUiState.ApolloError -> Text(text = s.errors[0].message)
                is HomeUiState.ApplicationError -> Text(text = "${s.error}")
                is HomeUiState.Success -> HomeScreen("${s.hello}")
            }
        }
    }
}