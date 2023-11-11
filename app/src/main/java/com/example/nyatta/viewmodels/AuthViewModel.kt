package com.example.nyatta.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyatta.data.auth.OfflineAuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: OfflineAuthRepository
): ViewModel() {
    private val _auth = MutableStateFlow(AuthState())
    val auth: StateFlow<AuthState> = _auth.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getUser().stateIn(
                scope = viewModelScope,
                initialValue = listOf(),
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            ).collect { user ->
                if (user.isNotEmpty()) {
                    _auth.update {
                        it.copy(
                            token = user[0].token,
                            phone = user[0].phone,
                            isAuthed = user[0].token.isNotEmpty(),
                            isLandlord = user[0].isLandlord
                        )
                    }
                }
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class AuthState(
    val isAuthed: Boolean = false,
    val phone: String = "",
    val token: String = "",
    val isLandlord: Boolean = false
)