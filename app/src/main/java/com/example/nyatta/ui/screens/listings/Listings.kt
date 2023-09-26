package com.example.nyatta.ui.screens.listings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.nyatta.GetListingsQuery

@Composable
fun Listings(listings: GetListingsQuery.Data, modifier: Modifier = Modifier) {
    if (listings.getListings.isNotEmpty()) {
        Text(text = "We have listings to show")
    } else {
        Text(text = "No listings to show for now")
    }
}