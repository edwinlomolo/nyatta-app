package com.example.nyatta.ui.screens.listing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.nyatta.ui.screens.home.HomeViewModel
import com.example.nyatta.ui.theme.MabryFont


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListingView(
    modifier: Modifier = Modifier,
    onClose: () -> Unit,
    bottomSheetState: SheetState
) {
    val homeViewModel: HomeViewModel = viewModel(factory = HomeViewModel.Factory)
    val tabState = homeViewModel.tabState
    val tabTitles = homeViewModel.titles

    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = bottomSheetState,
        modifier = modifier
    ) {
        Column(modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()) {
            TabRow(selectedTabIndex = tabState) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        text = {
                            Text(
                                text = title,
                                style = TextStyle(
                                    fontFamily = MabryFont,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        },
                        onClick = { homeViewModel.updateTabState(index) },
                        selected = (index == tabState)
                    )
                }
            }
            when(tabTitles[tabState]) {
                "Photos" -> ListingPhoto()
                "Amenities" -> ListingAmenity()
                "Reviews" -> ListingReview()
            }
        }
    }
}