package com.example.nyatta.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.home.TopAppBar

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Description(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    onValueChange: (String) -> Unit = {},
    value: String,
    onActionButtonClick: () -> Unit = {},
    actionButtonText: String = stringResource(R.string.save_description),
    placeholder: @Composable (() -> Unit)? = null,
    navigateUp: () -> Unit = {},
    appBarTitle: String,
    actionButtonLeadingIcon: @Composable (() -> Unit)? = null
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                title = appBarTitle,
                scrollBehavior = scrollBehavior,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) {innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Onboarding(
                actionButtonLeadingIcon = actionButtonLeadingIcon,
                modifier = Modifier.padding(12.dp),
                onActionButtonClick = onActionButtonClick,
                actionButtonText = actionButtonText,
                alignBottomCenter = false
            ) {
                Column {
                    Title(title)
                    Description(description)
                    TextInput(
                        value = value,
                        onValueChange = { onValueChange(it) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                keyboardController?.hide()
                            }
                        ),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        placeholder = placeholder
                    )
                }
            }
        }
    }
}