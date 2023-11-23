package com.example.nyatta.compose.apartment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.viewmodels.OnboardingViewModel

object ApartmentDescriptionDestination: Navigation {
    override val route = "apartment/description"
    override val title = "Apartment Description"
}

@Composable
fun ApartmentDescription(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel(),
    onboardingViewModel: OnboardingViewModel = viewModel()
) {
    val uiState by apartmentViewModel.uiState.collectAsState()
    val onboardingState by onboardingViewModel.uiState.collectAsState()
    val reportError = if (uiState.description.isNotEmpty()) !uiState.dataValidity.description else false
    val descriptionHeadline = when(onboardingState.type) {
        "Unit" -> stringResource(id = R.string.apartment_description)
        "Condo" -> stringResource(id = R.string.apartment_description)
        "Homestead" -> stringResource(R.string.home_description)
        else -> ""
    }

    Description(
        modifier = modifier,
        isError = reportError,
        description = descriptionHeadline,
        placeholder = {
            Text(
                text = stringResource(R.string.apartment_name_example)
            )
        },
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