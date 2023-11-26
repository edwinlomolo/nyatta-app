package com.example.nyatta.viewmodels

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
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
            ApartmentViewModel(
                nyattaApplication().container.restApiRepository,
                nyattaApplication().container.nyattaGqlApiRepository
            )
        }
        // Initializer for PropertyViewModel
        initializer {
            PropertyViewModel(
                nyattaApplication().container.restApiRepository,
                nyattaApplication().container.nyattaGqlApiRepository
            )
        }
        // Initializer for TownsViewModel
        initializer {
            TownsViewModel(nyattaApplication().container.townsRepository)
        }
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(
                nyattaApplication().container.nyattaGqlApiRepository
            )
        }
        // Initializer for AuthViewModel
        initializer {
            AuthViewModel(
                nyattaApplication().container.authRepository,
                nyattaApplication().container.restApiRepository,
                nyattaApplication().container.nyattaGqlApiRepository
            )
        }
        // Initializer for ListingViewModel
        initializer {
            ListingViewModel(
                nyattaApplication().container.nyattaGqlApiRepository,
                this.createSavedStateHandle()
            )
        }
    }
}

// Extension to query application object and return instance of NyattaApp
fun CreationExtras.nyattaApplication(): NyattaApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NyattaApp)