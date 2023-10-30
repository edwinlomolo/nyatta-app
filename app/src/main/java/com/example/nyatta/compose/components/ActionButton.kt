package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    content: (@Composable () -> Unit)? = null
) {
    Button(
        modifier = modifier
            .padding(top = 4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier
                .padding(4.dp)
        )
        if (content != null) {
            content()
        }
    }
}