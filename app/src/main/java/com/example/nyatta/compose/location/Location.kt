package com.example.nyatta.compose.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.ui.theme.NyattaTheme

object LocationDestination: Navigation {
    override val route = "location"
    override val title = "Location"
}

@Composable
fun Location(
    onboardingViewModel: OnboardingViewModel = viewModel()
) {
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val onboardingState = onboardingUiState

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Title(stringResource(R.string.location))
        if (onboardingState.type == "Apartments Building") {
            Description(stringResource(R.string.location_description_property))
        } else {
            Description(stringResource(R.string.location_description_apartment))
        }
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(
                        R.drawable.kenya_gps_map
                    )
                    .crossfade(true)
                    .build(),
                contentDescription = "Kenya gps map",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(top = 8.dp)
            )
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