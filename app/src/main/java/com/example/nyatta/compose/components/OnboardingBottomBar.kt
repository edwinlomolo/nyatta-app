package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingBottomBar(
    modifier: Modifier = Modifier,
    showNextIcon: Boolean = true,
    navigateBack: () -> Unit = {},
    actionButtonText: @Composable() (RowScope.() -> Unit),
    onActionButtonClick: () -> Unit = {},
    validToProceed: Boolean = false
) {
    BottomAppBar {
        Row(
            modifier = modifier.padding(8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                modifier = Modifier.fillMaxWidth().height(48.dp),
                onClick = { if (validToProceed) onActionButtonClick() },
                shape = MaterialTheme.shapes.small
            ) {
                actionButtonText()
                if (showNextIcon) {
                    Icon(
                        Icons.TwoTone.ArrowForward,
                        contentDescription = "Proceed"
                    )
                }
            }
        }
    }
}