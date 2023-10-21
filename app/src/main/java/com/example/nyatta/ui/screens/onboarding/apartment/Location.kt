package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.TextInput
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

val options = listOf("Beach House Properties", "Mwea Ventures")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var building by remember { mutableStateOf(options[0]) }

    Onboarding(
        modifier = modifier,
        actionButtonText = stringResource(R.string.apartment_location_save),
        onActionButtonClick = {}
    ) {
        Title("Location")
        Description("Select building that shares a location with this unit.")
        Column {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextInput(
                    value = building,
                    readOnly = true,
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                    },
                    onValueChange = { /*TODO*/ },
                    modifier = Modifier
                        .padding(8.dp)
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {
                    options.forEach {
                        DropdownMenuItem(
                            text = { Text(it) },
                            onClick = {
                                building = it
                                expanded = false
                            }
                        )
                    }
                }
            }
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