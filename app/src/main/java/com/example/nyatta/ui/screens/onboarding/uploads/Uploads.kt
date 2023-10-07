package com.example.nyatta.ui.screens.onboarding.uploads

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.screens.onboarding.baths.pad
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Uploads(modifier: Modifier = Modifier) {
    Onboarding(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            FeatureImage(text = stringResource(R.string.living_room), imageCount = 3)
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

@Composable
fun FeatureImage(text: String, imageCount: Int, modifier: Modifier = Modifier) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.then(pad)
        )
        Row(modifier = Modifier
            .padding(8.dp)
            .horizontalScroll(rememberScrollState())) {
            repeat(imageCount) {
                Image(
                    painter = painterResource(R.drawable.rottweiler),
                    contentDescription = "Rottweiler",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(135.dp)
                        .padding(8.dp)
                        .clip(MaterialTheme.shapes.small)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UploadsPreview() {
    NyattaTheme {
        Uploads()
    }
}