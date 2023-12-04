package com.example.nyatta.compose.user

import android.graphics.Paint.Align
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetUserQuery
import com.example.nyatta.R
import com.example.nyatta.compose.components.ErrorContainer
import com.example.nyatta.compose.components.Loading
import com.example.nyatta.compose.listing.Tag
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.compose.property.PropertyDetailsDestination
import com.example.nyatta.data.model.User
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
    authViewModel: AuthViewModel = viewModel(),
    isLandlord: Boolean = false,
    onNavigateToProperty: (String) -> Unit = {}
) {
    var state by remember { mutableStateOf<GetUserDetailsState>(GetUserDetailsState.Loading) }

    LaunchedEffect(Unit) {
        state = try {
            val response = authViewModel.getUser()
            authViewModel.updateFirstname(response.getUser.first_name ?: "")
            authViewModel.updateLastname(response.getUser.last_name ?: "")
            authViewModel.updateAvatar(response.getUser.avatar?.upload ?: User().avatar)
            authViewModel.updateUserPhone(response.getUser.phone)
            authViewModel.updateUserId(response.getUser.id)
            GetUserDetailsState.Success(
                user = User(
                    id = response.getUser.id,
                    phone = response.getUser.phone,
                    firstName = response.getUser.first_name ?: "",
                    lastName = response.getUser.last_name ?: "",
                    avatar = response.getUser.avatar?.upload ?: User().avatar,
                    properties = response.getUser.properties,
                    units = response.getUser.units),
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
            AccountCard(
                modifier = modifier.padding(12.dp),
                properties = s.user?.properties ?: listOf(),
                units = s.user?.units ?: listOf(),
                onNavigateToProperty = onNavigateToProperty
            )
        }
    }


}

@Composable
private fun AccountCard(
    modifier: Modifier = Modifier,
    properties: List<GetUserQuery.Property>,
    units: List<GetUserQuery.Unit>,
    onNavigateToProperty: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        UserListings(
            properties = properties,
            units = units,
            onNavigateToProperty = onNavigateToProperty
        )
    }
}

@Composable
fun UserListings(
    properties: List<GetUserQuery.Property>,
    units: List<GetUserQuery.Unit>,
    onNavigateToProperty: (String) -> Unit
) {
   Properties(onNavigateToProperty = onNavigateToProperty, properties = properties)
   PrivateUnits(units = units)

}

@Composable
fun Properties(
    modifier: Modifier = Modifier,
    properties: List<GetUserQuery.Property>,
    onNavigateToProperty: (String) -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Column {
            Text(
                text = stringResource(R.string.properties),
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (properties.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.you_have_no_properties),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                } else {
                    items(items = properties, key = { it.id }) {
                        PropertyCard(
                            modifier = modifier
                                .clickable {
                                    onNavigateToProperty(it.id.toString())
                                },
                            property = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PrivateUnits(
    modifier: Modifier = Modifier,
    units: List<GetUserQuery.Unit>
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
    ) {
        Column {
            Text(
                text = stringResource(R.string.your_private_units),
                modifier = Modifier
                    .padding(bottom = 8.dp),
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (units.isEmpty()) {
                    item {
                        Column(
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.you_have_no_private_units),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                } else {
                    items(items = units, key = { it.id }) {
                        UnitCard(unit = it)
                    }
                }
            }
        }
    }
}

@Composable
fun PropertyCard(
    modifier: Modifier = Modifier,
    property: GetUserQuery.Property,
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
                    .data(property.thumbnail?.upload)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentDescription = stringResource(R.string.thumbnail)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = property.name,
                        modifier = Modifier
                            .padding(4.dp, top = 8.dp),
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${property.unitsCount} units",
                        modifier = Modifier
                            .padding(4.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}

@Composable
fun UnitCard(
    modifier: Modifier = Modifier,
    unit: GetUserQuery.Unit
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
                    .data(unit.thumbnail?.upload)
                    .crossfade(true)
                    .build(),
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(width = 240.dp, height = 100.dp)
                    .clip(MaterialTheme.shapes.small),
                contentDescription = stringResource(R.string.thumbnail)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .align(Alignment.BottomStart),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = unit.name,
                    modifier = Modifier.padding(end = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Tag(
                    text = unit.type
                )
            }
        }
    }
}