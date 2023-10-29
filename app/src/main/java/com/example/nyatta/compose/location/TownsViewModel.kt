package com.example.nyatta.compose.location

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetTownsQuery
import com.example.nyatta.data.towns.TownsRepository
import com.example.nyatta.ui.screens.location.TownsUiState.Success
import com.example.nyatta.ui.screens.location.TownsUiState.ApolloError
import com.example.nyatta.ui.screens.location.TownsUiState.ApplicationError
import com.example.nyatta.ui.screens.location.TownsUiState.Loading
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed interface TownsUiState {
    data class Success(val towns: List<GetTownsQuery.GetTown>? = null): TownsUiState
    object Loading: TownsUiState
    data class ApolloError(val errors: List<Error>): TownsUiState
    data class ApplicationError(val error: ApolloException): TownsUiState
}
class TownsViewModel(
    private val townsRepository: TownsRepository
): ViewModel() {
    var townsUiState: TownsUiState by mutableStateOf(Loading)
        private set
    private val _townsList = MutableStateFlow(Success())
    val townsList: StateFlow<Success> = _townsList.asStateFlow()
    var searchQuery by mutableStateOf("")
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
                    ApolloError(errors = response.errors!!)
                } else {
                    _townsList.update { currentState ->
                        currentState.copy(towns = response.data?.getTowns)
                    }
                    Success(response.data?.getTowns)
                }
            } catch (e: ApolloException) {
                ApplicationError(e)
            }
        }
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
    }

    fun townSuggestions(): List<GetTownsQuery.GetTown>? {
        return townsList.value.towns?.filter { it.town.contains(searchQuery, ignoreCase = true) }
    }
}