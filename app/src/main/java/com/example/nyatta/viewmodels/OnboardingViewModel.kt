package com.example.nyatta.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class OnboardingUiState(
    val type: String? = null,
    val validToProceed: OnboardingDataValidity = OnboardingDataValidity()
)

data class OnboardingDataValidity(
    val type: Boolean = false
)

class OnboardingViewModel: ViewModel() {
    val propertyOptions = listOf("Apartments Building", "Apartment"/*, "Bungalow"*/)
    private val _uiState = MutableStateFlow(OnboardingUiState())
    val uiState: StateFlow<OnboardingUiState> = _uiState.asStateFlow()

    fun setType(type: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(type = type.isNotEmpty())
            it.copy(
                type = type,
                validToProceed = valid
            )
        }
    }
}