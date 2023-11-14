package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.data.model.User

@Composable
fun AlertDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    icon: ImageVector,
    user: User = User(),
    confirmationText: String? = null,
) {
    val isLandlord = user.isLandlord

    AlertDialog(
        icon = {
            if (isLandlord) {
                Icon(
                    icon,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.done_icon)
                )
            } else {
                Icon(
                    icon,
                    modifier = Modifier.size(40.dp),
                    tint = MaterialTheme.colorScheme.primary,
                    contentDescription = stringResource(R.string.info)
                )
            }
        },
        title = {
            Text(
                text = dialogTitle,
                style = MaterialTheme.typography.titleMedium
            )
        },
        text = {
            Text(
                text = dialogText,
                style = MaterialTheme.typography.bodyLarge
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(
                    text = confirmationText ?: stringResource(R.string.done),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        },
    )
}