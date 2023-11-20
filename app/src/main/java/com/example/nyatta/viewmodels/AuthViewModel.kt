package com.example.nyatta.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyatta.GetUserQuery
import com.example.nyatta.data.auth.OfflineAuthRepository
import com.example.nyatta.data.model.Token
import com.example.nyatta.data.model.User
import com.example.nyatta.data.rest.RestApiRepository
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.Phonenumber
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authRepository: OfflineAuthRepository,
    private val restApiRepository: RestApiRepository,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): ViewModel() {
    val landlordSubscriptionFee = "1500"
    val countryPhoneCode = mapOf("KE" to "+254")
    val countryCurrencyCode = mapOf("KE" to "KES")
    val defaultRegion = "KE"

    private val phoneUtil = PhoneNumberUtil.getInstance()

    var createPaymentUiState: ICreatePayment by mutableStateOf(ICreatePayment.Success())
        private set

    var signInUiState: SignInUiState by mutableStateOf(SignInUiState.Success)
        private set

    val authUiState: StateFlow<Auth> = authRepository
        .getAuthToken()
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

    var firstName by mutableStateOf("")
        private set
    var lastName by mutableStateOf("")
        private set
    var avatar by mutableStateOf("")
        private set

    private val _userDetails = MutableStateFlow(UserDetails())
    val userUiDetails: StateFlow<UserDetails> = _userDetails.asStateFlow()

    fun updateFirstname(name: String) { firstName = name }
    fun updateLastname(name: String) { lastName = name }
    fun updateAvatar(upload: String) { avatar = upload }

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

    fun refreshUser() {
        viewModelScope.launch {
            try {
                authRepository.recycleUser(
                    isLandlord = authUiState.value.token.isLandlord,
                    gps = LatLng(authUiState.value.token.lat, authUiState.value.token.lng)
                )
            } catch(e: Throwable) {
                e.localizedMessage?.let { Log.e("RefreshUserOperationError", it) }
            }
        }
    }

    fun updateDeviceLocation(gps: LatLng) {
        if(gps.latitude != 0.0 && gps.longitude != 0.0) {
            viewModelScope.launch {
                try {
                    authRepository.storeUserLocation(
                        token = authUiState.value.token,
                        gps = gps
                    )
                } catch(e: Throwable) {
                    e.localizedMessage?.let { Log.e("UpdateDeviceLocationOperationError", it) }
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

    fun resetAuthState() {
        _userDetails.value = UserDetails()
    }

    suspend fun getUser() =  nyattaGqlApiRepository.getUser().dataOrThrow()

    init {
        resetAuthState()
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class Auth(val token: Token = Token())

data class UserDetails(
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val avatar: String = "",
    val validDetails: DataValidity = DataValidity(),
)

data class DataValidity(
    val phone: Boolean = false,
    val firstName: Boolean = false,
    val lastName: Boolean = false
)

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