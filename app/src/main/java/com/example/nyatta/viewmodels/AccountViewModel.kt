package com.example.nyatta.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.data.auth.AuthRepository
import com.example.nyatta.data.model.User
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber

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
    val countryCode = "+254"
    private val defaultRegion = "KE"
    private val phoneUtil = PhoneNumberUtil.getInstance()
    private val _userDetails = MutableStateFlow(UserDetails())
    val userUiDetails: StateFlow<UserDetails> = _userDetails.asStateFlow()

    var accUiState: AccountUiState by mutableStateOf(AccountUiState.Auth())
        private set

    fun signIn(cb: () -> Unit = {}) {
        if (userUiDetails.value.phone.isNotEmpty() && userUiDetails.value.validDetails) {
            accUiState = AccountUiState.Loading
            viewModelScope.launch {
                accUiState = try {
                    val phone = phoneUtil.parse(userUiDetails.value.phone, defaultRegion)
                    val response = authRepository.signUp(phone.countryCode.toString()+phone.nationalNumber.toString())
                    if (response.hasErrors()) {
                        AccountUiState.ApolloError(response.errors!!)
                    } else {
                        val res = response.data?.signIn
                        authRepository.signUser(
                            user = User(
                                isLandlord = res!!.user.is_landlord,
                                token = res.Token,
                                phone = res.user.phone
                            )
                        )
                        AccountUiState.Auth(
                            user = User(
                                phone = res.user.phone,
                                isLandlord = res.user.is_landlord,
                                token = res.Token
                            )
                        )
                    }
                } catch (e: ApolloException) {
                    AccountUiState.ApplicationError(e)
                }
                cb()
            }
        }
    }

    private fun validatePhone(phoneNumber: String): Boolean {
        return try {
            val phone = Phonenumber.PhoneNumber()
            phone.countryCode = countryCode.toInt()
            phone.nationalNumber = phoneNumber.toLong()
            return phoneUtil.isValidNumber(phone)
        } catch(e: Throwable) {
            false
        }
    }

    fun setPhone(phone: String) {
        _userDetails.update {
            it.copy(
                phone = phone,
                validDetails = if (phone.isNotEmpty()) validatePhone(phone) else true
            )
        }
    }

    fun setDeviceLocation(location: LatLng) {
        _userDetails.update {
            it.copy(location = location)
        }
    }

    init {
        _userDetails.value = UserDetails()
    }
}

data class UserDetails(
    val phone: String = "",
    val validDetails: Boolean = true,
    val location: LatLng = LatLng(0.0,0.0)
)