package com.lixiang.remotedisplay.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = Accent,
    primaryVariant = Accent,
    secondary = Success,
    background = Background,
    surface = Card,
    onPrimary = Color.Black
)

private val LightColorPalette = lightColors(
    primary = Accent,
    primaryVariant = Accent,
    secondary = Success
)

@Composable
fun LixiangTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette
    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
