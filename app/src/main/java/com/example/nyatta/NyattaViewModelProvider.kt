package com.example.nyatta

import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.nyatta.ui.screens.user.AccountViewModel
import com.example.nyatta.ui.screens.OnboardingViewModel
import com.example.nyatta.ui.screens.apartment.ApartmentViewModel
import com.example.nyatta.ui.screens.home.HomeViewModel
import com.example.nyatta.ui.screens.location.TownsViewModel
import com.example.nyatta.ui.screens.property.PropertyViewModel

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
            AccountViewModel(nyattaApplication().container.authRepository)
        }
        // Initializer for TownsViewModel
        initializer {
            TownsViewModel(nyattaApplication().container.townsRepository)
        }
        // Initializer for HomeViewModel
        initializer {
            HomeViewModel(nyattaApplication().container.helloRepository)
        }
    }
}

// Extension to query application object and return instance of NyattaApp
fun CreationExtras.nyattaApplication(): NyattaApp =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as NyattaApp)