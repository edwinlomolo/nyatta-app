package com.example.nyatta.ui.screens.onboarding.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
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
fun VerifyEmail(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Column(
        modifier = modifier
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextInput(
            onValueChange = { /*TODO*/ },
            placeholder = {
                Text("Verification code")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            )
        )
        ActionButton(text = "Verify")
    }
}

@Preview(showBackground = true)
@Composable
fun VerifyEmailPreview() {
    NyattaTheme {
        VerifyEmail()
    }
}