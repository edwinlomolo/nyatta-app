package com.example.nyatta.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnboardingUiState(
    val type: String = "",
)

class OnboardingViewModel: ViewModel() {
    val propertyOptions = listOf("Apartments Building", "Apartment"/*, "Bungalow"*/)
    private val _uiState = MutableStateFlow(OnboardingUiState(type = propertyOptions[0]))
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun setType(type: String) {
        _uiState.update { state ->
            state.copy(type = type)
        }
    }

    fun resetState() {
        _uiState.value = OnboardingUiState(type = propertyOptions[0])
    }

    init {
        resetState()
    }
}