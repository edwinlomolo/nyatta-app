package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Location(modifier: Modifier = Modifier) {
    Onboarding (
        modifier = modifier.fillMaxSize(),
        actionButtonText = stringResource(R.string.enable_location),
        onActionButtonClick = {}
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Title(stringResource(R.string.location))
            Description(stringResource(R.string.location_description))
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