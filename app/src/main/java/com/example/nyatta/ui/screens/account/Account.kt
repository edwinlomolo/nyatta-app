package com.example.nyatta.ui.screens.account

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
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
import com.example.nyatta.ui.screens.home.BottomBar
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

object AccountDestination: Navigation {
    override val route = "account"
    override val title = null
}

@Composable
fun Account(
    modifier: Modifier = Modifier,
    onNavigateTo: (route: String) -> Unit = {},
    currentRoute: String? = null
) {
    val extraPadding = Modifier
        .padding(top = 12.dp, bottom = 12.dp)
    val image = painterResource(
        R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c
    )

    Scaffold(
        bottomBar = {
            BottomBar(
                onNavigateTo = onNavigateTo,
                currentRoute = currentRoute
            )
        }
    ) {
        Surface(
            modifier = modifier
                .verticalScroll(rememberScrollState())
                .padding(it)
        ) {
            Column(
                modifier = Modifier
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
                            fontSize = 36.sp,
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
                                    fontSize = 16.sp
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
                                    fontSize = 16.sp,
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
                                    fontSize = 16.sp,
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
            .padding(top = 18.dp, bottom = 18.dp)
    ) {
        Column {
            if (title != null) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            content()
        }
    }
}

@Preview
@Composable
fun AccountPreview() {
    NyattaTheme {
        Account()
    }
}