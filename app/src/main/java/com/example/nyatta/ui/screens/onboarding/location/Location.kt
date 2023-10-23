package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
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

object LocationDestination: Navigation {
    override val route = "location"
    override val title = "Location"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {}
) {
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
                onActionButtonClick = {}
            ) {
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Title(stringResource(R.string.location))
                    Description(stringResource(R.string.location_description))
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