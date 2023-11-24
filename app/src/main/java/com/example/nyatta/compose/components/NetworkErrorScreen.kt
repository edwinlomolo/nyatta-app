package com.example.nyatta.compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.ui.theme.NyattaTheme

@Composable
fun NetworkError(
    modifier: Modifier = Modifier,
    errorString: String = ""
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_connection_error),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .size(40.dp),
            contentScale = ContentScale.Crop,
            contentDescription = stringResource(R.string.network_error)
        )
        Text(
            text = errorString,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun NetworkErrorPreview() {
    NyattaTheme {
        NetworkError()
    }
}