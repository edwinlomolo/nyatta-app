package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Dropdown
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Location(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(false) }
    var building by remember { mutableStateOf(propertyList[0]) }

    Onboarding(
        modifier = modifier,
        actionButtonText = stringResource(R.string.apartment_location_save),
        onActionButtonClick = {}
    ) {
        Title("Location")
        Description("Select building that shares a location with this unit.")
        Column {
            Dropdown(
                options = propertyList,
                expanded = state,
                onExpandedChange = {
                    state = !state
                },
                onDismissRequest = {
                    state = false
                },
                matchedOption = building
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