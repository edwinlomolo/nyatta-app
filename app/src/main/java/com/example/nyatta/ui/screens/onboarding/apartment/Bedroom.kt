package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Bedroom(modifier: Modifier = Modifier) {
    val (master, onMasterChange) = remember { mutableStateOf(false) }
    val (enSuite, onEnSuiteChange) = remember { mutableStateOf(false) }

    Onboarding(
        modifier = modifier,
        actionButtonText = "Save",
        onActionButtonClick = {}
    ) {
        Title(stringResource(R.string.bedroom_title))
        Description(stringResource(R.string.describe_bedrooms))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(R.string.bedroom, 1)
            )
            Spacer(modifier = Modifier.weight(1f))
            Row(
                modifier = Modifier
                    .toggleable(
                        value = false,
                        onValueChange = { onMasterChange(!master) },
                        role = Role.Checkbox
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.master)
                )
                Checkbox(
                    checked = master,
                    onCheckedChange = onMasterChange
                )
            }
            Row(
                modifier = Modifier
                    .toggleable(
                        value = enSuite,
                        onValueChange = { onEnSuiteChange(!enSuite) },
                        role = Role.Checkbox
                    ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text (
                    text = stringResource(R.string.ensuite)
                )
                Checkbox(
                    checked = enSuite,
                    onCheckedChange = onEnSuiteChange
                )
            }
        }
    }
}

@Preview
@Composable
fun BedroomPreview() {
    NyattaTheme {
        Bedroom()
    }
}