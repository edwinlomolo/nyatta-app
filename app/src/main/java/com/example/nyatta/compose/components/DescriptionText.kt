package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
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
        modifier = modifier.padding(4.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}
