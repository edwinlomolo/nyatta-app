package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

object ApartmentStateDestination: Navigation {
    override val route = "apartment/state"
    override val title = "Apartment state"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitState(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {}
) {
    var state by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentStateDestination.title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            Onboarding(
                modifier = Modifier.padding(12.dp),
                actionButtonText = "Save",
                onActionButtonClick = {
                    navigateNext(ApartmentPriceDestination.route)
                },
                alignBottomCenter = false
            ) {
                Title(stringResource(R.string.unit_state))
                Description(stringResource(R.string.tell_unit_state))
                Row {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = state,
                            onClick = { state = true }
                        )
                        Text(stringResource(R.string.vacant))
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RadioButton(
                            selected = !state,
                            onClick = { state = false }
                        )
                        Text(stringResource(R.string.occupied))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UnitStatePreview() {
    NyattaTheme {
        UnitState()
    }
}