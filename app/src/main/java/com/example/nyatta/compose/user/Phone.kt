package com.example.nyatta.compose.user

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.CircularProgressLoader
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AccountViewModel
import com.example.nyatta.viewmodels.SignInUiState

object UserOnboardingPhoneDestination: Navigation {
    override val route = "user/phone"
    override val title = "Phone number"
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Phone(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    accountViewModel: AccountViewModel = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val signInUiState = accountViewModel.signInUiState
    val userDetail by accountViewModel.userUiDetails.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = UserOnboardingPhoneDestination.title,
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
            Column(
                modifier = Modifier
                    .padding(12.dp),
            ) {
                TextInput(
                    value = userDetail.phone,
                    onValueChange = {
                        accountViewModel.setPhone(it)
                    },
                    isError = userDetail.phone.isNotEmpty() && !userDetail.validDetails.phone,
                    prefix = {
                        Text(
                            text = accountViewModel.countryPhoneCode[accountViewModel.defaultRegion]!!,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
                if (signInUiState is SignInUiState.SignInError && signInUiState.message!!.contains("Failed to execute GraphQL http network request")) {
                    Text(
                        text = stringResource(R.string.network_connection_issue),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                Button(
                    onClick = {
                        accountViewModel.signIn()
                        navigateNext(HomeDestination.route)

                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (signInUiState is SignInUiState.Loading) {
                            CircularProgressLoader()
                        } else {
                            Text(
                                text = stringResource(R.string.create_account),
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PhonePreview() {
    NyattaTheme {
        Phone()
    }
}