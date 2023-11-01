package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
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
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentStateDestination: Navigation {
    override val route = "apartment/state"
    override val title = "Apartment state"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitState(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    var state by remember { mutableStateOf(true) }

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Title(stringResource(R.string.unit_state))
        Description(stringResource(R.string.tell_unit_state))
        Row {
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = state,
                    onClick = { state = true }
                )
                Text(stringResource(R.string.vacant))
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = !state,
                    onClick = { state = false }
                )
                Text(stringResource(R.string.occupied))
            }
        }
    }
}

@Preview
@Composable
fun UnitStatePreview() {
    NyattaTheme {
        UnitState()
    }
}