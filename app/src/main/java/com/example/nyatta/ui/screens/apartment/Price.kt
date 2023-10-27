package com.example.nyatta.ui.screens.apartment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.TextInput
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

object ApartmentPriceDestination: Navigation {
    override val route = "apartment/price"
    override val title  = "Price"
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Price(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentPriceDestination.title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Onboarding(
                modifier = Modifier.padding(12.dp),
                actionButtonText = "Save",
                onActionButtonClick = {},
                alignBottomCenter = false
            ) {
                Title("Price")
                Description("Monthly charge for this unit")
                TextInput(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier
                        .padding(8.dp),
                    prefix = {
                        Text(
                            text = "KES",
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
    }
}

@Preview
@Composable
fun PricePreview() {
    NyattaTheme {
        Price()
    }
}