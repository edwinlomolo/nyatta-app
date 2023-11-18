package com.example.nyatta.viewmodels

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nyatta.data.rest.RestApiRepository
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.google.i18n.phonenumbers.Phonenumber
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okio.IOException
import java.io.InputStream

class PropertyViewModel(
    private val restApiRepository: RestApiRepository
): ViewModel() {
    private val defaultRegion = "KE"
    val countryCode = "+254"
    val phoneUtil: PhoneNumberUtil = PhoneNumberUtil.getInstance()
    private val _uiState = MutableStateFlow(PropertyData())
    val uiState: StateFlow<PropertyData> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(description = name.isNotEmpty())
            it.copy(
                description = name,
                validToProceed = valid
            )
        }
    }

    fun setIsCaretaker(isCaretaker: Boolean) {
        _uiState.update {
            it.copy(isCaretaker = isCaretaker)
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

    fun setCaretakerFirstname(name: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(caretakerFirstname = name.isNotEmpty())
            val caretaker = it.caretaker.copy(firstName = name)
            it.copy(
                caretaker = caretaker,
                validToProceed = valid
            )
        }
    }

    fun setCaretakerLastname(name: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(caretakerLastname = name.isNotEmpty())
            val caretaker = it.caretaker.copy(lastName = name)
            it.copy(
                caretaker = caretaker,
                validToProceed = valid
            )
        }
    }

    fun setCaretakerPhone(phone: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(caretakerPhone = if (phone.isNotEmpty()) validatePhone(phone) else true)
            val caretaker = it.caretaker.copy(phone = phone)
            it.copy(
                caretaker = caretaker,
                validToProceed = valid
            )
        }
    }

    fun setCaretakerImage(stream: InputStream) {
        val request = stream.readBytes().toRequestBody()
        val filePart = MultipartBody.Part.createFormData(
            "file",
            "photo_${System.currentTimeMillis()}.jpg",
            request
        )
        _uiState.update {
            val caretaker = it.caretaker.copy(image = ImageState.Loading)
            it.copy(caretaker = caretaker)
        }
        viewModelScope.launch {
            try {
                val response = restApiRepository.uploadImage(filePart)
                _uiState.update {
                    val caretaker = it.caretaker.copy(image = ImageState.Success(response.imageUri))
                    it.copy(caretaker = caretaker)
                }
            } catch(e: IOException) {
                _uiState.update {
                    val caretaker = it.caretaker.copy(image = ImageState.UploadError(e.localizedMessage))
                    it.copy(caretaker = caretaker)
                }
                e.localizedMessage?.let { Log.e("UploadCaretakerImageOperationError", it) }
            }
        }
    }

    fun setPropertyThumbnail(stream: InputStream) {
        val request = stream.readBytes().toRequestBody()
        val filePart = MultipartBody.Part.createFormData(
            "file",
            "photo_${System.currentTimeMillis()}.jpg",
            request
        )
        _uiState.update { it.copy(thumbnail = ImageState.Loading) }
        viewModelScope.launch {
            try {
                val response = restApiRepository.uploadImage(filePart)
                _uiState.update { it.copy(thumbnail = ImageState.Success(response.imageUri)) }
            } catch(e: IOException) {
                _uiState.update { it.copy(thumbnail = ImageState.UploadError(e.localizedMessage)) }
                e.localizedMessage?.let { Log.e("UploadPropertyThumbnailOperationError", it) }
            }
        }
    }

    fun createProperty() {
        try {
            setSubmitted(true)
        } catch(e: Throwable) {
            e.localizedMessage?.let {
                Log.e("CreatingPropertyOperationError", it)
            }
        }
    }

    fun setSubmitted(submitted: Boolean) {
        _uiState.update {
            it.copy(
                submitted = submitted
            )
        }
    }

    fun resetPropertyData() {
        _uiState.value = PropertyData()
    }

    init {
        resetPropertyData()
    }
}

data class CaretakerData(
    val image: ImageState = ImageState.Success(),
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = ""
)
data class PropertyDataValidity(
    val description: Boolean = true,
    val caretakerFirstname: Boolean = true,
    val caretakerLastname: Boolean = true,
    val caretakerPhone: Boolean = true,
    val caretakerImage: Boolean = true,
    val thumbnail: Boolean = false
)
data class PropertyData(
    val description: String = "",
    val isCaretaker: Boolean = false,
    val caretaker: CaretakerData = CaretakerData(),
    val validToProceed: PropertyDataValidity = PropertyDataValidity(),
    val thumbnail: ImageState = ImageState.Success(),
    val submitted: Boolean = false
)
interface ImageState {
    data class Success(val imageUri: String? = null): ImageState
    object Loading: ImageState
    data class UploadError(val message: String? = null): ImageState
}
