package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.ui.components.onboarding.PropertyDescription

@Composable
fun ApartmentDescription(
    modifier: Modifier = Modifier
) {
    PropertyDescription(
        modifier = modifier,
        title = stringResource(R.string.unit_name),
        description = stringResource(R.string.apartment_description),
        placeholder = {
            Text(
                text = stringResource(R.string.apartment_name_example)
            )
        }
    )
}

@Preview
@Composable
fun ApartmentDescriptionPreview() {
    NyattaTheme {
        ApartmentDescription()
    }
}