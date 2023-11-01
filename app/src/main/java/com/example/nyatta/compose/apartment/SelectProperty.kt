package com.example.nyatta.compose.apartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object SelectPropertyDestination: Navigation {
    override val route = "apartment/select_property"
    override val title = "Associate with property"
}

val propertyList = listOf("Beach House Properties", "Mwea Ventures")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProperty(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var matchedProperty by remember { mutableStateOf(propertyList[0]) }

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Title(stringResource(R.string.select_property))
        Description(stringResource(R.string.associate_unit_property))
        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = apartmentUiState.associatedTo,
                    onClick = { apartmentViewModel.setAssociatedTo(true) },
                    modifier = Modifier.semantics { contentDescription = "Associate property" }
                )
                Text(
                    text = stringResource(R.string.yes_caretaker)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !apartmentUiState.associatedTo,
                    onClick = { apartmentViewModel.setAssociatedTo(false) },
                    modifier = Modifier.semantics { contentDescription = "Don't associate property" }
                )
                Text(
                    text = stringResource(R.string.no_caretaker)
                )
            }
        }
        if (apartmentUiState.associatedTo) {
            Column {
                Box {
                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        }
                    ) {
                        TextInput(
                            value = matchedProperty,
                            onValueChange = { /*TODO*/ },
                            readOnly = true,
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                            },
                            modifier = Modifier
                                .menuAnchor()
                                .padding(start = 8.dp, end = 8.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = {
                                expanded = false
                            },
                            modifier = Modifier
                                .background(MaterialTheme.colorScheme.background)
                        ) {
                            propertyList.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        matchedProperty = it
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
}

@Preview
@Composable
fun SelectPropertyPreview() {
    NyattaTheme {
        SelectProperty()
    }
}