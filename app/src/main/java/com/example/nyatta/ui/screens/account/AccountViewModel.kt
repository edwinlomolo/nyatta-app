package com.example.nyatta.ui.screens.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class Location(
    val lat: Double = 0.0,
    val lon: Double = 0.0
)

data class User(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = ""
)

data class AccountUiState(
    var location: Location = Location(),
    var user: User = User()
)
class AccountViewModel: ViewModel() {
    var accUiState by mutableStateOf(AccountUiState())
        private set

    fun setLocation(lat: Double, lon: Double) {
        accUiState.location = Location(lat, lon)
    }

    fun isValidUser(user: User = accUiState.user) {
        return with(user) {
            firstName.isNotBlank() &&
                    lastName.isNotBlank() &&
                    email.isNotBlank() &&
                    phone.isNotBlank()
        }
    }
}