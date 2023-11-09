package com.example.nyatta.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Error
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.CreatePaymentMutation
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
    data class ApolloError(val errors: List<Error>): AccountUiState
    data class ApplicationError(val error: ApolloException): AccountUiState
}

interface ICreatePayment {
    data class Success(val success: String? = null): ICreatePayment
    object Loading: ICreatePayment
    data class ApolloError(val errors: List<Error>): ICreatePayment
    data class ApplicationError(val error: ApolloException): ICreatePayment
}

class AccountViewModel(
    private val authRepository: AuthRepository,
    private val client: ApolloClient
): ViewModel() {
    val landlordSubscriptionFee = "1500"
    val countryPhoneCode = mapOf("KE" to "+254")
    val countryCurrencyCode = mapOf("KE" to "KES")
    val defaultRegion = "KE"
    private val phoneUtil = PhoneNumberUtil.getInstance()
    private val _userDetails = MutableStateFlow(UserDetails())
    val userUiDetails: StateFlow<UserDetails> = _userDetails.asStateFlow()

    var accUiState: AccountUiState by mutableStateOf(AccountUiState.Auth())
        private set
    var createPaymentUiState: ICreatePayment by mutableStateOf(ICreatePayment.Success())
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
            phone.countryCode = countryPhoneCode[defaultRegion]?.toInt() ?: 0
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

    fun createPayment(phone: String, reason: String) {
        createPaymentUiState = ICreatePayment.Loading
        viewModelScope.launch {
            createPaymentUiState = try {
                val response = client.mutation(
                    CreatePaymentMutation(
                        phone = phone,
                        description = reason,
                        amount = landlordSubscriptionFee,
                    )
                ).execute()
                if (response.hasErrors()) {
                    ICreatePayment.ApolloError(response.errors!!)
                } else {
                    val res = response.data?.createPayment
                    ICreatePayment.Success(success = res?.success)
                }
            } catch (e: ApolloException) {
                ICreatePayment.ApplicationError(e)
            }
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