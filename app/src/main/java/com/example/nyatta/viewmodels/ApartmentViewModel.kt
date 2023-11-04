package com.example.nyatta.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.nyatta.compose.apartment.Bedroom
import com.example.nyatta.data.Amenity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.nyatta.data.amenities
import java.text.NumberFormat

class ApartmentViewModel: ViewModel() {
    val selectProperties = listOf(
        SelectPropertyData("87928yoihf", "Beach House Properties"),
        SelectPropertyData("209jfuf", "Mwea House Properties")
    )
    val defaultAmenities = amenities
    val unitTypeOptions = listOf("Single room", "Studio", "1", "2", "3", "4")
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
        val hasBedCount: Int = if (unitType != "Single room" && unitType != "Studio") unitType.toInt() else 0
        _uiState.update {
            val emptyBedrooms = Array(hasBedCount) { index -> Bedroom(number = index+1)}.toList()
            it.copy(
                unitType = unitType,
                bedrooms = emptyBedrooms
            )
        }
    }

    fun addUnitAmenity(amenity: Amenity) {
        _uiState.update {
            it.copy(
                selectedAmenities = it.addAmenity(amenity),
            )
        }
    }

    fun updateBedroomMaster(bedNumber: Int, master: Boolean) {
        _uiState.update {
            it.copy(bedrooms = it.updateBedroomMaster(bedNumber, master))
        }
    }

    fun updateBedroomEnSuite(bedroomNumber: Int, enSuite: Boolean) {
        _uiState.update {
            it.copy(bedrooms = it.updateBedroomEnSuite(bedroomNumber, enSuite = enSuite))
        }
    }

    fun setBathrooms(count: String) {
        _uiState.update {
            val valid = it.dataValidity.copy(bathrooms = count.isNotEmpty())
            it.copy(
                bathrooms = count,
                dataValidity = valid
            )
        }
    }

    fun setUnitPrice(price: String) {
        _uiState.update {
            val valid = it.dataValidity.copy(price = price.isNotEmpty())
            it.copy(
                price = price,
                dataValidity = valid
            )
        }
    }

    fun setUnitState(state: State) {
        _uiState.update {
            it.copy(state = state)
        }
    }

    private fun resetApartmentData() {
        _uiState.value = ApartmentData(
            associatedToProperty = selectProperties[0],
            unitType = unitTypeOptions[0],
        )
    }

    init {
        resetApartmentData()
    }
}

data class ApartmentData(
    val description: String = "",
    val associatedToProperty: SelectPropertyData = SelectPropertyData(),
    val dataValidity: ApartmentDataValidity = ApartmentDataValidity(),
    val unitType: String = "",
    val selectedAmenities: List<Amenity> = listOf(),
    val bedrooms: List<Bedroom> = listOf(),
    val bathrooms: String = "",
    val state: State = State.Vacant,
    val price: String = ""
)
fun ApartmentData.addAmenity(e: Amenity): List<Amenity> {
    val foundAmenityIndex = selectedAmenities.indexOfFirst { it.id == e.id }
    val mutableAmenities = selectedAmenities.toMutableList()
    if (mutableAmenities.size == 0) {
        mutableAmenities.add(e)
    } else if (foundAmenityIndex > 0) {
        mutableAmenities.removeAt(foundAmenityIndex)
    } else if (foundAmenityIndex < 0) {
        mutableAmenities.add(e)
    } else {
        mutableAmenities.removeAt(0)
    }
    return mutableAmenities.toList()
}
fun ApartmentData.updateBedroomMaster(bedroomNumber: Int, master: Boolean): List<Bedroom> {
    val mutableBedrooms = bedrooms.toMutableList()
    val bedroom = mutableBedrooms[bedroomNumber]
    mutableBedrooms[bedroomNumber] = Bedroom(number = bedroom.number, master = master, enSuite = bedroom.enSuite)
    return mutableBedrooms.toList()
}
fun ApartmentData.updateBedroomEnSuite(bedroomNumber: Int, enSuite: Boolean): List<Bedroom> {
    val mutableBedrooms = bedrooms.toMutableList()
    val bedroom = mutableBedrooms[bedroomNumber]
    mutableBedrooms[bedroomNumber] = Bedroom(number = bedroom.number, master = bedroom.master, enSuite = enSuite)
    return mutableBedrooms.toList()
}

data class SelectPropertyData(
    val id: String = "",
    val title: String = ""
)

data class ApartmentDataValidity(
    val description: Boolean = false,
    val bathrooms: Boolean = false,
    val price: Boolean = false
)

data class Bedroom(
    val number: Int = 0,
    val master: Boolean = false,
    val enSuite: Boolean = false
)
enum class State { Vacant, Occupied }