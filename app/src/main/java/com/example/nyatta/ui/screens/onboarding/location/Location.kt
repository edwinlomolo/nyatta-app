package com.example.nyatta.ui.screens.onboarding.location

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.ui.theme.NyattaTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Location(modifier: Modifier = Modifier) {
    var location by remember { mutableStateOf("") }

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Where is your property located",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            OutlinedTextField(
                value = location,
                modifier = Modifier.fillMaxWidth(),
                onValueChange = { location = it },
                label = { Text(text = "Location") },
                keyboardActions = KeyboardActions(
                    onDone = {}
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Next
                )
            )
        }
        Spacer(modifier = Modifier.weight(1f))
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
                    style = MaterialTheme.typography.titleMedium,
                    text = "Next",
                    modifier = Modifier.padding(8.dp)
                )
                Icon(painter = Icons.Filled.ArrowForward, contentDescription = "Next step")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LocationPreview() {
    NyattaTheme {
        Location()
    }
}