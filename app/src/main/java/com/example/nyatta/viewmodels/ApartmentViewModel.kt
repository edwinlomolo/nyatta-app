package com.example.nyatta.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nyatta.data.Amenity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.nyatta.data.amenities

class ApartmentViewModel: ViewModel() {
    val selectProperties = listOf(
        SelectPropertyData("87928yoihf", "Beach House Properties"),
        SelectPropertyData("209jfuf", "Mwea House Properties")
    )
    val defaultAmenities = amenities
    val unitTypeOptions = listOf("Single Room", "Studio", "1 bedroom", "2 bedroom", "3 bedroom")
    private val _uiState = MutableStateFlow(ApartmentData())
    val uiState: StateFlow<ApartmentData> = _uiState.asStateFlow()

    fun setName(name: String) {
        _uiState.update {
            val valid = it.dataValidity.copy(description = name.isNotEmpty())
            it.copy(
                description = name,
                dataValidity = valid
            )
        }
    }

    fun setAssociatedTo(associate: SelectPropertyData) {
        _uiState.update {
            it.copy(associatedToProperty = associate)
        }
    }

    fun setUnitType(unitType: String) {
        _uiState.update {
            it.copy(unitType = unitType)
        }
    }

    fun addUnitAmenity(amenity: Amenity) {
        _uiState.update {
            it.copy(
                selectedAmenities = it.addAmenity(amenity),
            )
        }
    }

    init {
        _uiState.value = ApartmentData(
            associatedToProperty = selectProperties[0],
            unitType = unitTypeOptions[0],
            selectedAmenities = mutableListOf()
        )
    }
}

data class ApartmentData(
    val description: String = "",
    val associatedToProperty: SelectPropertyData = SelectPropertyData(),
    val dataValidity: ApartmentDataValidity = ApartmentDataValidity(),
    val unitType: String = "",
    val selectedAmenities: List<Amenity> = listOf()
)
fun ApartmentData.addAmenity(e: Amenity): List<Amenity> {
    val foundAmenityIndex = selectedAmenities.indexOfFirst { it.id == e.id }
    val mutableList = selectedAmenities.toMutableList()
    if (mutableList.size == 0) {
        mutableList.add(e)
    } else if (foundAmenityIndex > 0) {
        mutableList.removeAt(foundAmenityIndex)
    } else if (foundAmenityIndex < 0) {
        mutableList.add(e)
    } else {
        mutableList.removeAt(0)
    }
    return mutableList.toList()
}
data class SelectPropertyData(
    val id: String = "",
    val title: String = ""
)
data class ApartmentDataValidity(
    val description: Boolean = false,
)