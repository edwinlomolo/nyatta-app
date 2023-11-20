package com.example.nyatta.compose.user

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.components.ActionButton
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AuthViewModel

object AccountDestination: Navigation {
    override val route = "account"
    override val title = null
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Account(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val authUiState by authViewModel.authUiState.collectAsState()
    val userUiState by authViewModel.userUiDetails.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp),
        ) {
            Box {
                // TODO avatar upload
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(authUiState.user.avatar)
                        .crossfade(true)
                        .build(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 32.dp)
                        .clip(MaterialTheme.shapes.small)
                        .size(120.dp),
                    contentDescription = "user_avatar"
                )
            }
            Column {
                TextInput(
                    isError = if (userUiState.firstName.isNotEmpty()) !userUiState.validDetails.firstName else false,
                    placeholder = {
                        Text(stringResource(R.string.first_name))
                    },
                    onValueChange = {
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextInput(
                    isError = if (userUiState.lastName.isNotEmpty()) !userUiState.validDetails.lastName else false,
                    placeholder = {
                        Text(stringResource(R.string.last_name))
                    },
                    onValueChange = {
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
                TextInput(
                    enabled = false,
                    value = authUiState.user.phone,
                    readOnly = true,
                    onValueChange = {}
                )
                // TODO show only if user data changed
                ActionButton(
                    text = stringResource(id = R.string.save)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Column {
                Text(
                    text = stringResource(R.string.properties),
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                LazyRow {
                    // TODO if user has listed properties
                    if (true) {
                        item {
                            Text(
                                text = "No properties",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        items(5) {
                            PropertyCard()
                        }
                    }
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        ) {
            Column {
                Text(
                    text = "Owned Units",
                    modifier = Modifier
                        .padding(bottom = 8.dp),
                    style = MaterialTheme.typography.titleMedium
                )
                LazyRow {
                    // TODO if user has owned units(condo)
                    if (true) {
                        item {
                            Text(
                                text = "No owned units",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    } else {
                        items(5) {
                            PropertyCard()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier
) {
    OutlinedCard(
        modifier = modifier
            .size(width = 240.dp, height = 160.dp)
            .padding(4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentDescription = stringResource(R.string.thumbnail)
            )
            Text(
                text = "Beach front properties",
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(8.dp),
                style = MaterialTheme.typography.titleSmall
            )
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