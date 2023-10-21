package com.example.nyatta.ui.components.onboarding

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PropertyDescription(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onValueChange: () -> Unit = {},
    onActionButtonClick: () -> Unit = {},
    actionButtonText: String = stringResource(R.string.save_description),
    placeholder: @Composable (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Onboarding(
        modifier = modifier,
        onActionButtonClick = { onActionButtonClick() },
        actionButtonText = actionButtonText
    ) {
        Column {
            Title(title)
            Description(description)
            TextInput(
                value = "",
                onValueChange = { onValueChange() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                placeholder = placeholder
            )
        }
    }
}