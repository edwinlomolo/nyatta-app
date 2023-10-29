package com.example.nyatta.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.nyatta.NyattaApp
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.model.User
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AuthViewModel(
    private val authRepository: OfflineAuthRepository
): ViewModel() {
    var authUiState: StateFlow<AuthUiState> = authRepository
        .getUser()
        .filterNotNull()
        .map {
            AuthUiState(
                user = it
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = AuthUiState()
        )
        private set

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as NyattaApp)
                val authRepository = application.container.authRepository
                AuthViewModel(authRepository)
            }
        }
    }
}

data class AuthUiState(val user: List<User> = listOf())