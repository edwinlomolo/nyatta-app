package com.example.nyatta.ui.screens.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.ActionButton
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.TextInput
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

val paymentOptions = listOf("Mpesa")
val optionsIcon = listOf(R.drawable.mpesa)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Mpesa(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(paymentOptions[0]) }

    Onboarding(
        modifier = modifier
            .padding(18.dp),
        actionButtonText = stringResource(R.string.pay_monthly),
        onActionButtonClick = {}
    ) {
        Title(stringResource(R.string.pay_monthly))
        Description(stringResource(R.string.pay_monthly_description))
        Row(Modifier.selectableGroup()) {
            paymentOptions.forEachIndexed { index, option ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .selectable(
                            selected = (option == selectedOption),
                            onClick = { onOptionSelected(option) },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = null
                    )
                    Image(
                        painterResource(optionsIcon[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier
                            .size(100.dp)
                    )
                }
            }
        }
        Column {
            TextInput(
                onValueChange = {},
                prefix = {
                    Text(
                        text = stringResource(R.string._254),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                )
            )
            TextInput(
                onValueChange = {},
                value = stringResource(R.string.monthly_fee),
                prefix = {
                    Text(
                        text = stringResource(R.string.kes),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                enabled = false
            )
        }
    }
}

@Preview
@Composable
fun MpesaPreview() {
    NyattaTheme {
        Mpesa()
    }
}