package com.example.nyatta.compose.listings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nyatta.GetListingsQuery

@Composable
fun Listings(listings: GetListingsQuery.Data, modifier: Modifier = Modifier) {
    if (listings.getListings.isNotEmpty()) {
        Text(
            text = "We have listings to show",
            style = MaterialTheme.typography.titleSmall
        )
    } else {
        Text(
            text = "No listings to show for now",
            style = MaterialTheme.typography.titleSmall
        )
    }
}