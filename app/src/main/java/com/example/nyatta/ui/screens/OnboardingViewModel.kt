package com.example.nyatta.ui.screens

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OnboardingViewModel: ViewModel() {
    val propertyOptions = listOf("Apartments Building", "Apartment"/*, "Bungalow"*/)
    private val _uiState = MutableStateFlow("")
    val uiState: StateFlow<String> = _uiState.asStateFlow()

    fun setType(type: String) {
        _uiState.value = type
    }
}