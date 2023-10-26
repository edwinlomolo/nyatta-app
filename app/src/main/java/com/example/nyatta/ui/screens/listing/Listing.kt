package com.example.nyatta.ui.screens.listing

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.ui.screens.home.TopAppBar

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
    Amenity("Internet", "Safaricom home fibre"),
    Amenity("Kitchen", "Open kitchen plan"),
    Amenity("Outdoor", "Front porch"),
    Amenity("Parking", "Free premises parking"),
    Amenity("Internet", "Zuku home fibre")
).groupBy { it.category }


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Listing(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    onNavigateToPhotos: (String) -> Unit = {}
) {
    val image = painterResource(
        R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c
    )

    Scaffold(
        topBar = {
            TopAppBar(
                canNavigateBack = true,
                title = "Unit name",
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            ListingBottomBar()
        }
    ) {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
           Column (
               modifier = Modifier.verticalScroll(rememberScrollState())
           ) {
               Box {
                   Image(
                       painter = image,
                       contentScale = ContentScale.Crop,
                       modifier = Modifier
                           .fillMaxWidth()
                           .height(280.dp),
                       contentDescription = null,
                       alpha = 0.8f
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
                       modifier = Modifier
                           .padding(start = 16.dp, end = 16.dp),
                       shape = MaterialTheme.shapes.small
                   ) {
                       Text(
                           text = "View",
                           style = TextStyle(
                               fontFamily = MabryFont,
                               fontSize = 18.sp
                           )
                       )
                   }
               }
               Section (
                   title = "Pricing"
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
                   title = "Features"
               ) {
                   Row(
                       modifier = Modifier.horizontalScroll(rememberScrollState())
                   ) {
                       repeat(5) {
                           FeaturedAmenities()
                       }
                   }
               }
               Section(
                   title = "Caretaker"
               ) {
                   Row(
                       modifier = Modifier.padding(8.dp),
                       verticalAlignment = Alignment.CenterVertically
                   ) {
                       Image(
                           painterResource(R.drawable.rottweiler),
                           contentDescription = null,
                           contentScale = ContentScale.Crop,
                           modifier = Modifier
                               .size(100.dp)
                               .clip(CircleShape)
                       )
                       Spacer(modifier = Modifier.size(18.dp))
                       Text(
                           text = "First Last Name",
                           style = TextStyle(
                               fontFamily = MabryFont,
                               fontSize = 18.sp,
                               fontWeight = FontWeight.SemiBold
                           )
                       )
                       Icon(
                           painterResource(R.drawable.verify),
                           contentDescription = "Verified",
                           modifier = Modifier.size(24.dp),
                           tint = MaterialTheme.colorScheme.onSurfaceVariant
                       )
                   }
               }
               Section(
                   title = "Amenities",
               ) {
                   LazyHorizontalGrid(
                       rows = GridCells.Fixed(1),
                       modifier = Modifier.height(156.dp)
                   ) {
                       items(amenities.keys.toList()) {
                           Text(
                               text = it,
                               modifier = Modifier.padding(8.dp),
                               fontWeight = FontWeight.Bold
                           )
                           Column(modifier = Modifier.offset(y = 36.dp)) {
                               amenities[it]?.map {
                                   Text(
                                       text = it.name,
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
    modifier: Modifier = Modifier
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
                text = "2",
                style = TextStyle(
                    fontFamily = MabryFont,
                    fontSize = 18.sp
                ),
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.size(12.dp))
            Icon(
                painterResource(R.drawable.bed),
                contentDescription = "Bed",
                modifier = Modifier.size(32.dp)
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
        style = TextStyle(
            fontFamily = MabryFont,
            fontSize = 24.sp,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 16.sp
        )
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
            .background(color = MaterialTheme.colorScheme.background)
            .padding(start = 12.dp, end = 12.dp, bottom = 4.dp, top = 4.dp)
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.surfaceTint
        )
    }
}

@Composable
fun ListingBottomBar(
    modifier: Modifier = Modifier
) {
    BottomAppBar(modifier = modifier) {
        Row {
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
            ) {
                Text(
                    text = "Call",
                    style = TextStyle(
                        fontSize = 18.sp,
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