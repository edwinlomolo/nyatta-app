package com.example.nyatta.compose.property

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.PropertyViewModel
import kotlinx.coroutines.launch

object PropertyThumbnailDestination: Navigation {
    override val route = "property/onboarding"
    override val title = null
}

@Composable
fun Thumbnail(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel()
) {
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            propertyViewModel.setPropertyThumbnail(it)
        }
    }
    val scope = rememberCoroutineScope()
    val propertyUiState by propertyViewModel.uiState.collectAsState()

    val propertyData = propertyUiState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Description(stringResource(R.string.thumbnail_description))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    propertyData.thumbnail ?: R.drawable.apartments
                )
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
                .size(240.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable {
                    scope.launch {
                        pickMedia.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                }
        )
    }
}

@Preview
@Composable
fun ThumbnailPreview() {
    NyattaTheme {
        Thumbnail()
    }
}