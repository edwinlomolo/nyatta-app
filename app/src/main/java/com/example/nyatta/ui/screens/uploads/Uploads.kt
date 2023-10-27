package com.example.nyatta.ui.screens.uploads

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.ui.components.ActionButton
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.screens.apartment.ApartmentStateDestination
import com.example.nyatta.ui.screens.apartment.ApartmentViewModel
import com.example.nyatta.ui.theme.NyattaTheme

object UploadsDestination: Navigation {
    override val route = "uploads"
    override val title = "Upload images"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Uploads(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = UploadsDestination.title,
                canNavigateBack = true,
                navigateUp = navigateUp
            )
        },
        bottomBar = {
            Row(modifier = Modifier.padding(4.dp)) {
                ActionButton(
                    text = "Save",
                    onClick = {
                        navigateNext(ApartmentStateDestination.route)
                    }
                )
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(12.dp)
            ) {
                Title(stringResource(R.string.uploads))
                Description(stringResource(R.string.describe_unit_images))
                FeatureImage(text = stringResource(R.string.living_room), imageCount = 4)
                FeatureImage(text = stringResource(R.string.baths), imageCount = 2)
                // TODO if has bedroom count
                FeatureImage(text = stringResource(R.string.bedrooms), imageCount = 3)
                // TODO if has balcony/front porch
                FeatureImage(text = stringResource(R.string.balcony), imageCount = 1)
                FeatureImage(text = stringResource(R.string.front_porch), imageCount = 1)
                FeatureImage(text = stringResource(R.string.kitchen), imageCount = 2)
                // TODO if has parking
                FeatureImage(text = stringResource(R.string.parking), imageCount = 2)
            }
        }
    }
}

@Composable
fun FeatureImage(text: String, imageCount: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .padding(8.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())) {
            repeat(imageCount) {
                Image(
                    painter = painterResource(R.drawable.rottweiler),
                    contentDescription = stringResource(R.string.rottweiler_description),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(135.dp)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
            // TODO show if there no images picked
            Image(
                Icons.Outlined.Add,
                contentDescription = stringResource(R.string.rottweiler_description),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(135.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.inversePrimary)
                    .clickable{}
            )
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