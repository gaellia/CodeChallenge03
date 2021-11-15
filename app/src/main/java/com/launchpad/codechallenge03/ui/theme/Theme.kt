package com.launchpad.codechallenge03.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = DarkBlue,
    primaryVariant = LightBlue,
    secondary = DarkGreen,
    secondaryVariant = LightGreen,
    surface = Color.White,
    onSurface = Color.Black,
    onBackground = Color.Black
)

private val LightColorPalette = lightColors(
    primary = LightBlue,
    primaryVariant = DarkBlue,
    secondary = DarkGreen,
    secondaryVariant = LightGreen,
    surface = Color.White
)

@Composable
fun CodeChallenge03Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}