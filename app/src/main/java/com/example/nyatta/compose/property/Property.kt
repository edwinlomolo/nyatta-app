package com.example.nyatta.compose.property

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nyatta.compose.navigation.Navigation
import com.example.nyatta.ui.theme.NyattaTheme

object PropertyDetailsDestination: Navigation {
    override val route = "property/details"
    override val title = null
    const val propertyIdArg = "propertyIdArg"
    val routeWithArgs = "${route}/{$propertyIdArg}"
}

@Composable
fun Property(
    modifier: Modifier = Modifier,
    propertyId: String = ""
) {}

@Preview
@Composable
fun PropertyPreview() {
    NyattaTheme {
        Property()
    }
}