package com.example.nyatta.compose.navigation

interface Navigation {
    // Path for a composable
    val route: String
    // Top app bar title
    val title: String?
}