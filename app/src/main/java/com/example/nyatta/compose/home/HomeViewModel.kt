package com.example.nyatta.compose.home

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
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

    // Initialize state
    init {
        getHello()
    }
}