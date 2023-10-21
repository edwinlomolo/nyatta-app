package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.TextInput
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Bath(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Onboarding(
        modifier = modifier,
        actionButtonText = "Save",
        onActionButtonClick = {}
    ) {
        Title(stringResource(R.string.bath_label_text))
        Description(stringResource(R.string.tell_baths))
        TextInput(
            value = "",
            onValueChange = {},
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    keyboardController?.hide()
                }
            ),
            modifier = Modifier
                .padding(8.dp)
        )
    }
}

@Preview
@Composable
fun BathPreview() {
    NyattaTheme {
        Bath()
    }
}