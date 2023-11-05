package com.example.nyatta.compose.location

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.ui.theme.NyattaTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

object LocationDestination: Navigation {
    override val route = "location"
    override val title = "Location"
}

@Composable
fun Location(
    onboardingViewModel: OnboardingViewModel = viewModel(),
    deviceLocation: LatLng = LatLng(0.0, 0.0)
) {
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val onboardingState = onboardingUiState
    val userDeviceLocation = LatLng(deviceLocation.latitude, deviceLocation.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userDeviceLocation, 15f)
    }

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
            GoogleMap(
                modifier = Modifier.height(200.dp),
                cameraPositionState = cameraPositionState
            ) {
                Marker(
                    state = MarkerState(position = userDeviceLocation),
                    title = stringResource(R.string.current_device_location)
                )
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