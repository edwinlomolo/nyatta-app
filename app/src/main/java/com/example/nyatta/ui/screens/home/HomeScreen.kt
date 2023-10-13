package com.example.nyatta.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.listing.Listing
import com.example.nyatta.ui.screens.listing.ListingView
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(modifier: Modifier = Modifier) {
    var openBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }
    val bottomSheetState = rememberModalBottomSheetState()

    Scaffold(
        topBar = {
            HomeAppBar()
        }
    ) {
        Surface(
            modifier = modifier
                .background(MaterialTheme.colorScheme.background)
                .fillMaxSize()
                .fillMaxWidth()
                .padding(it)
        ) {
            Column(modifier = modifier.verticalScroll(rememberScrollState())) {
                repeat(10) {
                    Listing(onOpenModalBottomSheet = { openBottomSheet = true })
                }
                if (openBottomSheet) ListingView(onClose = { openBottomSheet = false }, bottomSheetState = bottomSheetState)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column {
                Text(
                    text = stringResource(R.string.vacant_homes),
                    style = TextStyle(fontFamily = MabryFont, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                )
                Text(
                    text = "5 listings in your area",
                    style = TextStyle(
                        fontFamily = MabryFont,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        },
        actions = {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    painterResource(R.drawable.filter),
                    contentDescription = stringResource(R.string.filter_menu),
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    )
}


@Preview(showBackground = true)
@Composable
fun HomePreview() {
    NyattaTheme {
        Home()
    }
}