package com.example.nyatta.ui.components.onboarding

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.ui.theme.MabryFont

@Composable
fun Description(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        modifier = modifier.padding(8.dp),
        style = TextStyle(
            fontFamily = MabryFont,
            fontSize = 18.sp,
            lineHeight = 24.sp,
            fontWeight = FontWeight.ExtraLight
        )
    )
}
