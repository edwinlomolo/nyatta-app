package com.example.nyatta.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nyatta.ui.navigation.NyattaNavHost
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun NyattaApp() {
    NyattaNavHost(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun NyattaAppPreview() {
    NyattaTheme {
        NyattaApp()
    }
}