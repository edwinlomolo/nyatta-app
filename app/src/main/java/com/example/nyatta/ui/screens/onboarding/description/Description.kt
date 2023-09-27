package com.example.nyatta.ui.screens.onboarding.description

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
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
fun Description(modifier: Modifier = Modifier) {
    var description by remember {
        mutableStateOf("")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "This is what people will call your property",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Row(
            modifier = Modifier.padding(24.dp)
        ) {
            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(text = "Property name") },
                keyboardActions = KeyboardActions(
                    // TODO: onDone
                    onDone = {}
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
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
                    modifier = Modifier.padding(8.dp),
                    text = "Next",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DescriptionPreview() {
    NyattaTheme {
        Description()
    }
}