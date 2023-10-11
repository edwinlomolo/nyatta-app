package com.example.nyatta.ui.screens.onboarding.amenities

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.data.amenities

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Amenities(modifier: Modifier = Modifier) {
    Onboarding(modifier = modifier) {
        LazyColumn {
            items(amenities) { amenity ->
                ListItem(
                    headlineContent = {
                        Text(
                            text = amenity.label
                        )
                    }
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