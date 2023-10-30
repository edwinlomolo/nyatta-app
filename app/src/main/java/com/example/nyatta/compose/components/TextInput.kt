package com.example.nyatta.compose.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: (@Composable () -> Unit)? = null,
    onValueChange: (String) -> Unit,
    prefix: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    readOnly: Boolean = false,
    isError: Boolean = false,
    enabled: Boolean = true
) {
    TextField(
        modifier = modifier
            .fillMaxWidth(),
        prefix = prefix,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.background,
            unfocusedContainerColor = if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.background,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
            errorTextColor = MaterialTheme.colorScheme.error
        ),
        isError = isError,
        supportingText = {
            if (isError) Text(text = "Invalid", color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.labelSmall)
        },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        value = value,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = placeholder,
        enabled = enabled,
        readOnly = readOnly,
        onValueChange = { onValueChange(it) }
    )
}