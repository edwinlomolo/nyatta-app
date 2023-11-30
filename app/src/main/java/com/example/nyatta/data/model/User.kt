package com.example.nyatta.data.model

import com.example.nyatta.GetUserQuery

data class User(
    val id: Any = "",
    val avatar: String = "https://nyatta-staging-media.s3.amazonaws.com/user.png",
    val phone: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val properties: List<GetUserQuery.Property> = listOf(),
    val units: List<GetUserQuery.Unit> = listOf()
)
