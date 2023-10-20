package com.example.nyatta.ui.screens.onboarding.property.description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.screens.onboarding.baths.pad
import com.example.nyatta.ui.theme.NyattaTheme


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Description(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var description by remember {
        mutableStateOf("")
    }
    Onboarding(
        modifier = modifier,
        onActionButtonClick = {},
        actionButtonText = stringResource(R.string.save_description)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Title(stringResource(R.string.property_name))
            Description(stringResource(R.string.property_name_description))
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .then(pad),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                supportingText = {
                    Text(
                        text = stringResource(R.string.description_supporting_text),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        Description()
    }
}