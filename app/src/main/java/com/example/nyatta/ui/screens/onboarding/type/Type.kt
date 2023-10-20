package com.example.nyatta.ui.screens.onboarding.type

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nyatta.R
import com.example.nyatta.ui.screens.onboarding.Onboarding
import com.example.nyatta.ui.theme.MabryFont
import com.example.nyatta.ui.theme.NyattaTheme

val propertyOptions = listOf("Apartments Building", "Apartment", "Bungalow")
val optionImages = listOf(R.drawable.apartments, R.drawable.apartment, R.drawable.bungalow)
val typeDefinition = listOf(
    "Large building divided into separate residential apartments on different floors",
    "A private self-contained unit in an apartments building",
    "House or home that is typically either a single story, or one and a half stories tall"
)
@Composable
fun Type(
    modifier: Modifier = Modifier
) {
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(propertyOptions[0]) }
    Onboarding(
        modifier = modifier
            .fillMaxSize()
            .selectableGroup(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let's start with how you will organize your listings",
            style = TextStyle(
                fontFamily = MabryFont,
                fontSize = 36.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp, top = 18.dp)
        )
        Text(
            text = "This will make it easier for you to group/add your listings independently or under one building.",
            style = TextStyle(
                fontFamily = MabryFont,
                fontSize = 18.sp
            ),
            modifier = Modifier
                .padding(start = 16.dp)
        )
        propertyOptions.forEachIndexed { index, option ->
            Row(
                modifier = Modifier
                    .padding(18.dp)
                    .fillMaxWidth()
                    .selectable(
                        selected = (option == selectedOption),
                        onClick = { onOptionSelected(option) },
                        role = Role.RadioButton
                    )
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = if (option == selectedOption)
                            MaterialTheme.colorScheme.surface
                        else Color.Transparent
                    ),
                    border = BorderStroke(
                        1.dp,
                        color = if (option == selectedOption)
                            MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurfaceVariant
                    ),
                    shape = RoundedCornerShape(0.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (option == selectedOption),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.size(28.dp))
                        Image(
                            painterResource(optionImages[index]),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(60.dp)
                        )
                        Column {
                            Text(
                                text = option,
                                modifier = Modifier
                                    .padding(start = 12.dp),
                                style = TextStyle(
                                    fontFamily = MabryFont,
                                    fontSize = 18.sp
                                )
                            )
                            Text(
                                modifier = Modifier
                                    .padding(start = 12.dp, top = 4.dp),
                                text = typeDefinition[index],
                                style = TextStyle(
                                    fontFamily = MabryFont,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TypePreview() {
    NyattaTheme {
        Type()
    }
}