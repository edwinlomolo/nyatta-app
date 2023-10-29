package com.example.nyatta.compose.listings

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Error
import com.example.nyatta.data.listings.ListingsRepository
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface ListingsUiState {
    data class Success(val listings: Any?): ListingsUiState
    object Loading: ListingsUiState
    data class ApolloError(val errors: List<Error>): ListingsUiState
    data class ApplicationError(val error: IOException): ListingsUiState
}

class ListingsViewModel(
    private val listingsRepository: ListingsRepository
): ViewModel() {
    var uiState: ListingsUiState by mutableStateOf(ListingsUiState.Loading)

    init {
        getListings()
    }

    fun getListings() {
        viewModelScope.launch {
            uiState = try {
                val response = listingsRepository.getListings()
                if (response.hasErrors()) {
                    ListingsUiState.ApolloError(response.errors!!)
                } else {
                    ListingsUiState.Success(response.data?.getListings)
                }
            } catch(e: IOException) {
                ListingsUiState.ApplicationError(e)
            }
        }
    }
}