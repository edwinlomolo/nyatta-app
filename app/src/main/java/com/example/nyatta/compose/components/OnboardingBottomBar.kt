package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nyatta.R

@Composable
fun OnboardingBottomBar(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    actionButtonText: @Composable() (RowScope.() -> Unit),
    onActionButtonClick: () -> Unit = {},
    validToProceed: Boolean = true
) {
    BottomAppBar {
        Row(
            modifier = modifier.padding(8.dp)
        ) {
            Button(
                onClick = navigateBack,
                shape = MaterialTheme.shapes.small,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )
            ) {
                Text(
                    text = stringResource(R.string.back),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (validToProceed) {
                Button(
                    onClick = onActionButtonClick,
                    shape = MaterialTheme.shapes.small
                ) {
                    actionButtonText()
                }
            }
        }
    }
}