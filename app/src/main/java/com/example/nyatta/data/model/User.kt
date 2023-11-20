package com.example.nyatta.data.model

data class User(
    val avatar: String = "https://nyatta-staging-media.s3.amazonaws.com/user.png",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
)
