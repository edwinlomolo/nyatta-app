package com.example.nyatta.viewmodels

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
    authRepository: OfflineAuthRepository
): ViewModel() {
    val authUiState: StateFlow<Auth> = authRepository
        .getUser()
        .filterNotNull()
        .map {
            if (it.isNotEmpty()) {
                Auth(it[0])
            } else {
                Auth()
            }
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = Auth(),
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class Auth(val user: User = User())
