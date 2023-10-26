package com.example.nyatta.model

data class AuthRequest(
    val firstname: String,
    val lastname: String,
    val phone: String,
    val email: String
)