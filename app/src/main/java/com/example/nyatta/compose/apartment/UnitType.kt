package com.example.nyatta.compose.apartment

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
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.components.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object UnitTypeDestination: Navigation {
    override val route = "apartment/type"
    override val title = "Apartment type"
}

val unitTypeOptions = listOf("Single Room", "Studio", "1 bedroom", "2 bedroom", "3 bedroom")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Unit(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
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
                title = UnitTypeDestination.title
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
                navigateBack = navigateBack
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