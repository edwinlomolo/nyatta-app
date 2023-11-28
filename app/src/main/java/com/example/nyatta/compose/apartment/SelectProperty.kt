package com.example.nyatta.compose.apartment

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.apollographql.apollo3.exception.ApolloException
import com.example.nyatta.GetUserPropertiesQuery
import com.example.nyatta.R
import com.example.nyatta.compose.components.CircularProgressLoader
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.TextInput
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.type.PropertyType
import com.example.nyatta.ui.theme.NyattaTheme
import com.example.nyatta.viewmodels.ApartmentViewModel

object SelectPropertyDestination: Navigation {
    override val route = "apartment/select_property"
    override val title = "Associate with property"
}

private sealed interface IUserProperties{
    data object Loading: IUserProperties
    data class Success(val properties: List<GetUserPropertiesQuery.GetUserProperty> = listOf()): IUserProperties
    data class ApolloError(val message: String): IUserProperties
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectProperty(
    modifier: Modifier = Modifier,
    apartmentViewModel: ApartmentViewModel = viewModel()
) {
    val apartmentUiState by apartmentViewModel.uiState.collectAsState()
    val selectedProperty = apartmentUiState.associatedToProperty

    var expanded by remember { mutableStateOf(false) }
    var propertiesState by remember { mutableStateOf<IUserProperties>(IUserProperties.Loading)}

    LaunchedEffect(Unit) {
        propertiesState = try {
            val res = apartmentViewModel.getUserProperties().dataOrThrow()
            IUserProperties.Success(res.getUserProperties)
        } catch(e: ApolloException) {
            IUserProperties.ApolloError(e.localizedMessage!!)
        }
    }

    Column(
        modifier = modifier
            .padding(12.dp)
    ) {
        Description(stringResource(R.string.associate_unit_property))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            when(val s = propertiesState) {
                IUserProperties.Loading -> CircularProgressLoader()
                is IUserProperties.Success -> {
                    val p = s.properties/*.filter { it.type.toString() == "Apartments Building" }*/
                    if (p.isNotEmpty()) {
                        Box {
                            ExposedDropdownMenuBox(
                                expanded = expanded,
                                onExpandedChange = {
                                    expanded = !expanded
                                }
                            ) {
                                TextInput(
                                    value = selectedProperty?.name ?: "",
                                    onValueChange = { /*TODO*/ },
                                    readOnly = true,
                                    trailingIcon = {
                                        ExposedDropdownMenuDefaults.TrailingIcon(expanded)
                                    },
                                    modifier = Modifier
                                        .menuAnchor()
                                        .padding()
                                )
                                ExposedDropdownMenu(
                                    expanded = expanded,
                                    onDismissRequest = {
                                        expanded = false
                                    },
                                    modifier = Modifier
                                        .background(MaterialTheme.colorScheme.background)
                                ) {
                                    p.forEach {
                                        DropdownMenuItem(
                                            text = { Text(it.name) },
                                            onClick = {
                                                apartmentViewModel.setAssociatedTo(it)
                                                expanded = false
                                            },
                                            contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                        )
                                    }
                                }
                            }
                        }
                    } else {
                        Text(
                            text = stringResource(R.string.create_property_for_unit),
                            style = MaterialTheme.typography.labelSmall,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
                is IUserProperties.ApolloError -> {
                    Text(
                        text = s.message,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.error
                    )

                }
            }
        }
    }
}

@Preview
@Composable
fun SelectPropertyPreview() {
    NyattaTheme {
        SelectProperty()
    }
}