package com.example.nyatta.compose.payment

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.ActionButton
import com.example.nyatta.compose.components.AlertDialog
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AuthViewModel
import com.example.nyatta.viewmodels.ICreatePayment

object PayDestination: Navigation {
    override val route = "property/payment"
    override val title = "Pay"
}

val paymentOptions = listOf("Mpesa")
val optionsIcon = listOf(R.drawable.mpesa)

@Composable
fun Mpesa(
    modifier: Modifier = Modifier,
    navigateNext: (String) -> Unit = {},
    navigateUp: () -> Unit = {},
    authViewModel: AuthViewModel = viewModel()
) {
    val accUiState by authViewModel.userUiDetails.collectAsState()
    val createPaymentState = authViewModel.createPaymentUiState
    val phone = accUiState.phone
    val keyboardController = LocalSoftwareKeyboardController.current
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(paymentOptions[0]) }
    val (showDialog, onShowDialog) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Description(stringResource(R.string.first_marketing_copy))
        Description(stringResource(R.string.pay_monthly_description))
        Row(
            Modifier
                .selectableGroup()
                .padding(start = 8.dp)
        ) {
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
                onValueChange = { authViewModel.setPhone(it) },
                value = phone,
                prefix = {
                    Text(
                        text = authViewModel.countryPhoneCode[authViewModel.defaultRegion]!!,
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
                ),
                isError = accUiState.phone.isNotEmpty() && !accUiState.validDetails.phone
            )
            TextInput(
                readOnly = true,
                onValueChange = {},
                value = authViewModel.landlordSubscriptionFee,
                prefix = {
                    Text(
                        text = authViewModel.countryCurrencyCode[authViewModel.defaultRegion]!!,
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                enabled = false
            )
            if (createPaymentState is ICreatePayment.ApolloError && createPaymentState.message != null && createPaymentState.message.contains("Failed to execute GraphQL http network request")) {
                Text(
                    text = stringResource(id = R.string.network_connection_issue),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.error
                )
            }
            ActionButton(
                isLoading = (createPaymentState is ICreatePayment.Loading),
                text = stringResource(R.string.pay),
                onClick = {
                    authViewModel.createPayment {
                        onShowDialog(true)
                    }
                }
            )
        }
        when {
            showDialog && createPaymentState is ICreatePayment.Success && createPaymentState.success != null -> {
                AlertDialog(
                    onDismissRequest = { return@AlertDialog },
                    onConfirmation = {
                        // TODO send back to create property/unit
                        onShowDialog(false)
                        navigateUp()
                    },
                    dialogTitle = stringResource(R.string.continue_payment),
                    dialogText = createPaymentState.success,
                    icon = Icons.TwoTone.CheckCircle,
                    confirmationText = stringResource(R.string.continue_next)
                )
            }
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