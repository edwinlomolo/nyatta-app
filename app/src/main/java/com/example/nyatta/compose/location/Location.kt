package com.example.nyatta.compose.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.compose.screens.OnboardingViewModel
import com.example.nyatta.ui.theme.NyattaTheme

object LocationDestination: Navigation {
    override val route = "location"
    override val title = "Location"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateToNext: (String) -> Unit = {},
    onboardingViewModel: com.example.nyatta.compose.screens.OnboardingViewModel = viewModel()
) {
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                canNavigateBack = true,
                navigateUp = navigateUp,
                title = LocationDestination.title
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Onboarding (
                modifier = modifier.fillMaxSize(),
                actionButtonText = stringResource(R.string.enable_location),
                onActionButtonClick = {
                    navigateToNext(TownDestination.route)
                },
                alignBottomCenter = false
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Title(stringResource(R.string.location))
                    if (onboardingUiState.type == "Apartments Building") {
                        Description(stringResource(R.string.location_description_property))
                    } else {
                        Description(stringResource(R.string.location_description_apartment))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LocationPreview() {
    NyattaTheme {
        Location()
    }
}