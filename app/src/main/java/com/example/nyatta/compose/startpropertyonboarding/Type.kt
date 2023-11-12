package com.example.nyatta.compose.startpropertyonboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.R
import com.example.nyatta.compose.components.Description
import com.example.nyatta.compose.components.Title
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.viewmodels.OnboardingViewModel
import com.example.nyatta.ui.theme.NyattaTheme

object StartOnboardingDestination: Navigation {
    override val route = "add"
    override val title = null
}


val optionImages = listOf(R.drawable.apartments, R.drawable.apartment, R.drawable.bungalow)
val typeDefinition = listOf(
    R.string.apartments_building_definition,
    R.string.apartment_definition,
    //"House or home that is typically either a single story, or one and a half stories tall"
)
@Composable
fun Type(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = viewModel()
) {
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val propertyOptions = onboardingViewModel.propertyOptions

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Description(stringResource(R.string.what_to_add))
        propertyOptions.forEachIndexed { index, option ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .wrapContentHeight()
                    .selectable(
                        selected = (option == onboardingUiState.type),
                        onClick = {
                            onboardingViewModel.setType(option)
                        },
                        role = Role.RadioButton
                    ),
                colors = CardDefaults.cardColors(
                    containerColor = if (option == onboardingUiState.type)
                        MaterialTheme.colorScheme.surface
                    else Color.Transparent
                ),
                border = BorderStroke(
                    1.dp,
                    color = if (option == onboardingUiState.type)
                        MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurfaceVariant
                ),
                shape = RoundedCornerShape(0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (option == onboardingUiState.type),
                        onClick = { onboardingViewModel.setType(option) }
                    )
                    Image(
                        painterResource(optionImages[index]),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(48.dp)
                    )
                    Spacer(modifier = Modifier.size(16.dp))
                    Column {
                        Text(
                            text = option,
                            modifier = Modifier
                                .padding(start = 8.dp),
                            style = MaterialTheme.typography.titleSmall
                        )
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 4.dp),
                            text = stringResource(typeDefinition[index]),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TypePreview() {
    NyattaTheme {
        Type()
    }
}