package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.viewmodels.AuthViewModel

object ApartmentPriceDestination: Navigation {
    override val route = "apartment/price"
    override val title  = "Price"
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Price(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel(),
    authViewModel: AuthViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    val apartmentData = apartmentUiState
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(12.dp)
    ) {
        Description(stringResource(R.string.unit_monthly_charge))
        TextInput(
            value = apartmentData.price,
            onValueChange = { apartmentViewModel.setUnitPrice(it) },
            modifier = Modifier
                .padding(8.dp),
            prefix = {
                Text(
                    text = authViewModel.countryCurrencyCode[authViewModel.defaultRegion]!!,
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
    }
}

@Preview
@Composable
fun PricePreview() {
    NyattaTheme {
        Price()
    }
}