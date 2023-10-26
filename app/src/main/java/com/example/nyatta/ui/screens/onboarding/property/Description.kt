package com.example.nyatta.ui.screens.onboarding.property

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.NyattaViewModelProvider
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme

object PropertyDescriptionDestination: Navigation {
    override val route = "property/description"
    override val title = "Property Description"
}

@Composable
fun PropertyDescription(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    propertyViewModel: PropertyViewModel = viewModel(factory = NyattaViewModelProvider.Factory)
) {
    val uiState by propertyViewModel.uiState.collectAsState()

    Description(
        modifier = modifier,
        onActionButtonClick = { navigateNext(CaretakerDestination.route) },
        title = stringResource(R.string.property_name),
        description = stringResource(R.string.property_name_description),
        placeholder = {
            Text(
                text = stringResource(R.string.description_supporting_text)
            )
        },
        navigateUp = navigateUp,
        appBarTitle = PropertyDescriptionDestination.title,
        onValueChange = { propertyViewModel.setName(it) },
        value = uiState.description
    )
}

@Preview
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        PropertyDescription()
    }
}