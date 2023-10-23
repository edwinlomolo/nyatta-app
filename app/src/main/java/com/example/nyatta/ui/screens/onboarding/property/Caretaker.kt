package com.example.nyatta.ui.screens.onboarding.property

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.components.onboarding.Description
import com.example.nyatta.ui.components.onboarding.TextInput
import com.example.nyatta.ui.components.onboarding.Title
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.screens.onboarding.location.LocationDestination
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

object CaretakerDestination: Navigation {
    override val route = "property/caretaker"
    override val title = "Caretaker"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Caretaker(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {}
) {
    var state by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                canNavigateBack = true,
                navigateUp = navigateUp,
                title = CaretakerDestination.title
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Onboarding(
                modifier = modifier,
                actionButtonText = stringResource(R.string.save_caretaker),
                onActionButtonClick = { navigateNext(LocationDestination.route) }
            ) {
                Title("Caretaker")
                Description("This is someone that can be reached to schedule a visit or allow access to your property.")
                Column {
                    Text(
                        text = "Are you the caretaker?",
                        style = TextStyle(
                            fontFamily = MabryFont,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(8.dp)
                    )
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .selectableGroup()
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = state,
                                onClick = { state = true },
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = "Yes radio button"
                                    }
                            )
                            Text(
                                text = stringResource(R.string.yes_caretaker)
                            )
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = !state,
                                onClick = { state = false },
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = "No radio button"
                                    }
                            )
                            Text(
                                text = stringResource(R.string.no_caretaker)
                            )
                        }
                    }
                }
                // TODO Image upload
                if (!state) {
                    CaretakerDetails()
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CaretakerDetails(
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painterResource(R.drawable.user),
                contentDescription = "User image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.Top)
                    .padding(12.dp)
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable {},
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
            Column {
                TextInput(
                    modifier = Modifier.padding(8.dp),
                    placeholder = {
                        Text("First name")
                    },
                    onValueChange = { /*TODO*/ },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextInput(
                    modifier = Modifier.padding(8.dp),
                    placeholder = {
                        Text("Last name")
                    },
                    onValueChange = { /*TODO*/ },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Next
                    )
                )
                TextInput(
                    modifier = Modifier.padding(8.dp),
                    onValueChange = { /*TODO*/ },
                    prefix = {
                        Text(
                            text = "+254",
                            color = MaterialTheme.colorScheme.primary
                        )
                    },
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        keyboardType = KeyboardType.Number
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            keyboardController?.hide()
                        }
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun CaretakerPreview() {
    NyattaTheme {
        Caretaker()
    }
}