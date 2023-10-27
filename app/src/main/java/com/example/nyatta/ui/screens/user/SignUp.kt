package com.example.nyatta.ui.screens.user

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

object UserSignUpDestination: Navigation {
    override val route = "user/sign_up"
    override val title = "Sign up or Log in"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {}
) {
    BackHandler {
        navigateUp()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = UserSignUpDestination.title,
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
                alignBottomCenter = false,
                actionButtonText = "Continue with Phone",
                onActionButtonClick = {
                    navigateNext(UserOnboardingPhoneDestination.route)
                }
            ) {}
        }
    }
}

@Preview
@Composable
fun SignUpPreview() {
    NyattaTheme {
        SignUp()
    }
}