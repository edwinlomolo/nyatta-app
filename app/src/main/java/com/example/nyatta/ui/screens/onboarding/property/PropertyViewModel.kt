package com.example.nyatta.ui.screens.onboarding.property

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PropertyData(
    val description: String = "",
    val caretaker: CaretakerData = CaretakerData()
)

data class CaretakerData(
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = ""
)

class PropertyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PropertyData())
    val uiState: StateFlow<PropertyData> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update { state ->
            state.copy(description = name)
        }
    }
    fun resetState() {
        _uiState.value = PropertyData()
    }

    init {
        resetState()
    }
}