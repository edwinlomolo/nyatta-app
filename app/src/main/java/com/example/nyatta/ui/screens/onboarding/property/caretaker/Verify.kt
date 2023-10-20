package com.example.nyatta.ui.screens.onboarding.property.caretaker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Verify(modifier: Modifier = Modifier) {
    Onboarding(
        modifier = modifier
    ) {}
}

@Preview(showBackground = true)
@Composable
fun VerifyPreview() {
    NyattaTheme {
        Verify()
    }
}