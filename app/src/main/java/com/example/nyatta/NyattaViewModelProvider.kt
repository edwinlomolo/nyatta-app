package com.example.nyatta

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.nyatta.ui.screens.onboarding.OnboardingViewModel
import com.example.nyatta.ui.screens.onboarding.apartment.ApartmentViewModel
import com.example.nyatta.ui.screens.onboarding.property.PropertyViewModel

// Factory for entire app view models
object NyattaViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for OnboardingViewModel
        initializer {
            OnboardingViewModel()
        }
        // Initializer for ApartmentViewModel
        initializer {
            ApartmentViewModel()
        }
        // Initializer for PropertyViewModel
        initializer {
            PropertyViewModel()
        }
    }
}

// Extension to query application object and return instance of NyattaApp
fun CreationExtras.nyattaApplication(): NyattaApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NyattaApp)