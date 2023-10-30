package com.example.nyatta.compose.apartment

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.data.amenities
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Onboarding
import com.example.nyatta.compose.components.Title
import com.example.nyatta.navigation.Navigation
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.viewmodels.ApartmentViewModel

object ApartmentAmenitiesDestination: Navigation {
    override val route = "apartment/amenities"
    override val title = "Amenities"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Amenities(
    modifier: Modifier = Modifier,
    navigateBack: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentAmenitiesDestination.title
            )
        }
    ) { innerPadding ->
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            Onboarding(
                modifier = modifier,
                navigateBack = navigateBack
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
    }
}

@Preview
@Composable
fun AmenitiesPreview() {
    NyattaTheme {
        Amenities()
    }
}