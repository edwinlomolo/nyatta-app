package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.data.amenities
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentAmenitiesDestination: Navigation {
    override val route = "apartment/amenities"
    override val title = "Amenities"
}

@Composable
fun Amenities(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Title("Amenities")
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