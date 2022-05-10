package com.thoughtworks.lonestarcafe.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val lightThemeColors = lightColors(
    primary = Color.PurplePrimary,
    primaryVariant = Color.PurpleDark,
    background = Color.PurpleLightPrimary,
    surface = Color.White,
    onPrimary = Color.White,
    secondary = Color.RedPrimary,
    secondaryVariant = Color.RedLight,
    onSecondary = Color.White,
    error = Color.RedDark,
    onBackground = Color.BlackText,
    onSurface = Color.BlackText
)

@Composable
fun LoneStarCafeTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = lightThemeColors,
        content = content
    )
}