package com.example.nyatta.compose.listing

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.compose.home.TopAppBar
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

data class Amenity(
    val category: String,
    val name: String
)

object ListingDetailsDestination: Navigation {
    override val route = "listing_details"
    override val title = null
    const val listingIdArg = "listingId"
    val routeWithArgs = "${route}/{$listingIdArg}"
}

val amenities = listOf(
    Amenity("Internet", "Safaricom Home Fibre"),
    Amenity("Kitchen", "Open Kitchen Plan"),
    Amenity("Outdoor", "Front Porch"),
    Amenity("Parking", "Free Premises Parking"),
    Amenity("Internet", "Zuku Home Fibre")
)

val featuredAmenities = setOf(
    "Front Porch",
    "Balcony",
    "Piped Water",
    "Dining Area/Space",
    "Kitchen",
    "Internet"
)
val featuredAmenityIcon = mapOf(
    "Front porch" to R.drawable.veranda,
    "Balcony" to R.drawable.balcony,
    "Kitchen" to R.drawable.open_kitchen,
    "Piped Water" to R.drawable.tap,
    "Dining Area/Space" to R.drawable.dining_icon,
    "Internet" to R.drawable.internet_icon
)

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Listing(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    onNavigateToPhotos: (String) -> Unit = {}
) {
    val image = R.drawable.vacant_unfurnished_apartment_with_a_balcony_and_african_00656184_015b_4296_8063_d4957def7a7d

    Scaffold(
        topBar = {
            TopAppBar(
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            ListingBottomBar()
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
           Column (
               modifier = Modifier.verticalScroll(rememberScrollState())
           ) {
               Box {
                   AsyncImage(
                       model = ImageRequest.Builder(LocalContext.current)
                           .data(image)
                           .crossfade(true)
                           .build(),
                       placeholder = painterResource(id = R.drawable.loading_img),
                       error = painterResource(id = R.drawable.ic_broken_image),
                       contentScale = ContentScale.Crop,
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(280.dp),
                       contentDescription = stringResource(id = R.string.listing_image),
                   )
                   FlowRow(
                       modifier = Modifier
                           .fillMaxWidth(1f)
                           .wrapContentHeight(align = Alignment.Top)
                           .padding(8.dp),
                       horizontalArrangement = Arrangement.spacedBy(10.dp),
                       verticalArrangement = Arrangement.spacedBy(10.dp),
                   ) {
                       Tag(text= stringResource(R.string.master))
                       Tag(text= stringResource(R.string.en_suite))
                   }
               }
               Row(
                   modifier = Modifier
                       .fillMaxWidth()
                       .offset(y = (-50).dp),
                   horizontalArrangement = Arrangement.End
               ) {
                   Button(
                       onClick = { onNavigateToPhotos(ListingPhotosDestination.route) },
                       colors = ButtonDefaults.buttonColors(
                           containerColor = MaterialTheme.colorScheme.background
                       ),
                       modifier = Modifier
                           .padding(start = 16.dp, end = 16.dp),
                       shape = MaterialTheme.shapes.small
                   ) {
                       Text(
                           text = stringResource(R.string.view),
                           color = MaterialTheme.colorScheme.primary,
                           style = MaterialTheme.typography.labelSmall
                       )
                   }
               }
               Section (
                   title = stringResource(R.string.pricing)
               ) {
                   Row(modifier = Modifier.padding(8.dp)) {
                       Text(
                           text = "KES 10,000",
                       )
                       Spacer(modifier = Modifier.weight(1f))
                       Text(
                           text = "2 bedroom",
                       )
                   }
               }
               Section(
                   title = stringResource(R.string.features)
               ) {
                   val featured = amenities
                       .filter {
                           featuredAmenities.contains(it.name) || it.name.contains("Kitchen") || (it.name == "Safaricom Home Fibre" || it.name == "Zuku Home Fibre")
                       }
                       .map {
                           if (it.name == "Safaricom Home Fibre" || it.name == "Zuku Home Fibre" || it.name.contains("Kitchen")) {
                               it.category
                           } else {
                               it.name
                           }
                       }
                       .union(listOf())

                   Row(
                       modifier = Modifier.horizontalScroll(rememberScrollState())
                   ) {
                       featured.map {
                           featuredAmenityIcon[it]?.let { it1 -> FeaturedAmenities(icon = it1, name = it) }
                       }
                   }
               }
               Section(
                   title = stringResource(R.string.location)
               ) {
                   val propertyLocation = LatLng(37.4219983, -122.084)
                   val cameraPositionState = rememberCameraPositionState {
                       position = CameraPosition.fromLatLngZoom(propertyLocation, 15f)
                   }

                   GoogleMap(
                       modifier = Modifier
                           .height(160.dp)
                           .padding(8.dp),
                       cameraPositionState = cameraPositionState
                   ) {
                       Marker(
                           state = MarkerState(position = propertyLocation)
                       )
                   }
               }
               Section(
                   title = stringResource(R.string.caretaker)
               ) {
                   Row(
                       modifier = Modifier.padding(8.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       AsyncImage(
                           model = ImageRequest.Builder(LocalContext.current)
                               .data(R.drawable.rottweiler)
                               .crossfade(true)
                               .build(),
                           contentDescription = stringResource(id = R.string.caretaker_image),
                           contentScale = ContentScale.Crop,
                           modifier = Modifier
                               .size(80.dp)
                               .clip(RoundedCornerShape(8.dp))
                       )
                       Spacer(modifier = Modifier.size(18.dp))
                       Text(
                           text = "John Doe",
                           style = MaterialTheme.typography.titleSmall
                       )
                   }
               }
               Section(
                   title = stringResource(R.string.amenities),
               ) {
                   Column(
                       modifier = Modifier.padding(bottom = 8.dp)
                   ) {
                       val amenities = amenities.groupBy { it.category }

                       amenities.keys.toList().map { amenity ->
                           Text(
                               text = amenity,
                               modifier = Modifier.padding(8.dp),
                               style = MaterialTheme.typography.titleSmall
                           )
                           Column {
                               amenities[amenity]?.map {
                                   Text(
                                       text = it.name,
                                       style = MaterialTheme.typography.bodyLarge,
                                       modifier = Modifier.padding(start = 16.dp)
                                   )
                               }
                           }
                       }
                   }
               }
           }
        }
    }
}

@Composable
fun FeaturedAmenities(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    name: String
) {
    Column(
        modifier = modifier
            .padding(8.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant
            )
            .border(BorderStroke(1.dp, color = MaterialTheme.colorScheme.primary))
            .height(132.dp)
            .width(140.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                painterResource(icon),
                contentDescription = name,
                modifier = Modifier
                    .size(32.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String,
    content: @Composable () -> Unit
) {
    Text(
        modifier = modifier.padding(8.dp),
        text = title,
        style = MaterialTheme.typography.titleMedium
    )
    Divider(modifier = Modifier.padding(8.dp))
    content()
}

@Composable
fun Tag(
    modifier: Modifier = Modifier,
    text: String
) {
    Box(
        modifier = modifier
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = MaterialTheme.shapes.small
            )
            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp, top = 4.dp)
    ) {
        Text(
            text = text,
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodySmall
        )
    }
}

@Composable
fun ListingBottomBar(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    BottomAppBar(modifier = modifier) {
        Row {
            Button(
                onClick = {
                    // TODO use caretaker phone
                    val dialIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:0792921440")
                    }
                    context.startActivity(dialIntent)
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(
                    text = stringResource(R.string.call),
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun ListingPreview() {
    NyattaTheme {
        Listing()
    }
}