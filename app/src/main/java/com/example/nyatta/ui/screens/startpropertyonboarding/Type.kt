package com.example.nyatta.ui.screens.startpropertyonboarding

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.NyattaViewModelProvider
import com.example.nyatta.R
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.ApartmentOnboarding
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.navigation.PropertyOnboarding
import com.example.nyatta.ui.screens.OnboardingViewModel
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

object StartOnboardingDestination: Navigation {
    override val route = "add"
    override val title = null
}


val optionImages = listOf(R.drawable.apartments, R.drawable.apartment, R.drawable.bungalow)
val typeDefinition = listOf(
    "Large building divided into separate residential apartments on different floors",
    "A private self-contained unit in an apartments building",
    "House or home that is typically either a single story, or one and a half stories tall"
)
@Composable
fun Type(
    modifier: Modifier = Modifier,
    onboardingViewModel: OnboardingViewModel = viewModel(),
    navigateToNext: (String) -> Unit = {}
) {
    val onboardingUiState by onboardingViewModel.uiState.collectAsState()
    val propertyOptions = onboardingViewModel.propertyOptions

    Scaffold {
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(it)
        ) {
            Onboarding(
                modifier = modifier
                    .selectableGroup(),
                actionButtonText = "Start",
                onActionButtonClick = {
                    when (onboardingUiState) {
                        "Apartments Building" -> navigateToNext(
                            PropertyOnboarding.route
                        )

                        else -> navigateToNext(ApartmentOnboarding.route)
                    }
                }
            ) {
                Title(stringResource(R.string.start_your_setup))
                Description(stringResource(R.string.what_to_add))
                propertyOptions.forEachIndexed { index, option ->
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .selectable(
                                selected = (option == onboardingUiState),
                                onClick = {
                                    onboardingViewModel.setType(option)
                                },
                                role = Role.RadioButton
                            )
                    ) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = if (option == onboardingUiState)
                                    MaterialTheme.colorScheme.surface
                                else Color.Transparent
                            ),
                            border = BorderStroke(
                                1.dp,
                                color = if (option == onboardingUiState)
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
                                    selected = (option == onboardingUiState),
                                    onClick = { onboardingViewModel.setType(option) }
                                )
                                Spacer(modifier = Modifier.size(28.dp))
                                Image(
                                    painterResource(optionImages[index]),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(60.dp)
                                )
                                Column {
                                    Text(
                                        text = option,
                                        modifier = Modifier
                                            .padding(start = 12.dp),
                                        style = TextStyle(
                                            fontFamily = MabryFont,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                    )
                                    Text(
                                        modifier = Modifier
                                            .padding(start = 12.dp, top = 4.dp),
                                        text = typeDefinition[index],
                                        style = TextStyle(
                                            fontFamily = MabryFont,
                                            fontSize = 14.sp
                                        )
                                    )
                                }
                            }
                        }
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