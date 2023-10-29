package com.example.nyatta.compose.apartment

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class ApartmentData(
    val description: String = "",
    val associatedTo: Boolean = false
)

class ApartmentViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ApartmentData())
    val uiState: StateFlow<ApartmentData> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update { state ->
            state.copy(description = name)
        }
    }

    fun setAssociatedTo(associate: Boolean) {
        _uiState.update {
            it.copy(associatedTo = associate)
        }
    }
}