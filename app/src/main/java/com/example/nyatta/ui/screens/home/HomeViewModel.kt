package com.example.nyatta.ui.screens.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.NyattaApp
import com.example.nyatta.data.hello.HelloRepository
import kotlinx.coroutines.launch
import com.apollographql.apollo3.api.Error

sealed interface HomeUiState {
    data class Success(val hello: String?): HomeUiState
    object Loading: HomeUiState
    data class ApolloError(val errors: List<Error>): HomeUiState
    data class ApplicationError(val error: ApolloException): HomeUiState
}

class HomeViewModel(
    private val helloRepository: HelloRepository
): ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)

    // Listing view
    val titles = listOf("Photos", "Amenities", "Reviews")
    var tabState by mutableStateOf(0)
        private set

    // Run hello query
    fun getHello() {
        viewModelScope.launch {
            homeUiState = try {
                val response = helloRepository.getHello()
                if (response.hasErrors()) {
                    HomeUiState.ApolloError(errors = response.errors!!)
                } else {
                    HomeUiState.Success(response.data?.hello)
                }
            } catch (e: ApolloException) {
                HomeUiState.ApplicationError(e)
            }
        }
    }

    // Listing view state
    fun updateTabState(state: Int) {
        tabState = state
    }

    // Initialize state
    init {
        getHello()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NyattaApp)
                val helloRepository = application.container.helloRepository
                HomeViewModel(helloRepository = helloRepository)
            }
        }
    }
}