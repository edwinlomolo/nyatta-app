package com.example.nyatta.compose.apartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object UnitTypeDestination: Navigation {
    override val route = "apartment/type"
    override val title = "Apartment type"
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Unit(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    val unitTypeOptions = apartmentViewModel.unitTypeOptions
    val unitType = apartmentUiState.unitType
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier
            .padding(12.dp)
    ) {
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
                    val typeOption = if (option != "Single room" && option != "Studio") "$option bedroom" else option
                    DropdownMenuItem(
                        text = { Text(typeOption) },
                        onClick = {
                            apartmentViewModel.setUnitType(option)
                            expanded = false
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                    )
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