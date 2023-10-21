package com.example.nyatta.ui.screens.onboarding.property

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.PropertyDescription
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Description(
    modifier: Modifier = Modifier
) {
    PropertyDescription(
        modifier = modifier,
        title = stringResource(R.string.property_name),
        description = stringResource(R.string.property_name_description),
        placeholder = {
            Text(
                text = stringResource(R.string.description_supporting_text)
            )
        }
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        Description()
    }
}