package com.example.nyatta.compose.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.CheckCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.AlertDialog
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.navigation.PaymentGraph
import com.example.nyatta.data.model.User
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.PropertyViewModel
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
    user: User = User(),
    navigateNext: (String) -> Unit = {},
    onboardingViewModel: OnboardingViewModel = viewModel(),
    propertyViewModel: PropertyViewModel = viewModel(),
    deviceLocation: LatLng = LatLng(0.0, 0.0)
) {
    val propertyUiState by propertyViewModel.uiState.collectAsState()
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val onboardingState = onboardingUiState
    val userDeviceLocation = LatLng(deviceLocation.latitude, deviceLocation.longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userDeviceLocation, 15f)
    }
    val propertySubmitted = propertyUiState.submitted

    Column(
        modifier = Modifier.padding(12.dp)
    ) {
        if (onboardingState.type == "Apartments Building") {
            Description(stringResource(R.string.location_description_property))
        } else {
            Description(stringResource(R.string.location_description_apartment))
        }
        GoogleMap(
            modifier = Modifier
                .height(200.dp)
                .padding(top = 8.dp),
            cameraPositionState = cameraPositionState
        ) {
            Marker(
                state = MarkerState(position = userDeviceLocation),
                title = stringResource(R.string.current_device_location)
            )
        }
        when {
            propertySubmitted -> {
                AlertDialog(
                    onDismissRequest = { /*TODO*/ },
                    onConfirmation = {
                        if (!user.isLandlord) {
                            propertyViewModel.setSubmitted(false)
                            navigateNext(PaymentGraph.route)
                        } else {
                            propertyViewModel.setSubmitted(false)
                        }
                    },
                    dialogTitle = if (!user.isLandlord) stringResource(R.string.subscribe_to_continue) else stringResource(
                        R.string.congratulations
                    ),
                    dialogText = if (!user.isLandlord) stringResource(R.string.not_landlord_line) else stringResource(
                        R.string.subscribed_landlord_line
                    ),
                    icon = Icons.TwoTone.CheckCircle,
                    confirmationText = stringResource(R.string.subscribe)
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