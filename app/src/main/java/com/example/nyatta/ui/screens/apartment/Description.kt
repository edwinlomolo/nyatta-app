package com.example.nyatta.ui.screens.apartment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.NyattaViewModelProvider
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.navigation.Navigation

object ApartmentDescriptionDestination: Navigation {
    override val route = "apartment/description"
    override val title = "Apartment Description"
}

@Composable
fun ApartmentDescription(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val uiState by apartmentViewModel.uiState.collectAsState()

    Description(
        modifier = modifier,
        onActionButtonClick = {
            navigateNext(SelectPropertyDestination.route)
        },
        navigateUp = navigateUp,
        title = stringResource(R.string.unit_name),
        description = stringResource(R.string.apartment_description),
        placeholder = {
            Text(
                text = stringResource(R.string.apartment_name_example)
            )
        },
        appBarTitle = ApartmentDescriptionDestination.title,
        onValueChange = { apartmentViewModel.setName(it) },
        value = uiState.description
    )
}

@Preview
@Composable
fun ApartmentDescriptionPreview() {
    NyattaTheme {
        ApartmentDescription()
    }
}