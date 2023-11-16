package com.example.nyatta.data.rest

import com.example.nyatta.data.model.Upload
import com.example.nyatta.network.NyattaRestApiService
import okhttp3.MultipartBody

class RestApiRepository(
    private val nyattaRestApiService: NyattaRestApiService
): NyattaRestApiService {
    override suspend fun uploadImage(body: MultipartBody.Part): Upload = nyattaRestApiService.uploadImage(body)
}