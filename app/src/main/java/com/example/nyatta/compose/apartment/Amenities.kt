package com.example.nyatta.compose.apartment

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
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
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()
    val amenities = apartmentViewModel.defaultAmenities

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Title("Amenities")
        Description(stringResource(R.string.select_amenities_offered))
        LazyColumn {
            items(amenities, key = { it.id }) { amenity ->
                ListItem(
                    modifier = Modifier
                        .clickable{
                            apartmentViewModel.addUnitAmenity(amenity)
                        },
                    headlineContent = {
                        Row {
                            Text(
                                text = amenity.label
                            )
                            if (apartmentUiState.selectedAmenities.any { it.id == amenity.id }) {
                                Spacer(modifier = Modifier.size(28.dp))
                                Icon(
                                    Icons.TwoTone.Check,
                                    contentDescription = amenity.label
                                )
                            } else null
                        }
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