package com.example.nyatta

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.nyatta.compose.navigation.NyattaNavHost
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun NyattaApplication() {
    NyattaNavHost(navController = rememberNavController())
}

@Preview(showBackground = true)
@Composable
fun NyattaApplicationPreview() {
    NyattaTheme {
        NyattaApplication()
    }
}