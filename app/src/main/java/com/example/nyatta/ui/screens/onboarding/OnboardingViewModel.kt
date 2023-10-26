package com.example.nyatta.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class OnboardingUiState(
    var type: String = "",
)

class OnboardingViewModel: ViewModel() {
    val propertyOptions = listOf("Apartments Building", "Apartment"/*, "Bungalow"*/)
    private val _uiState = MutableStateFlow(OnboardingUiState(type = propertyOptions[0]))
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun setType(type: String) {
        _uiState.value.type = type
    }

    fun resetState() {
        _uiState.value = OnboardingUiState(type = propertyOptions[0])
    }

    init {
        resetState()
    }
}