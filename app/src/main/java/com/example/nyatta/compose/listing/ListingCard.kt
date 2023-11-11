package com.example.nyatta.compose.listing

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.ui.theme.MabryFont


@Composable
fun ListingCard(
    modifier: Modifier = Modifier,
) {
    val image = R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Box {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.listing_image),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Row {
                    Text(
                        text = "2 bedroom",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = "KES 10,000",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Text(
                    text = "3 KM away",
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontStyle = FontStyle.Italic
                    ),
                    modifier = Modifier.padding(start = 8.dp, bottom = 2.dp)
                )
                Text(
                    text = "2 days ago",
                    style = TextStyle(fontFamily = MabryFont),
                    modifier = Modifier.padding(start = 8.dp, bottom = 8.dp)
                )
            }
        }

    }
}