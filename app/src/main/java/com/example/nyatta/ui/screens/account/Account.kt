package com.example.nyatta.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
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
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun Account(
    modifier: Modifier = Modifier
) {
    val extraPadding = Modifier
        .padding(top = 12.dp, bottom = 12.dp)
    val image = painterResource(
        R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.then(extraPadding)
        ) {
            Text(
                text = stringResource(R.string.account_info),
                style = TextStyle(
                    fontFamily = MabryFont,
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        Section(
            modifier = Modifier
                .then(extraPadding)
        ) {
            Image(
                image,
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                ,
                contentScale = ContentScale.Crop
            )
        }
        Section(
            title = stringResource(R.string.personal_details),
        ) {
            Section(
                title = stringResource(R.string.name)
            ) {
                Row {
                    Text(
                        text = "Name",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontWeight = FontWeight.ExtraLight,
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp)
                    )
                }
            }
            Section(
                title = stringResource(R.string.phone)
            ) {
                Row {
                    Text(
                        text = "Phone",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight
                        ),
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp)
                    )
                }
            }
            // TODO Disable email field from being editable
            Section(
                title = stringResource(R.string.email)
            ) {
                Row {
                    Text(
                        text = "Email",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraLight
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        Icons.Outlined.KeyboardArrowRight,
                        contentDescription = null,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .size(32.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun Section(
    modifier: Modifier = Modifier,
    title: String? = null,
    content: @Composable () -> Unit?
) {
    Row(
        modifier = modifier
            .padding(top = 28.dp, bottom = 28.dp)
    ) {
        Column {
            if (title != null) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontSize = 38.sp,
                    )
                )
            }
            content()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AccountPreview() {
    NyattaTheme {
        Account()
    }
}