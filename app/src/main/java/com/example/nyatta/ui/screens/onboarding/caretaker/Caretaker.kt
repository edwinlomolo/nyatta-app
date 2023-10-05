package com.example.nyatta.ui.screens.onboarding.caretaker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun Caretaker(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    val countryCode by remember {
        mutableStateOf("+254")
    }
    Onboarding(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // TODO Image upload
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.caretaker_label),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 16.dp).align(Alignment.CenterHorizontally)
            )
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .size(100.dp)
            ) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Box(
            modifier = Modifier.padding(16.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                value = firstName,
                onValueChange = { firstName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    Text(
                        text = stringResource(R.string.caretaker_supporting_firstname),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                label = { Text(text = stringResource(R.string.caretaker_firstname)) }
            )
        }
        Box {
            OutlinedTextField(
                modifier = Modifier.padding(16.dp),
                value = lastName,
                onValueChange = { lastName = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                ),
                supportingText = {
                    Text(
                        text = stringResource(R.string.caretaker_supporting_lastname),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                label = { Text(text = stringResource(R.string.caretaker_lastname)) }
            )
        }
        Box {
            OutlinedTextField(
                leadingIcon = {
                    Text(text = countryCode)
                },
                modifier = Modifier.padding(16.dp),
                value = phone,
                onValueChange = { phone = it },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                supportingText = {
                    Text(
                        text = stringResource(R.string.caretaker_supporting_phone),
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                label = { Text(text = stringResource(R.string.caretaker_phone)) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaretakerPreview() {
    NyattaTheme {
        Caretaker()
    }
}