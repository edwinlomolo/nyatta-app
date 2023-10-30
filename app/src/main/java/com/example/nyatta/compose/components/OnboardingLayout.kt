package com.example.nyatta.compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Onboarding(
    modifier: Modifier = Modifier,
    actionButtonText: String = "",
    onActionButtonClick: () -> Unit = {},
    actionButtonLeadingIcon: @Composable (() -> Unit)? = null,
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    alignBottomCenter: Boolean = true,
    showProceedButton: Boolean = true,
    isActionButtonLoading: Boolean = false,
    navigateBack: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier.padding(8.dp)
            ) {
                Button(
                    onClick = navigateBack,
                    shape = MaterialTheme.shapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Text(
                        text = "Back",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = onActionButtonClick,
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "Next",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = verticalArrangement,
                horizontalAlignment = horizontalAlignment
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun OnboardingLayoutPreview() {
    NyattaTheme {
        Onboarding {}
    }
}