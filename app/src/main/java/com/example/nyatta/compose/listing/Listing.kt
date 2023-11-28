package com.example.nyatta.compose.listing

import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetUnitQuery
import com.example.nyatta.R
import com.example.nyatta.compose.components.Loading
import com.example.nyatta.compose.components.NetworkError
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.compose.home.TopAppBar
import com.example.nyatta.viewmodels.ListingViewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch

data class Amenity(
    val category: String,
    val name: String
)

private sealed interface IGetUnit {
    data object Loading: IGetUnit
    data class Success(val unit: GetUnitQuery.GetUnit?): IGetUnit
    data class ApolloError(val message: String?): IGetUnit
}

object ListingDetailsDestination: Navigation {
    override val route = "listing_details"
    override val title = null
    const val listingIdArg = "listingId"
    val routeWithArgs = "${route}/{$listingIdArg}"
}

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
    unitId: String = "",
    listingViewModel: ListingViewModel = viewModel(),
    navigateUp: () -> Unit = {},
    onNavigateToPhotos: (String) -> Unit = {}
) {
    var state by remember { mutableStateOf<IGetUnit>(IGetUnit.Loading) }
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        state = try {
            val res = listingViewModel.getUnit(unitId).dataOrThrow()
            IGetUnit.Success(res.getUnit)
        } catch(e: ApolloException) {
            IGetUnit.ApolloError(e.localizedMessage)
        }
    }

    when (val s = state) {
        IGetUnit.Loading -> Loading()
        is IGetUnit.ApolloError -> NetworkError(errorString = s.message!!)
        is IGetUnit.Success -> {
            val featured = s.unit!!.amenities
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
            val avatar = s.unit.caretaker?.avatar?.upload ?: s.unit.property?.caretaker?.avatar?.upload
            val firstName = s.unit.caretaker?.first_name ?: s.unit.property?.caretaker?.first_name
            val lastName = s.unit.caretaker?.last_name ?: s.unit.property?.caretaker?.last_name
            val phone = s.unit.caretaker?.phone ?: s.unit.property?.caretaker?.phone

            Scaffold(
                topBar = {
                    TopAppBar(
                        canNavigateBack = true,
                        navigateUp = navigateUp
                    )
                },
                bottomBar = {
                    ListingBottomBar(phone = phone!!)
                }
            ) { innerPadding ->
                if (showBottomSheet) {
                    ModalBottomSheet(
                        onDismissRequest = {
                            scope.launch {
                                sheetState.hide()
                            }.invokeOnCompletion {
                                if (!sheetState.isVisible) {
                                    showBottomSheet = false
                                }
                            }
                        },
                        sheetState = sheetState
                    ) {
                        Column(
                            modifier = modifier
                                .fillMaxSize()
                                .padding(innerPadding),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            LazyVerticalStaggeredGrid(
                                columns = StaggeredGridCells.Adaptive(200.dp),
                                verticalItemSpacing = 4.dp,
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                content = {
                                    items(items = s.unit.images, key = { it.id }) {
                                        AsyncImage(
                                            model = ImageRequest.Builder(LocalContext.current)
                                                .data(it.upload)
                                                .crossfade(true)
                                                .build(),
                                            contentDescription = stringResource(R.string.listing_photo),
                                            error = painterResource(R.drawable.ic_broken_image),
                                            placeholder = painterResource(R.drawable.loading_img),
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                        )
                                    }
                                },
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                }
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
                                    .data(s.unit!!.images[0].upload)
                                    .crossfade(true)
                                    .build(),
                                placeholder = painterResource(id = R.drawable.loading_img),
                                error = painterResource(id = R.drawable.ic_broken_image),
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp)
                                    .clickable { showBottomSheet = true },
                                contentDescription = stringResource(id = R.string.listing_image),
                            )
                        }
                        FlowRow(
                            modifier = Modifier
                                .fillMaxWidth(1f)
                                .wrapContentHeight(align = Alignment.Top)
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            Text(
                                text = s.unit.name,
                                style = MaterialTheme.typography.bodyLarge
                            )
                            if (s.unit.bedrooms.isNotEmpty()) {
                                if (s.unit.bedrooms.any { it.master }) Tag(text= stringResource(R.string.master))
                                if (s.unit.bedrooms.any { it.enSuite }) Tag(text= stringResource(R.string.en_suite))
                                Text(
                                    text = "${s.unit.bedrooms.size} bedrooms",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            }
                        }
                        Section (
                            title = stringResource(R.string.pricing)
                        ) {
                            Row(modifier = Modifier.padding(8.dp)) {
                                Text(
                                    text = "KES ${s.unit.price}",
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = when(s.unit.type) {
                                        "1" -> "1 bedroom"
                                        "2" -> "2 bedroom"
                                        "3" -> "3 bedroom"
                                        "4" -> "4 bedroom"
                                        "5" -> "5 bedroom"
                                        "Unit" -> "Apartment Unit"
                                        else -> s.unit.type
                                    },
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                        if (featured.isNotEmpty()) {
                            Section(
                                title = stringResource(R.string.features)
                            ) {
                                Row(
                                    modifier = Modifier.horizontalScroll(rememberScrollState())
                                ) {
                                    featured.map {
                                        featuredAmenityIcon[it]?.let { it1 -> FeaturedAmenities(icon = it1, name = it) }
                                    }
                                }
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
                                        .data(avatar)
                                        .crossfade(true)
                                        .build(),
                                    placeholder = painterResource(id = R.drawable.loading_img),
                                    contentDescription = stringResource(id = R.string.caretaker_image),
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(80.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                )
                                Spacer(modifier = Modifier.size(18.dp))
                                Text(
                                    text = "$firstName $lastName",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                        Section(
                            title = stringResource(R.string.location)
                        ) {
                            val propertyLocation = if (s.unit.location != null) LatLng(s.unit.location.lat, s.unit.location.lng) else LatLng(s.unit.property!!.location!!.lat, s.unit.property.location!!.lng)
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
                            title = stringResource(R.string.amenities),
                        ) {
                            Column(
                                modifier = Modifier.padding(bottom = 8.dp)
                            ) {
                                val amenities = s.unit.amenities.groupBy { it.category }

                                amenities.keys.toList().map { amenity ->
                                    Text(
                                        text = amenity,
                                        modifier = Modifier.padding(8.dp),
                                        style = MaterialTheme.typography.labelSmall
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
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
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
        style = MaterialTheme.typography.titleSmall
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
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ListingBottomBar(
    modifier: Modifier = Modifier,
    phone: String
) {
    val context = LocalContext.current

    BottomAppBar(modifier = modifier) {
        Row {
            Button(
                onClick = {
                    // TODO use caretaker phone
                    Intent(Intent.ACTION_DIAL).apply {
                        data = Uri.parse("tel:$phone")
                    }.also { context.startActivity(it) }
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