package com.example.nyatta.ui.screens.onboarding.price

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.screens.onboarding.baths.pad
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Price(modifier: Modifier = Modifier) {
    var pricing by remember {
        mutableStateOf("")
    }
    Onboarding(modifier = modifier) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.price_label_text),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.then(pad)
            )
            OutlinedTextField(
                value = pricing,
                onValueChange = {
                    pricing = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .then(pad),
                trailingIcon = {
                    Text(
                        text = stringResource(R.string.pricing_trailing_label),
                        modifier = Modifier.then(pad),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                },
                leadingIcon = {
                    Text(
                        text = stringResource(R.string.pricing_leading_label),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.then(pad)
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PricePreview() {
    NyattaTheme {
        Price()
    }
}