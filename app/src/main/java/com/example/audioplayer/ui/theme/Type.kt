package com.example.audioplayer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.audioplayer.R

// Set of Material typography styles to start with
val GoogleSans = FontFamily(
    Font(R.font.google_sans)   // refers to google_sans.xml
)
val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = GoogleSans,
        fontSize = 16.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = GoogleSans,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    )
)
