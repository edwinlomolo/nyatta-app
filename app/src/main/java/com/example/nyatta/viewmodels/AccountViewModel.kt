package com.example.nyatta.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.example.nyatta.CreatePaymentMutation
import com.example.nyatta.data.auth.AuthRepository
import com.example.nyatta.data.model.User
import com.example.nyatta.network.NyattaGqlApiRepository
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
    data class ApolloError(val message: String?): AccountUiState
}

interface SignInUiState {
    object Success: SignInUiState

    object Loading: SignInUiState

    data class SignInError(val message: String?): SignInUiState
}

interface ICreatePayment {
    data class Success(val success: String? = null): ICreatePayment
    object Loading: ICreatePayment
    data class ApolloError(val message: String?): ICreatePayment
}

class AccountViewModel(
    private val authRepository: AuthRepository,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): ViewModel() {
    val landlordSubscriptionFee = "1500"
    val countryPhoneCode = mapOf("KE" to "+254")
    val countryCurrencyCode = mapOf("KE" to "KES")
    val defaultRegion = "KE"

    private val phoneUtil = PhoneNumberUtil.getInstance()

    private val _userDetails = MutableStateFlow(UserDetails())
    val userUiDetails: StateFlow<UserDetails> = _userDetails.asStateFlow()

    var createPaymentUiState: ICreatePayment by mutableStateOf(ICreatePayment.Success())
        private set

    var signInUiState: SignInUiState by mutableStateOf(SignInUiState.Success)
        private set

    fun signIn() {
        if (userUiDetails.value.phone.isNotEmpty() && userUiDetails.value.validDetails.phone) {
            signInUiState = SignInUiState.Loading
            viewModelScope.launch {
                signInUiState = try {
                    val phone = phoneUtil.parse(userUiDetails.value.phone, defaultRegion)
                    authRepository
                        .signIn(
                            phone.countryCode.toString()+phone.nationalNumber.toString()
                        )
                    SignInUiState.Success
                } catch (e: Throwable) {
                    SignInUiState.SignInError(e.localizedMessage)
                }
            }
        }
    }

    private fun validatePhone(phoneNumber: String): Boolean {
        return try {
            val phone = Phonenumber.PhoneNumber()
            phone.countryCode = countryPhoneCode[defaultRegion]?.toInt() ?: 0
            phone.nationalNumber = phoneNumber.toLong()
            return phoneUtil.isValidNumber(phone)
        } catch(e: Throwable) {
            false
        }
    }

    fun setPhone(phone: String) {
        _userDetails.update {
            val valid = it.validDetails.copy(phone = if (phone.isNotEmpty()) validatePhone(phone) else false)
            it.copy(
                phone = phone,
                validDetails =  valid
            )
        }
    }

    fun setDeviceLocation(location: LatLng) {
        _userDetails.update {
            it.copy(location = location)
        }
    }

    fun createPayment() {
        val phone = phoneUtil.parse(userUiDetails.value.phone, defaultRegion)
        createPaymentUiState = ICreatePayment.Loading
        viewModelScope.launch {
            createPaymentUiState = try {
                val response = nyattaGqlApiRepository
                    .createPayment(
                        phone = phone.countryCode.toString()+phone.nationalNumber.toString(),
                        amount = landlordSubscriptionFee
                    ).dataOrThrow()
                ICreatePayment.Success(response.createPayment.success)
            } catch (e: Throwable) {
                ICreatePayment.ApolloError(e.localizedMessage)
            }
        }
    }

    fun resetAccountState() {
        _userDetails.value = UserDetails()
    }

    init {
        resetAccountState()
    }
}

data class UserDetails(
    val phone: String = "",
    val validDetails: DataValidity = DataValidity(),
    val location: LatLng = LatLng(0.0,0.0)
)

data class DataValidity(
    val phone: Boolean = false
)