package com.example.nyatta.viewmodels

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.nyatta.NyattaApp

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
        // Initializer for AccountViewModel
        initializer {
            AccountViewModel(
                nyattaApplication().container.authRepository,
                nyattaApplication().container.client
            )
        }
        // Initializer for TownsViewModel
        initializer {
            TownsViewModel(nyattaApplication().container.townsRepository)
        }
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(nyattaApplication().container.helloRepository)
        }
        // Initializer for AuthViewModel
        initializer {
            AuthViewModel(nyattaApplication().container.authRepository)
        }
    }
}

// Extension to query application object and return instance of NyattaApp
fun CreationExtras.nyattaApplication(): NyattaApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NyattaApp)