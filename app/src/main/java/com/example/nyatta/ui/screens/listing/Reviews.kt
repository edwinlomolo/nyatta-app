package com.example.nyatta.ui.screens.listing

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun ListingReview(modifier: Modifier = Modifier) {
    Text("Reviews")
}

@Preview(showBackground = true)
@Composable
fun ListingReviewPreview() {
    NyattaTheme {
        ListingReview()
    }
}