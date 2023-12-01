package com.example.nyatta.compose.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.AccountCircle
import androidx.compose.material.icons.twotone.AddCircle
import androidx.compose.material.icons.twotone.LocationOn
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.nyatta.R
import com.example.nyatta.compose.home.HomeDestination
import com.example.nyatta.compose.startpropertyonboarding.StartOnboardingDestination
import com.example.nyatta.compose.user.AccountDestination

sealed class Screen(
    val route: String,
    @StringRes val nameResourceId: Int,
    val icon: ImageVector,
    val modifier: Modifier
) {
    data object Home: Screen(
        HomeDestination.route,
        R.string.explore,
        Icons.TwoTone.LocationOn,
        Modifier
    )
    data object Add: Screen(
        StartOnboardingDestination.route,
        R.string.add,
        Icons.TwoTone.AddCircle,
        modifier = Modifier.size(36.dp)
    )
    data object Account: Screen(
        AccountDestination.route,
        R.string.account,
        Icons.TwoTone.AccountCircle,
        Modifier
    )
}

val navigationItems = listOf(
    Screen.Home,
    Screen.Add,
    Screen.Account
)