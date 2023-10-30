package com.example.nyatta.compose.property

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.ArrowForward
import androidx.compose.material3.Icon
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
import com.example.nyatta.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyDescriptionDestination: Navigation {
    override val route = "property/description"
    override val title = "Property Description"
}

@Composable
fun PropertyDescription(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    propertyViewModel: PropertyViewModel = viewModel()
) {
    val propertyUiState by propertyViewModel.uiState.collectAsState()

    Description(
        modifier = modifier,
        actionButtonLeadingIcon = {
            Icon(
                Icons.TwoTone.ArrowForward,
                contentDescription = stringResource(R.string.declare_caretaker)
            )
        },
        actionButtonText = stringResource(R.string.declare_caretaker),
        onActionButtonClick = { navigateNext(CaretakerDestination.route) },
        title = stringResource(R.string.property_name),
        description = stringResource(R.string.property_name_description),
        placeholder = {
            Text(
                text = stringResource(R.string.description_supporting_text)
            )
        },
        navigateBack = navigateBack,
        appBarTitle = PropertyDescriptionDestination.title,
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