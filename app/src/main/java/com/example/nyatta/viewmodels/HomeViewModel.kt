package com.example.nyatta.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyatta.data.hello.HelloRepository
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    data class Success(val hello: String?): HomeUiState
    data object Loading: HomeUiState
    data class ApolloError(val message: String?): HomeUiState
}

class HomeViewModel(
    private val helloRepository: HelloRepository,
): ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Success(null))

    // Run hello query
    fun getHello() {
        viewModelScope.launch {
            homeUiState = try {
                val response = helloRepository.getHello()
                when {
                    response.data != null -> {
                        val res = response.data!!.hello
                        HomeUiState.Success(res)
                    }
                    else -> HomeUiState.ApolloError(response.exception!!.localizedMessage)
                }
            } catch (e: Throwable) {
                HomeUiState.ApolloError(e.localizedMessage)
            }
        }
    }

    init {
        getHello()
    }
}