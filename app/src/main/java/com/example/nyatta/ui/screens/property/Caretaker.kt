package com.example.nyatta.ui.screens.property

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.nyatta.NyattaViewModelProvider
import com.example.nyatta.R
import com.example.nyatta.ui.components.Description
import com.example.nyatta.ui.components.TextInput
import com.example.nyatta.ui.components.Title
import com.example.nyatta.ui.navigation.LocationGraph
import com.example.nyatta.ui.navigation.Navigation
import com.example.nyatta.ui.screens.home.TopAppBar
import com.example.nyatta.ui.components.Onboarding
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme
import kotlinx.coroutines.launch

object CaretakerDestination: Navigation {
    override val route = "property/caretaker"
    override val title = "Caretaker"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Caretaker(
    modifier: Modifier = Modifier,
    navigateUp: () -> Unit = {},
    navigateNext: (String) -> Unit = {},
    propertyViewModel: PropertyViewModel = viewModel(),
) {
    val propertyUiState by propertyViewModel.uiState.collectAsState()

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
                modifier = modifier.padding(12.dp),
                actionButtonText = stringResource(R.string.save_caretaker),
                onActionButtonClick = { navigateNext(LocationGraph.route) }
            ) {
                Title(stringResource(R.string.caretaker))
                Description(stringResource(R.string.caretaker_description))
                Column {
                    Text(
                        text = stringResource(R.string.is_caretaker),
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
                                selected = propertyUiState.isCaretaker,
                                onClick = { propertyViewModel.setIsCaretaker(true) },
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = "Yes"
                                    }
                            )
                            Text(
                                text = stringResource(R.string.yes_caretaker)
                            )
                        }
                        Spacer(modifier = Modifier.size(32.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = !propertyUiState.isCaretaker,
                                onClick = { propertyViewModel.setIsCaretaker(false) },
                                modifier = Modifier
                                    .semantics {
                                        contentDescription = "No"
                                    }
                            )
                            Text(
                                text = stringResource(R.string.no_caretaker)
                            )
                        }
                    }
                }
                // TODO Image upload
                if (!propertyUiState.isCaretaker) {
                    CaretakerDetails(
                         propertyViewModel = propertyViewModel
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CaretakerDetails(
    modifier: Modifier = Modifier,
    propertyViewModel: PropertyViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val propertyData by propertyViewModel.uiState.collectAsState()
    val scope = rememberCoroutineScope()
    val pickMedia = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) {
        if (it != null) {
            propertyViewModel.setCaretakerImage(it.toString())
        }
    }

    Column(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(
                    propertyData.caretaker.image.ifBlank { R.drawable.user }
                )
                .crossfade(true)
                .build(),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 4.dp)
                .size(120.dp)
                .clip(CircleShape)
                .clickable {
                    scope.launch {
                        pickMedia.launch(
                            PickVisualMediaRequest(
                                ActivityResultContracts.PickVisualMedia.ImageOnly
                            )
                        )
                    }
                }
        )
        Column {
            TextInput(
                placeholder = {
                    Text("First name")
                },
                value = propertyData.caretaker.firstName,
                onValueChange = { propertyViewModel.setCaretakerFirstname(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            TextInput(
                placeholder = {
                    Text("Last name")
                },
                value = propertyData.caretaker.lastName,
                onValueChange = { propertyViewModel.setCaretakerLastname(it) },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                )
            )
            TextInput(
                value = propertyData.caretaker.phone,
                onValueChange = { propertyViewModel.setCaretakerPhone(it) },
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

@Preview
@Composable
fun CaretakerPreview() {
    NyattaTheme {
        Caretaker()
    }
}