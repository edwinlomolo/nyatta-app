package com.example.nyatta.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.nyatta.network.NyattaGqlApiRepository

class ListingViewModel(
    private val nyattaGqlApiRepository: NyattaGqlApiRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    suspend fun getUnit(id: String) = nyattaGqlApiRepository.getUnit(id)
}