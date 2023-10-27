package com.example.nyatta.ui.screens.apartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.TextInput
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

object UnitTypeDestination: Navigation {
    override val route = "apartment/type"
    override val title = "Apartment type"
}

val unitTypeOptions = listOf("Single Room", "Studio", "1 bedroom", "2 bedroom", "3 bedroom")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Unit(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    var unitType by remember {
        mutableStateOf(unitTypeOptions[0])
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = UnitTypeDestination.title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Onboarding(
                modifier = Modifier.padding(12.dp),
                onActionButtonClick = {
                    navigateNext(ApartmentAmenitiesDestination.route)
                },
                actionButtonText = "Save",
                alignBottomCenter = false
            ) {
                Title(stringResource(R.string.unit_type))
                Description("Tell how this unit looks like")
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextInput(
                        readOnly = true,
                        value = unitType,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        unitTypeOptions.forEach { option ->
                            DropdownMenuItem(
                                text = { Text(option) },
                                onClick = {
                                    unitType = option
                                    expanded = false
                                },
                                contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun UnitPreview() {
    NyattaTheme {
        Unit()
    }
}