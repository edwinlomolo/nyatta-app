package com.example.nyatta.ui.screens.onboarding.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Face(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(R.drawable.user),
            contentDescription = "User image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(12.dp)
                .size(120.dp)
                .clip(CircleShape)
                .clickable{},
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
        )
        ActionButton(
            text = "Save",
            onClick = {}
        )
    }
}

@Preview
@Composable
fun FacePreview() {
    NyattaTheme {
        Face()
    }
}