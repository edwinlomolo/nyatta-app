package com.example.nyatta.ui.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.CreateUserMutation
import com.example.nyatta.data.auth.AuthRepository
import com.example.nyatta.model.AuthRequest
import kotlinx.coroutines.launch

interface AccountUiState {
    data class Auth(val token: CreateUserMutation.CreateUser?): AccountUiState
    object Loading: AccountUiState
    data class ApolloError(val errors: List<Error>): AccountUiState
    data class ApplicationError(val error: ApolloException): AccountUiState
}
class AccountViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    var accUiState: AccountUiState by mutableStateOf(AccountUiState.Loading)
        private set

    fun signUser(request: AuthRequest) {
        viewModelScope.launch {
            accUiState = try {
                val response = authRepository.signUser(request)
                if (response.hasErrors()) {
                    AccountUiState.ApolloError(errors = response.errors!!)
                } else {
                    AccountUiState.Auth(response.data?.createUser)
                }
            } catch (e: ApolloException) {
                AccountUiState.ApplicationError(e)
            }
        }
    }

    fun checkAuth(accState: AccountUiState = accUiState): Boolean {
        return accState is AccountUiState.Auth
    }
}