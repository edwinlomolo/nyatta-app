package com.example.nyatta.ui.screens.onboarding.amenities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Amenities(modifier: Modifier = Modifier) {
    Onboarding(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.amenities_label),
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )
            Row(
                modifier = Modifier.clickable(
                    onClick = {}
                )
            ) {
                Text(
                    text = stringResource(R.string.select_amenities),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.size(32.dp))
                Icon(
                    Icons.Filled.ArrowForward,
                    modifier = Modifier.size(32.dp),
                    contentDescription = null
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AmenitiesPreview() {
    NyattaTheme {
        Amenities()
    }
}