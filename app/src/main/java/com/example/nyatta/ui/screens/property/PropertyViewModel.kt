package com.example.nyatta.ui.screens.property

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class PropertyData(
    val description: String = "",
    val isCaretaker: Boolean = false,
    val caretaker: CaretakerData = CaretakerData(),
)

data class CaretakerData(
    val image: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phone: String = ""
)

class PropertyViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(PropertyData())
    val uiState: StateFlow<PropertyData> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update {
            it.copy(description = name)
        }
    }

    fun setIsCaretaker(isCaretaker: Boolean) {
        _uiState.update {
            it.copy(isCaretaker = isCaretaker)
        }
    }

    fun setCaretakerFirstname(name: String) {
        _uiState.update {
            val caretaker = it.caretaker.copy(firstName = name)
            it.copy(caretaker = caretaker)
        }
    }

    fun setCaretakerLastname(name: String) {
        _uiState.update {
            val caretaker = it.caretaker.copy(lastName = name)
            it.copy(caretaker = caretaker)
        }
    }

    fun setCaretakerPhone(phone: String) {
        _uiState.update {
            val caretaker = it.caretaker.copy(phone = phone)
            it.copy(caretaker = caretaker)
        }
    }

    fun setCaretakerImage(image: String) {
        _uiState.update {
            val caretaker = it.caretaker.copy(image = image)
            it.copy(caretaker = caretaker)
        }
    }
}