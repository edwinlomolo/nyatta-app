package com.example.nyatta.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: OfflineAuthRepository
): ViewModel() {
    private val _auth = MutableStateFlow(AuthState())
    val auth: StateFlow<AuthState> = _auth.asStateFlow()

    init {
        viewModelScope.launch {
            authRepository.getUser().shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            ).collect { user ->
                if (user.isNotEmpty()) {
                    val response = authRepository.signUp(user[0].phone)
                    if (response.hasErrors()) {
                        _auth.update {
                            it.copy(
                                token = user[0].token,
                                phone = user[0].phone,
                                isAuthed = user[0].token.isNotEmpty(),
                                isLandlord = user[0].isLandlord
                            )
                        }
                    } else {
                        val res = response.data?.signIn
                        authRepository.signUser(
                            user = User(
                                token = res!!.Token,
                                phone = res.user.phone,
                                isLandlord = res.user.is_landlord
                            )
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