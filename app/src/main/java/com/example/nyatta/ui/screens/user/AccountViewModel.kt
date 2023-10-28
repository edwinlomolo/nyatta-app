package com.example.nyatta.ui.screens.user

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.data.auth.AuthRepository
import com.example.nyatta.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

interface AccountUiState {
    data class Auth(val user: User? = null): AccountUiState
    object Loading: AccountUiState
    object NotLoading: AccountUiState
    data class ApolloError(val errors: List<Error>): AccountUiState
    data class ApplicationError(val error: ApolloException): AccountUiState
}
class AccountViewModel(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _userDetails = MutableStateFlow(UserDetails())
    val userUiDetails: StateFlow<UserDetails> = _userDetails.asStateFlow()

    var accUiState: AccountUiState by mutableStateOf(AccountUiState.Auth())
        private set

    fun signIn() {
        accUiState = AccountUiState.Loading
        viewModelScope.launch {
            accUiState = try {
                val response = authRepository.signUp(userUiDetails.value.phone)
                if (response.hasErrors()) {
                    AccountUiState.ApolloError(response.errors!!)
                } else {
                    val res = response.data?.signIn
                    authRepository.signUser(
                        user = User(
                            isLandlord = res!!.user.is_landlord,
                            token = res!!.Token,
                            phone = res!!.user.phone
                        )
                    )
                    AccountUiState.Auth(
                        user = User(
                            phone = res!!.user.phone,
                            isLandlord = res!!.user.is_landlord,
                            token = res!!.Token
                        )
                    )
                }
            } catch(e: ApolloException) {
                AccountUiState.ApplicationError(e)
            }
        }
    }

    fun setPhone(phone: String) {
        _userDetails.update {
            it.copy(phone = phone)
        }
    }
}

data class UserDetails(
    val phone: String = ""
)