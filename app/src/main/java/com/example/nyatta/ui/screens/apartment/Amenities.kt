package com.example.nyatta.ui.screens.apartment

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar

object ApartmentAmenitiesDestination: Navigation {
    override val route = "apartment/amenities"
    override val title = "Amenities"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Amenities(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = ApartmentAmenitiesDestination.title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        }
    ) { innerPadding ->
        Surface(modifier = modifier
            .fillMaxSize()
            .padding(innerPadding)
        ) {
            Column(
                modifier = modifier
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Title("Amenities")
                    Spacer(modifier = Modifier.weight((1f)))
                    IconButton(
                        onClick = { navigateNext(ApartmentBedroomsDestination.route) }
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
    }
}

@Preview
@Composable
fun AmenitiesPreview() {
    NyattaTheme {
        Amenities()
    }
}