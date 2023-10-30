package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.nyatta.navigation.Navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.compose.components.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentBedroomsDestination: Navigation {
    override val route = "apartment/bedrooms"
    override val title = "Bedrooms"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bedroom(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val (master, onMasterChange) = remember { mutableStateOf(false) }
    val (enSuite, onEnSuiteChange) = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentBedroomsDestination.title
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
                navigateBack = navigateBack,
                onActionButtonClick = {
                    navigateNext(ApartmentBathsDestination.route)
                }
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
                        Text (
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
    }
}

@Preview
@Composable
fun BedroomPreview() {
    NyattaTheme {
        Bedroom()
    }
}