package com.example.nyatta.compose.user

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.R
import com.example.nyatta.compose.components.ActionButton
import com.example.nyatta.compose.components.ErrorContainer
import com.example.nyatta.compose.components.Loading
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.data.model.User
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.AuthViewModel

object AccountDestination: Navigation {
    override val route = "account"
    override val title = null
}

private sealed interface GetUserDetailsState {
    data object Loading: GetUserDetailsState
    data class ApolloError(val message: String? = null): GetUserDetailsState
    data class Success(val user: User? = null): GetUserDetailsState
}

@Composable
fun Account(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel()
) {
    var state by remember { mutableStateOf<GetUserDetailsState>(GetUserDetailsState.Loading) }

    LaunchedEffect(Unit) {
        state = try {
            val response = authViewModel.getUser()
            GetUserDetailsState.Success(
                user = User(
                    firstName = response.getUser.first_name,
                    lastName = response.getUser.last_name,
                    avatar = response.getUser.avatar?.upload ?: User().avatar)
            )
        } catch(e: ApolloException) {
            GetUserDetailsState.ApolloError(e.localizedMessage)
        }
    }

    when(val s = state) {
        GetUserDetailsState.Loading -> {
            Loading()
        }
        is GetUserDetailsState.ApolloError -> {
            ErrorContainer(message = s.message!!)
        }
        is GetUserDetailsState.Success -> {
            authViewModel.updateFirstname(s.user!!.firstName)
            authViewModel.updateLastname(s.user.lastName)
            authViewModel.updateAvatar(s.user.avatar)
            FaceCard(
                modifier = modifier,
                authViewModel = authViewModel
            )
        }
    }


}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun FaceCard(
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel = viewModel(),
) {
    val keyboardController = LocalSoftwareKeyboardController.current

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
                        .data(authViewModel.avatar)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.loading_img),
                    error = painterResource(R.drawable.ic_broken_image),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .padding(start = 8.dp, end = 32.dp)
                        .clip(MaterialTheme.shapes.small)
                        .size(100.dp)
                        .clickable {},
                    contentDescription = "user_avatar"
                )
            }
            Column {
                TextInput(
                    value = authViewModel.firstName,
                    isError = authViewModel.firstName.isEmpty(),
                    placeholder = {
                        Text(stringResource(R.string.first_name))
                    },
                    onValueChange = {
                        authViewModel.updateFirstname(it)
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextInput(
                    value = authViewModel.lastName,
                    isError = authViewModel.lastName.isEmpty(),
                    placeholder = {
                        Text(stringResource(R.string.last_name))
                    },
                    onValueChange = {
                        authViewModel.updateLastname(it)
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