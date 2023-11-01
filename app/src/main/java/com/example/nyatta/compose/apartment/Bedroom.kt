package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentBedroomsDestination: Navigation {
    override val route = "apartment/bedrooms"
    override val title = "Bedrooms"
}

@Composable
fun Bedroom(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val (master, onMasterChange) = remember { mutableStateOf(false) }
    val (enSuite, onEnSuiteChange) = remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .padding(8.dp)
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
            Row(
                modifier = Modifier
                    .toggleable(
                        value = false,
                        onValueChange = { onMasterChange(!master) },
                        role = Role.Checkbox
                    )
                    .padding(horizontal = 8.dp),
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
                Text(
                    text = stringResource(R.string.en_suite)
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