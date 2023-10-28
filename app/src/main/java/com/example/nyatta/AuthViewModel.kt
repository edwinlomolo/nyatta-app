package com.example.nyatta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val authUiState: StateFlow<AuthUiState> = authRepository
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

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class AuthUiState(val user: List<User> = listOf())