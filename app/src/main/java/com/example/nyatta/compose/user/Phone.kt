package com.example.nyatta.compose.user

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.nyatta.ui.components.TextInput
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.screens.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AccountUiState
import com.example.nyatta.viewmodels.AccountViewModel
import kotlinx.coroutines.launch

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
    val scope = rememberCoroutineScope()
    val keyboardController = LocalSoftwareKeyboardController.current
    val accUiState = accountViewModel.accUiState
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
            Onboarding(
                modifier = Modifier
                    .padding(12.dp),
                alignBottomCenter = false,
                isActionButtonLoading = accUiState is AccountUiState.Loading,
                actionButtonText = stringResource(R.string.create_account),
                onActionButtonClick = {
                    scope.launch {
                        accountViewModel.signIn {
                            navigateNext(StartOnboardingDestination.route)
                        }
                    }
                }
            ) {
                TextInput(
                    value = userDetail.phone,
                    onValueChange = {
                        accountViewModel.setPhone(it)
                    },
                    isError = !userDetail.validDetails,
                    prefix = {
                        Text(
                            "+254",
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