package com.example.nyatta.compose.property

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyDescriptionDestination: Navigation {
    override val route = "property/description"
    override val title = "Property Description"
}

@Composable
fun PropertyDescription(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel()
) {
    val propertyUiState by propertyViewModel.uiState.collectAsState()

    Description(
        isError = !propertyUiState.validToProceed.description,
        modifier = modifier,
        description = stringResource(R.string.property_name_description),
        placeholder = {
            Text(
                text = stringResource(R.string.description_supporting_text)
            )
        },
        onValueChange = { propertyViewModel.setName(it) },
        value = propertyUiState.description
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        PropertyDescription()
    }
}