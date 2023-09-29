package com.example.nyatta.ui.screens.onboarding.caretaker

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Caretaker(
    modifier: Modifier = Modifier
) {
    var firstName by remember {
        mutableStateOf("")
    }
    var lastName by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    val countryCode by remember {
        mutableStateOf("+254")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.weight(.5f)
        ) {
            Text(
                text = stringResource(R.string.caretaker_description),
                style = MaterialTheme.typography.titleMedium,
            )
        }
        // TODO Image upload
        Row {
            IconButton(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(8.dp)
                    .size(100.dp)
            ) {
                Icon(
                    Icons.Filled.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(100.dp),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        Row {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text(text = stringResource(R.string.caretaker_firstname)) }
            )
        }
        Row {
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text(text = stringResource(R.string.caretaker_lastname)) }
            )
        }
        Row(modifier = Modifier.weight(1f)) {
            OutlinedTextField(
                value = countryCode,
                onValueChange = {},
                modifier = Modifier.weight(.5f),
                label = {},
                enabled = false
            )
            Spacer(modifier = Modifier.size(32.dp))
            OutlinedTextField(
                modifier = Modifier.weight(1f),
                value = phone,
                onValueChange = { phone = it },
                label = { Text(text = stringResource(R.string.caretaker_phone)) }
            )
        }
        Row(
            modifier = Modifier
                .align(Alignment.End)
                .padding(8.dp)

        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = "Next",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CaretakerPreview() {
    NyattaTheme {
        Caretaker()
    }
}