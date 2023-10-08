package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetTownsQuery
import com.example.nyatta.NyattaApp
import com.example.nyatta.data.towns.TownsRepository
import kotlinx.coroutines.launch

sealed interface TownsUiState {
    data class Success(val towns: List<GetTownsQuery.GetTown>?): TownsUiState
    object Loading: TownsUiState
    data class ApolloError(val errors: List<Error>): TownsUiState
    data class ApplicationError(val error: ApolloException): TownsUiState
}
class TownsViewModel(
    private val townsRepository: TownsRepository
): ViewModel() {
    var townsUiState: TownsUiState by mutableStateOf(TownsUiState.Loading)
        private set

    // Initialize state
    init {
        getTowns()
    }

    fun getTowns() {
        viewModelScope.launch {
            townsUiState = try {
                val response = townsRepository.getTowns()
                if (response.hasErrors()) {
                    TownsUiState.ApolloError(errors = response.errors!!)
                } else {
                    TownsUiState.Success(response.data?.getTowns)
                }
            } catch (e: ApolloException) {
                TownsUiState.ApplicationError(e)
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NyattaApp)
                val townsRepository = application.container.townsRepository
                TownsViewModel(townsRepository = townsRepository)
            }
        }
    }
}