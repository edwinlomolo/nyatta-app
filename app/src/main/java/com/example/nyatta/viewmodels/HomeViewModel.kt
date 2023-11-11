package com.example.nyatta.viewmodels

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.data.hello.HelloRepository
import kotlinx.coroutines.launch
import com.apollographql.apollo3.api.Error
import com.example.nyatta.data.apollographql.ApolloRepository
import com.example.nyatta.data.auth.OfflineAuthRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

sealed interface HomeUiState {
    data class Success(val hello: String?): HomeUiState
    object Loading: HomeUiState
    data class ApolloError(val errors: List<Error>): HomeUiState
    data class ApplicationError(val error: ApolloException): HomeUiState
}

class HomeViewModel(
    private val helloRepository: HelloRepository,
    authRepository: OfflineAuthRepository,
    private val apolloRepository: ApolloRepository
): ViewModel() {
    var homeUiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
    private val _auth: StateFlow<AuthState> = authRepository
        .getUser()
        .filterNotNull()
        .map {
            AuthState(
                isAuthed = it[0].token.isNotEmpty(),
                token = it[0].token,
                isLandlord = it[0].isLandlord,
                phone = it[0].phone
            )
        }
        .stateIn(
            scope = viewModelScope,
            initialValue = AuthState(),
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS)
        )

    // Run hello query
    fun getHello() {
        Log.d("tokn", "${_auth.value.token}")
        viewModelScope.launch {
            homeUiState = try {
                val response = apolloRepository.getHello(_auth.value.token)
                if (response.hasErrors()) {
                    HomeUiState.ApolloError(errors = response.errors!!)
                } else {
                    HomeUiState.Success(response.data?.hello)
                }
            } catch (e: ApolloException) {
                HomeUiState.ApplicationError(e)
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    init {
        getHello()
    }
}