package com.example.nyatta.ui.screens

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.apollographql.apollo3.api.ApolloResponse
import com.example.nyatta.HelloQuery
import com.example.nyatta.Nyatta
import com.example.nyatta.data.HelloRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface HomeUiState {
    data class Success(val hello: ApolloResponse<HelloQuery.Data>): HomeUiState
    object Loading: HomeUiState
    object Error: HomeUiState
}
class HomeViewModel(
    private val helloRepository: HelloRepository
): ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set

    // Initialize state
    init {
        getHello()
    }

    // Run hello query
    fun getHello() {
        viewModelScope.launch {
            homeUiState = try {
                HomeUiState.Success(helloRepository.getHello())
            } catch (e: IOException) {
                HomeUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as Nyatta)
                val helloRepository = application.container.helloRepository
                HomeViewModel(helloRepository = helloRepository)
            }
        }
    }
}