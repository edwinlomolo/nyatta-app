package com.example.nyatta.ui.screens.onboarding.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.data.amenities
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.Title

@Composable
fun Amenities(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Title("Amenities")
            Spacer(modifier = Modifier.weight((1f)))
            IconButton(
                onClick = { /*TODO*/ }
            ) {
                Icon(
                    Icons.Outlined.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier
                        .size(32.dp)
                )
            }
        }
        Description(stringResource(R.string.select_amenities_offered))
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

@Preview
@Composable
fun AmenitiesPreview() {
    NyattaTheme {
        Amenities()
    }
}