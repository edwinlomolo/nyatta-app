package com.example.nyatta.ui.screens.onboarding.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Names(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .padding(18.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            value = "",
            onValueChange = {},
            placeholder = {
                Text("First name")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            )
        )
        TextInput(
            value = "",
            placeholder = {
                Text("Last name")
            },
            onValueChange = {},
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            )
        )
        ActionButton(
            text = "Save",
            onClick = {}
        )
    }
}

@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
    content: (@Composable () -> Unit)? = null,
) {
    Button(
        modifier = modifier
            .padding(top = 12.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = { onClick() }
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(8.dp)
        )
        if (content != null) {
            content()
        }
    }
}

@Composable
fun TextInput(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: (@Composable () -> Unit)? = null,
    onValueChange: () -> Unit,
    prefix: (@Composable () -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    TextField(
        modifier = modifier
            .padding(top = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        prefix = prefix,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background
        ),
        value = value,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        placeholder = placeholder,
        onValueChange = { onValueChange() }
    )
}

@Preview(showBackground = true)
@Composable
fun NamesPreview() {
    NyattaTheme {
        Names()
    }
}