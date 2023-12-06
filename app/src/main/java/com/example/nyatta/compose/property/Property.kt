package com.example.nyatta.compose.property

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetPropertyQuery
import com.example.nyatta.R
import com.example.nyatta.compose.components.Loading
import com.example.nyatta.compose.components.NetworkError
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.PropertyViewModel

object PropertyDetailsDestination: Navigation {
    override val route = "user/property/details"
    override val title = null
    const val propertyIdArg = "propertyIdArg"
    val routeWithArgs = "${route}/{$propertyIdArg}"
}

private sealed interface IGetPropertyDetails {
    data object Loading: IGetPropertyDetails
    data class ApolloError(val message: String?): IGetPropertyDetails
    data class Success(val success: GetPropertyQuery.GetProperty): IGetPropertyDetails
}

@Composable
fun Property(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel = viewModel(),
    propertyId: String = ""
) {
    var state by remember { mutableStateOf<IGetPropertyDetails>(IGetPropertyDetails.Loading) }

    LaunchedEffect(Unit) {
        state = try {
            val res = propertyViewModel.getProperty(propertyId)
            IGetPropertyDetails.Success(res.getProperty)
        } catch(e: ApolloException) {
            IGetPropertyDetails.ApolloError(e.localizedMessage)
        }
    }

    when(val s = state) {
        IGetPropertyDetails.Loading -> Loading()
        is IGetPropertyDetails.ApolloError -> {
            NetworkError(errorString = s.message!!)
        }
        is IGetPropertyDetails.Success -> {
            PropertyHeader(
                property = s.success
            )
        }
    }
}

@Composable
private fun PropertyHeader(
    modifier: Modifier = Modifier,
    property: GetPropertyQuery.GetProperty
) {
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize()
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(property.thumbnail?.upload)
                    .crossfade(true)
                    .build(),
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.loading_img),
                error = painterResource(id = R.drawable.ic_broken_image),
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .align(Alignment.CenterVertically)
                    .width(100.dp)
                    .height(100.dp),
                contentDescription = stringResource(id = R.string.thumbnail)
            )
            Column(
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(
                    text = property.name,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "${property.caretaker?.first_name} ${property.caretaker?.last_name}",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        PropertyMenu(
            units = property.units
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PropertyMenu(
    modifier: Modifier = Modifier,
    units: List<GetPropertyQuery.Unit>
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(
        "Units"/*,
        "Tenants"*/
    )

    Column {
        PrimaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = state
        ) {
            titles.forEachIndexed { index, tab ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    unselectedContentColor = MaterialTheme.colorScheme.surfaceVariant,
                    text = {
                        Text(
                            text = tab,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
        when (state) {
            0 -> Units(units = units)
            1 -> {}
        }
    }
}

@Composable
private fun Units(
    modifier: Modifier = Modifier,
    units: List<GetPropertyQuery.Unit>
) {
    LazyColumn {
        items(units) {
            Unit(unit = it)
        }
    }
}

@Composable
private fun Unit(
    modifier: Modifier = Modifier,
    unit: GetPropertyQuery.Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(unit.thumbnail?.upload)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.ic_broken_image),
            modifier = Modifier
                .padding(top = 12.dp, end = 12.dp, bottom = 12.dp)
                .clip(MaterialTheme.shapes.small)
                .align(Alignment.CenterVertically)
                .width(100.dp)
                .height(100.dp),
            contentDescription = stringResource(id = R.string.thumbnail)
        )
        Column {
            Text(
                text = unit.name,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = unit.state.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

@Preview
@Composable
fun PropertyPreview() {
    NyattaTheme {
        Property()
    }
}