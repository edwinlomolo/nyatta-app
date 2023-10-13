package com.example.nyatta.ui.screens.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.MabryFont


@Composable
fun Listing(
    modifier: Modifier = Modifier,
    onOpenModalBottomSheet: () -> Unit
) {
    val image =
        painterResource(
            R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c
        )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onOpenModalBottomSheet()
            }
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "Apartment",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(260.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(16.dp))
                .fillMaxWidth()
        )
        Column(modifier = Modifier.padding(8.dp)) {
            Row {
                Text(
                    text = "2 bedroom",
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "KES 10,000",
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }
            Text(
                text = "3 KM away",
                style = TextStyle(
                    fontFamily = MabryFont,
                    fontSize = 16.sp,
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
            Text(
                text = "2 days ago",
                style = TextStyle(fontFamily = MabryFont, fontSize = 16.sp),
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
    }
}