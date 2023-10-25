package com.example.nyatta.ui.screens.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    actionButtonText: String = "",
    onActionButtonClick: () -> Unit = {},
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    alignBottomCenter: Boolean = true,
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
        // TODO fix this hack for button hidden in keyboard view
        if (alignBottomCenter) Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            verticalAlignment = Alignment.Bottom
        ) {
            Button(
                onClick = { onActionButtonClick() },
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxWidth(),
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    modifier = Modifier.padding(8.dp),
                    text = actionButtonText,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}