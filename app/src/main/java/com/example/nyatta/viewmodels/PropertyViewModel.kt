package com.example.nyatta.viewmodels

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.google.i18n.phonenumbers.Phonenumber

class PropertyViewModel: ViewModel() {
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

    fun setCaretakerImage(image: String) {
        _uiState.update {
            val valid = it.validToProceed.copy(caretakerImage = image.isNotEmpty())
            val caretaker = it.caretaker.copy(image = image)
            it.copy(
                caretaker = caretaker,
                validToProceed = valid
            )
        }
    }

    fun setPropertyThumbnail(thumbnail: Uri?) {
        _uiState.update {
            val valid = it.validToProceed.copy(thumbnail = thumbnail != null)
            it.copy(
                thumbnail = thumbnail,
                validToProceed = valid
            )
        }
    }
}

data class CaretakerData(
    val image: String = "",
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
    val thumbnail: Uri? = null
)
