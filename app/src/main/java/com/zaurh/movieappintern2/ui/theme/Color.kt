package com.zaurh.movieappintern2.ui.theme

import androidx.compose.ui.graphics.Color

sealed class ThemeColors(
    val surface: Color,
    val background: Color,
    val text: Color
) {
    object Night : ThemeColors(
        surface = Color(0xFF070420),
        background = Color(0xFF25233D),
        text = Color.White
    )

    object Day : ThemeColors(
        surface = Color(0xFFEBFFF9),
        background = Color(0xFFD0EBF1),
        text = Color(0xFF070420)
    )
}