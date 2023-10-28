package com.example.nyatta.ui.components

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
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
    content: @Composable () -> Unit
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
        // TODO has to be another way to do this
        if (alignBottomCenter) Spacer(modifier = Modifier.weight(1f))
        if (showProceedButton) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                if (!isActionButtonLoading) {
                    Button(
                        onClick = { onActionButtonClick() },
                        modifier = Modifier
                            .padding(top = 4.dp)
                            .fillMaxWidth(),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = actionButtonText,
                            style = MaterialTheme.typography.titleSmall
                        )
                        if (actionButtonLeadingIcon != null) actionButtonLeadingIcon()
                    }
                } else {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun OnboardingPreview() {
    NyattaTheme {
        Onboarding {}
    }
}