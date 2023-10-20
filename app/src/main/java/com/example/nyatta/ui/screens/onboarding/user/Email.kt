package com.example.nyatta.ui.screens.onboarding.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.ui.components.onboarding.ActionButton
import com.example.nyatta.ui.components.onboarding.TextInput
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Email(
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
            onValueChange = { /*TODO*/ },
            placeholder = {
                Text("Email address")
            },
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
            text = "Verify email",
            onClick = {}
        ) {
            Icon(
                Icons.Outlined.ArrowForward,
                contentDescription = null
            )
        }
    }
}

@Preview
@Composable
fun EmailPreview() {
    NyattaTheme {
        Email()
    }
}