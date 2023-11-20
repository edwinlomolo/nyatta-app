package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CircularProgressLoader(
    modifier: Modifier = Modifier
) {
    CircularProgressIndicator(
        modifier = modifier.size(18.dp),
        color = MaterialTheme.colorScheme.primary
    )
}