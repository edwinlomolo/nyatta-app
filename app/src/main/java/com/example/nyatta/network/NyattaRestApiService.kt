package com.example.nyatta.network

import com.example.nyatta.data.model.Upload
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface NyattaRestApiService {
    @Multipart
    @POST("upload")
    suspend fun uploadImage(@Part body: MultipartBody.Part): Upload
}
