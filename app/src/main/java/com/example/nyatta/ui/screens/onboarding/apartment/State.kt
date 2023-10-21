package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Row
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
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun UnitState(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableStateOf(true) }

    Onboarding(
        modifier = modifier,
        actionButtonText = "Save",
        onActionButtonClick = {}
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