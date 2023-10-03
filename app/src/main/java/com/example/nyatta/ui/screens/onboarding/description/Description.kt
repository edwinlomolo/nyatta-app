package com.example.nyatta.ui.screens.onboarding.description

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.screens.onboarding.baths.pad
import com.example.nyatta.ui.theme.NyattaTheme


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Description(modifier: Modifier = Modifier) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var description by remember {
        mutableStateOf("")
    }
    Onboarding(
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
                Text(
                    text = stringResource(R.string.description_label_text),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.then(pad)
                )
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
                            style = MaterialTheme.typography.titleSmall
                        )
                    }
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        Description()
    }
}