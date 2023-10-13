package com.example.nyatta.ui.screens.listing

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingView(
    onClose: () -> Unit,
    bottomSheetState: SheetState,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = bottomSheetState,
        modifier = modifier
    ) {
        Row(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {
            Text("Listing Info")
        }
    }
}