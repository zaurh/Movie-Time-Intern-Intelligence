package com.zaurh.movieappintern2.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    surface = ThemeColors.Night.surface,
    background = ThemeColors.Night.background,
    primary = ThemeColors.Night.text,
)

private val LightColorScheme = lightColorScheme(
    surface = ThemeColors.Day.surface,
    background = ThemeColors.Day.background,
    primary = ThemeColors.Day.text
)

@Composable
fun MovieAppIntern2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}