package com.example.nyatta.ui.screens.onboarding.baths

import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme

val pad = Modifier.padding(8.dp)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bath(modifier: Modifier = Modifier) {
    var baths by remember {
        mutableStateOf("1")
    }
    Column(
        modifier = modifier
            .fillMaxSize(),
    ) {
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Column {
                Text(
                    text = stringResource(R.string.bath_label_text),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .then(pad)
                )
                OutlinedTextField(
                    value = baths,
                    modifier = Modifier
                        .fillMaxWidth()
                        .then(pad),
                    onValueChange = {
                        baths = it
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        // TODO onDone - submit and proceed
                        onDone = {}
                    )
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.End)
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = MaterialTheme.shapes.small
            ) {
                Text(
                    text = stringResource(R.string.next_text),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.then(pad)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BathPreview() {
    NyattaTheme {
        Bath()
    }
}