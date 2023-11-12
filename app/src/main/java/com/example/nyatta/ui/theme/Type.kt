package com.example.nyatta.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.nyatta.R

val MabryFont = FontFamily(
    Font(R.font.mabry_regular),
    Font(R.font.mabry_bold, FontWeight.Bold),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = MabryFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = MabryFont,
        fontSize = 13.sp,
    ),
    // Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = MabryFont,
        fontWeight = FontWeight.Normal,
        fontSize = 36.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = MabryFont,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = MabryFont,
        fontWeight = FontWeight.SemiBold,
    ),
    labelSmall = TextStyle(
        fontFamily = MabryFont,
        lineHeight = 8.sp,
        fontWeight = FontWeight.SemiBold,
    )
)