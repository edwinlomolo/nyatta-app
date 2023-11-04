package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentBathsDestination: Navigation {
    override val route = "apartment/baths"
    override val title = "Bathrooms"
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Bath(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    val apartmentData = apartmentUiState
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Title(stringResource(R.string.bath_label_text))
        Description(stringResource(R.string.tell_baths))
        TextInput(
            value = apartmentData.bathrooms,
            onValueChange = { apartmentViewModel.setBathrooms(it) },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            placeholder = {
                Text(stringResource(R.string.bath_label_text))
            },
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