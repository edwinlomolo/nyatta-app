package com.example.nyatta.compose.user

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.ui.theme.NyattaTheme

object UserSignUpDestination: Navigation {
    override val route = "user/sign_up"
    override val title = "Sign up or Log in"
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUp(
    modifier: Modifier = Modifier,
    text: @Composable (() -> Unit)? = null,
    navigateNext: (String) -> Unit = {}
) {
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
            Column(
                modifier = Modifier.padding(12.dp)
            ) {
                if (text != null) text()
                Button(
                    onClick = {
                        navigateNext(UserOnboardingPhoneDestination.route)
                    },
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = stringResource(R.string.continue_with_phone),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
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