package com.example.nyatta.ui.screens.listing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Listing(modifier: Modifier = Modifier) {
    var openBottomSheet by rememberSaveable {
        mutableStateOf(false)
    }
    val scope = rememberCoroutineScope()
    val bottomSheetState = rememberModalBottomSheetState()
    val image = painterResource(
        R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c
    )
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ListingTopBar(showListingReview = { openBottomSheet = !openBottomSheet }, scrollBehavior = scrollBehavior)
        },
        bottomBar = {
            ListingBottomAppBar()
        }
    ) {
        Surface(
            modifier = Modifier
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Image(
                    painter = image,
                    contentScale = ContentScale.Crop,
                    contentDescription = "Apartment",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp)
                )
                Box(modifier = Modifier.padding(top = 8.dp, start = 8.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Unit name",
                            style = TextStyle(fontFamily = MabryFont, fontSize = 32.sp, fontWeight = FontWeight.Bold)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = "KES 10, 000 Monthly",
                            style = TextStyle(fontFamily = MabryFont, fontSize = 16.sp),
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }
                Box(modifier = Modifier.padding(8.dp)) {
                    Text(
                        text = "Luxurious two bedroom flat with beautifully designed boasting an exposed minimal interior, set on the lower ground floor.",
                        style = TextStyle(fontFamily = MabryFont, fontSize = 18.sp)
                    )
                }
                Box(modifier = Modifier.padding(top = 16.dp, start = 8.dp)) {
                    Column {
                        Text(
                            text = "Caretaker",
                            style = TextStyle(fontFamily = MabryFont, fontWeight = FontWeight.Bold, fontSize = 32.sp),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = image,
                                contentDescription = "Caretaker",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .size(64.dp)
                                    .clip(CircleShape)
                            )
                            Spacer(modifier = Modifier.size(16.dp))
                            Column {
                                Row {
                                    Text(
                                        text = "Caretaker Name",
                                        modifier = Modifier.padding(bottom = 4.dp),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                    Spacer(modifier = Modifier.size(4.dp))
                                    Icon(
                                        painterResource(R.drawable.verify),
                                        modifier = Modifier.size(28.dp),
                                        contentDescription = "Verify",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }
                                // TODO review caretaker
                                /*
                                Text(
                                    text = "Review caretaker",
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier
                                        .clickable(
                                            onClick = {}
                                        )
                                )
                                 */
                            }
                        }
                    }
                }
                Box(modifier = Modifier.padding(top = 32.dp, start = 8.dp)) {
                    Column {
                        Text(
                            text = "Amenities(35)",
                            style = TextStyle(fontFamily = MabryFont, fontSize = 32.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )
                            repeat(15) {
                                Text(
                                    text = "Some amenity type",
                                )
                            }
                    }
                }
                if (openBottomSheet) {
                    ListingReviewSheet(
                        onDismiss = { openBottomSheet = false },
                        sheetState = bottomSheetState,
                        onClose = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    openBottomSheet = false
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingTopBar(showListingReview: () -> Unit, scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier) {
    MediumTopAppBar(
        modifier = modifier,
        title = { Text(text = "Lavington Height Apartment", maxLines = 1, overflow = TextOverflow.Ellipsis) },
        actions = {
            IconButton(onClick = showListingReview) {
                Icon(
                    painterResource(R.drawable.feedback),
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Feedback"
                )
            }
            Spacer(modifier = Modifier.size(16.dp))
            IconButton(onClick = { /*TODO*/ }) {
                Icon(
                    Icons.Outlined.FavoriteBorder,
                    modifier = Modifier.size(32.dp),
                    contentDescription = "Save"
                )
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun ListingBottomAppBar(modifier: Modifier = Modifier) {
    BottomAppBar(modifier = modifier) {
        Button(
            onClick = { /*TODO*/ },
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Call now",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun ListingReviewSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onClose: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        modifier = modifier,
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    Icons.Default.Close,
                    contentDescription = "Close icon",
                    modifier = Modifier
                        .size(28.dp)
                        .shadow(elevation = 16.dp, shape = MaterialTheme.shapes.small),
                    tint = MaterialTheme.colorScheme.surfaceTint
                )
            }
            Text(
                text = "Reviews",
                style = TextStyle(fontFamily = MabryFont, fontSize = 24.sp),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Box(modifier = Modifier.padding(8.dp)) {
            Row {
                var text by remember { mutableStateOf("") }
                Image(
                    painter = painterResource(R.drawable.rottweiler),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.weight(1f))
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }
        LazyColumn {
            items(10) {
                ListItem(
                    headlineContent = { Text("Item $it") },
                    leadingContent = {
                        Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Localized description"
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ListingPreview() {
    NyattaTheme {
        Listing()
    }
}