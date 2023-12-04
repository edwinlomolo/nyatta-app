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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

object PropertyDetailsDestination: Navigation {
    override val route = "property/details"
    override val title = null
    const val propertyIdArg = "propertyIdArg"
    val routeWithArgs = "${route}/{$propertyIdArg}"
}

private fun getMonthYearDate(date: Date): String? {
    val formatter = SimpleDateFormat("MMMM, yyyy", Locale.getDefault())

    val c = Calendar.getInstance()
    c.time = date
    return formatter.format(c.time)
}

@Composable
fun Property(
    modifier: Modifier = Modifier,
    propertyId: String = ""
) {
    PropertyHeader()
}

@Composable
private fun PropertyHeader(
    modifier: Modifier = Modifier
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
                    .data(R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c)
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
                    text = "Property name",
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "Caretaker name",
                    style = MaterialTheme.typography.titleSmall,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
        Text(
            text = "Added ${getMonthYearDate(Calendar.getInstance().time)}",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 24.dp)
        )
        PropertyMenu()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PropertyMenu(
    modifier: Modifier = Modifier
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
            0 -> Units()
            1 -> {}
        }
    }
}

@Composable
private fun Units(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(5) {
            Unit()
        }
    }
}

@Composable
private fun Unit(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.apartment_sunset_in_the_background_in_africa_and_person_c4dadd13_9720_4c7f_ad7b_86e197bfd86c)
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
                text = "Unit name",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "state tag",
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