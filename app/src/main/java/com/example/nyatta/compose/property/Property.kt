package com.example.nyatta.compose.property

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Call
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.R
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme

object PropertyDetailsDestination: Navigation {
    override val route = "property/details"
    override val title = null
    const val propertyIdArg = "propertyIdArg"
    val routeWithArgs = "${route}/{$propertyIdArg}"
}

@Composable
fun Property(
    modifier: Modifier = Modifier,
    propertyId: String = ""
) {
    PropertyHeader()
}

@Composable
fun PropertyHeader(
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
                contentDescription = "thumbnail"
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
            text = "Join date",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .padding(top = 24.dp)
        )

        PropertyMenu()
    }
}

data class TabTitle(
    val title: String = "",
    val enabled: Boolean = false
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PropertyMenu(
    modifier: Modifier = Modifier
) {
    var state by remember { mutableIntStateOf(0) }
    val titles = listOf(TabTitle("Units",true), TabTitle("Tenants", false))

    Column {
        PrimaryTabRow(
            modifier = Modifier.fillMaxWidth(),
            selectedTabIndex = state
        ) {
            titles.forEachIndexed { index, tab ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    enabled = tab.enabled,
                    text = {
                        Text(
                            text = tab.title,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }
        Tenants()
    }
}

@Composable
fun Tenants(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(5) {
            Tenant()
        }
    }
}

@Composable
fun Tenant(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
       Box(
           Modifier
               .padding(12.dp)
               .align(Alignment.CenterVertically)
               .size(100.dp)
               .height(100.dp)
               .width(100.dp)
               .background(
                   color = MaterialTheme.colorScheme.surfaceVariant,
                   shape = MaterialTheme.shapes.small
               )
       ) {
           Text(
               text = "T",
               style = MaterialTheme.typography.titleLarge,
               modifier = Modifier.align(Alignment.Center)
           )
       }
        Column {
            Text(
                text = "John Doe",
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "Unit name",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            IconButton(onClick = { /*TODO*/ }) {
               Icon(
                   Icons.TwoTone.Call,
                   contentDescription = "call",
                   modifier = Modifier
                       .align(Alignment.Start)
                       .size(24.dp)
               )
            }
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