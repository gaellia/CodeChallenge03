package com.launchpad.codechallenge03.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.launchpad.codechallenge03.R

val karlaFontFamily = FontFamily(
    Font(R.font.karla_bold, weight = FontWeight.Bold),
    Font(R.font.karla_extrabold, weight = FontWeight.ExtraBold),
    Font(R.font.karla_extralight, weight = FontWeight.ExtraLight),
    Font(R.font.karla_light, weight = FontWeight.Light),
    Font(R.font.karla_medium, weight = FontWeight.Medium),
    Font(R.font.karla_regular, weight = FontWeight.Normal),
    Font(R.font.karla_semibold, weight = FontWeight.SemiBold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = karlaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    body2 = TextStyle(
        fontFamily = karlaFontFamily
    ),
    h1 = TextStyle(
        fontFamily = karlaFontFamily
    ),
    h2 = TextStyle(
        fontFamily = karlaFontFamily
    ),
    h3 = TextStyle(
        fontFamily = karlaFontFamily
    ),
    h4 = TextStyle(
        fontFamily = karlaFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    h5 = TextStyle(
        fontFamily = karlaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)