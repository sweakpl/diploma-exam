package com.example.diplomaexam.presentation.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val ColorPalette = lightColors(
    primary = MidnightBlue,
    primaryVariant = Stratos,
    secondary = PeruTan,
    secondaryVariant = IndianTan,
    background = Color.White,
    surface = Color.White,
    error = Monza,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White
)

@Composable
fun DiplomaExamTheme(
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalSpace provides Space()) {
        MaterialTheme(
            colors = ColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}