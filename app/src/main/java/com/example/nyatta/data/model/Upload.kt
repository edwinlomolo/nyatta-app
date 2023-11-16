package com.example.nyatta.data.model

import com.squareup.moshi.Json

data class Upload(
    @Json(name = "image_uri") val imageUri: String
)
