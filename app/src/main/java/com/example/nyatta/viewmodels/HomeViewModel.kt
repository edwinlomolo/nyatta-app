package com.example.nyatta.viewmodels

import androidx.lifecycle.ViewModel
import com.example.nyatta.network.NyattaGqlApiRepository
import com.google.android.gms.maps.model.LatLng

sealed interface HomeUiState {
    data class Success(val hello: String?): HomeUiState
    data object Loading: HomeUiState
    data class ApolloError(val message: String?): HomeUiState
}

class HomeViewModel(
    private val nyattaGqlApiRepository: NyattaGqlApiRepository
): ViewModel() {
    suspend fun getNearByListings(deviceLocation: LatLng) = nyattaGqlApiRepository.getNearByListings(deviceLocation).dataOrThrow()
}
