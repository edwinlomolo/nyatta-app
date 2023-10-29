package com.example.nyatta.compose.apartment

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentLocationDestination: Navigation {
    override val route = "apartment/location"
    override val title = "Apartment location"
}

val options = listOf("Beach House Properties", "Mwea Ventures")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var building by remember { mutableStateOf(options[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentLocationDestination.title,
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
                actionButtonText = stringResource(R.string.apartment_location_save),
                onActionButtonClick = {
                    navigateNext(UnitTypeDestination.route)
                },
                alignBottomCenter = false
            ) {
                Title(stringResource(R.string.location))
                Description(stringResource(R.string.location_building_share))
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.TopStart)
                ) {
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
                            options.forEach {
                                DropdownMenuItem(
                                    text = { Text(it) },
                                    onClick = {
                                        building = it
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
fun LocationPreview() {
    NyattaTheme {
        Location()
    }
}