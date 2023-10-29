package com.example.nyatta.compose.listing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun ListingAmenity(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Amenities")
    }
}

@Preview
@Composable
fun ListingAmenityPreview() {
    NyattaTheme {
        ListingAmenity()
    }
}