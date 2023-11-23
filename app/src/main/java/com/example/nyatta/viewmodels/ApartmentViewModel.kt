package com.example.nyatta.viewmodels

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetUserPropertiesQuery
import com.example.nyatta.data.Amenity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.nyatta.data.amenities
import com.example.nyatta.data.rest.RestApiRepository
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.internal.toImmutableList
import okhttp3.internal.toImmutableMap
import okio.IOException
import java.io.InputStream

class ApartmentViewModel(
    private val restApiRepository: RestApiRepository,
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): ViewModel() {
    val defaultAmenities = amenities
    val unitTypeOptions = listOf("Single room", "Studio", "1", "2", "3", "4")

    private val _uiState = MutableStateFlow(ApartmentData())
    val uiState: StateFlow<ApartmentData> = _uiState.asStateFlow()

    var createUnitState: ICreateUnit by mutableStateOf(ICreateUnit.Success())
        private set

    fun setName(name: String) {
        _uiState.update {
            val valid = it.dataValidity.copy(description = name.isNotEmpty())
            it.copy(
                description = name,
                dataValidity = valid
            )
        }
    }

    fun setAssociatedTo(associate: GetUserPropertiesQuery.GetUserProperty) {
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

    fun setUnitImages(category: String, images: List<InputStream>) {
        images.forEach { stream ->
            viewModelScope.launch {
                val request = stream.readBytes().toRequestBody()
                val filePart = MultipartBody.Part.createFormData(
                    "file",
                    "photo_${System.currentTimeMillis()}.jpg",
                    request
                )
                _uiState.update {
                    val imageState = ImageState.Loading
                    it.copy(images = it.addImage(category, imageState))
                }
                val addedItemIndex = _uiState.value.images[category]?.size?.minus(1)
                viewModelScope.launch(Dispatchers.IO) {
                    try {
                        val response = restApiRepository.uploadImage(filePart)
                        _uiState.update {
                            val imageState = ImageState.Success(response.imageUri)
                            it.copy(
                                images = it.updateUnitImage(
                                    category,
                                    imageState,
                                    addedItemIndex!!
                                )
                            )
                        }
                    } catch (e: IOException) {
                        _uiState.update {
                            val imageState = ImageState.UploadError(e.localizedMessage)
                            it.copy(
                                images = it.updateUnitImage(
                                    category,
                                    imageState,
                                    addedItemIndex!!
                                )
                            )
                        }
                        e.localizedMessage?.let { Log.e("UploadUnitImageOperationError", it) }
                    }
                }
            }
        }
    }

    fun updateUnitImage(category: String, image: InputStream, index: Int) {
        val request = image.readBytes().toRequestBody()
        val filePart = MultipartBody.Part.createFormData(
            "file",
            "photo_${System.currentTimeMillis()}.jpg",
            request
        )
        _uiState.update {
            val imageState = ImageState.Loading
            it.copy(images = it.updateUnitImage(category, imageState, index))
        }
        viewModelScope.launch {
            try {
                val response = restApiRepository.uploadImage(filePart)
                _uiState.update {
                    val imageState = ImageState.Success(response.imageUri)
                    it.copy(images = it.updateUnitImage(category, imageState, index))
                }
            } catch(e: IOException) {
                _uiState.update {
                    val imageState = ImageState.UploadError(e.localizedMessage)
                    it.copy(images = it.updateUnitImage(category, imageState, index))
                }
            }
        }
    }

    private fun unitSubmitted(submitted: Boolean) {
        _uiState.update {
            it.copy(submitted = submitted)
        }
    }

    fun createUnit() {
        createUnitState = ICreateUnit.Loading
        viewModelScope.launch {
            createUnitState = try {
                resetApartmentData()
                ICreateUnit.Success(true)
            } catch(e: Throwable) {
                unitSubmitted(false)
                e.localizedMessage?.let { Log.e("CreateUnitOperationError", it) }
                ICreateUnit.CreateUnitError(e.localizedMessage)
            }
        }
    }

    private fun resetApartmentData() {
        _uiState.value = ApartmentData(
            unitType = unitTypeOptions[0]
        )
    }

    suspend fun getUserProperties() = nyattaGqlApiRepository.getUserProperties()

    fun createUnit(type: String, deviceLocation: LatLng, propertyData: PropertyData, cb: () -> Unit) {
        createUnitState = ICreateUnit.Loading
        viewModelScope.launch {
            createUnitState = try {
                nyattaGqlApiRepository.addUnit(type, deviceLocation, propertyData, _uiState.value)
                ICreateUnit.Success().also { cb() }
            } catch(e: ApolloException) {
                ICreateUnit.CreateUnitError(e.localizedMessage)
            }
        }
    }

    init {
        resetApartmentData()
    }
}

data class ApartmentData(
    val description: String = "",
    val associatedToProperty: GetUserPropertiesQuery.GetUserProperty? = null,
    val dataValidity: ApartmentDataValidity = ApartmentDataValidity(),
    val unitType: String = "",
    val selectedAmenities: List<Amenity> = listOf(),
    val bedrooms: List<Bedroom> = listOf(),
    val bathrooms: String = "",
    val state: State = State.VACANT,
    val price: String = "",
    val images: Map<String, List<ImageState>> = mapOf(),
    val submitted: Boolean = false
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
fun ApartmentData.addImage(category: String, image: ImageState): Map<String, List<ImageState>> {
    val imageEntry = images.toMutableMap()
    if (imageEntry[category] == null) imageEntry[category] = listOf()
    val keyValues = imageEntry[category]?.toMutableList()
    keyValues?.add(image)
    imageEntry[category] = keyValues!!.toImmutableList()
    return imageEntry.toImmutableMap()
}
fun ApartmentData.updateUnitImage(category: String, image: ImageState, index: Int): Map<String, List<ImageState>> {
    val imageEntry = images.toMutableMap()
    if (imageEntry[category] == null) imageEntry[category] = listOf()
    val keyValues = imageEntry[category]?.toMutableList()
    keyValues?.set(index, image)
    imageEntry[category] = keyValues!!.toImmutableList()
    return imageEntry.toImmutableMap()
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
enum class State { VACANT, OCCUPIED }

interface ICreateUnit {
    data class Success(val success: Boolean = false): ICreateUnit
    object Loading: ICreateUnit
    data class CreateUnitError(val message: String? = null): ICreateUnit
}