package com.example.nyatta.compose.uploads

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.ApartmentViewModel
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentData
import kotlinx.coroutines.launch

object UploadsDestination: Navigation {
    override val route = "uploads"
    override val title = "Upload images"
}

@Composable
fun Uploads(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()

    val apartmentData = apartmentUiState
    val hasBedCount = apartmentData.unitType != "Single room" && apartmentData.unitType != "Studio"
    val hasFrontPorch = apartmentData.selectedAmenities.any { it.label == "Front Porch" }
    val hasBalcony = apartmentData.selectedAmenities.any { it.label == "Balcony" }

    Column(
        modifier = modifier
            .padding(8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Title(stringResource(R.string.uploads))
        Description(stringResource(R.string.describe_unit_images))
        FeatureImage(
            text = stringResource(R.string.living_room),
            imageCount = 8,
            apartmentViewModel = apartmentViewModel,
            apartmentData = apartmentData
        )
        FeatureImage(
            text = stringResource(R.string.baths),
            imageCount = 4,
            apartmentViewModel = apartmentViewModel,
            apartmentData = apartmentData
        )
        // TODO if has bedroom count
        if (hasBedCount) {
            FeatureImage(
                text = stringResource(R.string.bedrooms),
                imageCount = 8,
                apartmentViewModel = apartmentViewModel,
                apartmentData = apartmentData
            )
        }
        // TODO if has balcony/front porch
        if (hasBalcony) {
            FeatureImage(
                text = stringResource(R.string.balcony),
                imageCount = 4,
                apartmentViewModel = apartmentViewModel,
                apartmentData = apartmentData
            )
        }
        if (hasFrontPorch) {
            FeatureImage(
                text = stringResource(R.string.front_porch),
                imageCount = 4,
                apartmentViewModel = apartmentViewModel,
                apartmentData = apartmentData
            )
        }
        FeatureImage(
            text = stringResource(R.string.kitchen),
            imageCount = 4,
            apartmentViewModel = apartmentViewModel,
            apartmentData = apartmentData
        )
    }
}

@Composable
fun FeatureImage(
    modifier: Modifier = Modifier,
    text: String,
    imageCount: Int,
    apartmentViewModel: ApartmentViewModel,
    apartmentData: ApartmentData
) {
    val images = apartmentData.images
    val imagesSize = images[text]?.size ?: 0
    val scope = rememberCoroutineScope()
    val pickMultipleMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(imageCount)
    ) {
        if (it.isNotEmpty()) {
            apartmentViewModel.setUnitImages(text, it)
        }
    }

    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(
            text = "$text (${imageCount-imagesSize})",
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()))
        {
            if (imagesSize > 0) {
                images[text]?.forEachIndexed { _, item ->
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(
                                item
                            )
                            .crossfade(true)
                            .build(),
                        contentDescription = "Feature image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(135.dp)
                            .padding(8.dp)
                            .clip(MaterialTheme.shapes.small)
                    )
                }
            }
            if (imagesSize < imageCount) {
                Image(
                    painterResource(R.drawable.image_gallery),
                    contentDescription = stringResource(R.string.rottweiler_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            scope.launch {
                                pickMultipleMedia.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            }
                        }
                )
            }
        }
    }
}

@Preview
@Composable
fun UploadsPreview() {
    NyattaTheme {
        Uploads()
    }
}