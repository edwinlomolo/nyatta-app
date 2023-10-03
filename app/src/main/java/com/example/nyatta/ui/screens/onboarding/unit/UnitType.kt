package com.example.nyatta.ui.screens.onboarding.unit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme

val pad = Modifier.padding(8.dp)
val options = listOf("Single Room", "Studio", "1 bedroom", "2 bedroom", "3 bedroom")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Unit(modifier: Modifier = Modifier) {
    var unitType by remember {
        mutableStateOf(options[0])
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(
                    text = "Select unit type",
                    modifier = Modifier.then(pad),
                    style = MaterialTheme.typography.titleMedium
                )
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = unitType,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .menuAnchor()
                            .fillMaxWidth()
                            .then(pad)
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = {
                            expanded = false
                        },
                        modifier = Modifier
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        options.forEach { option ->
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
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(R.string.next_text),
                    modifier = Modifier.then(pad),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UnitPreview() {
    NyattaTheme {
        Unit()
    }
}