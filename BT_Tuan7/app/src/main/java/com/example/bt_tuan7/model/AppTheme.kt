package com.example.bt_tuan7.model

import androidx.compose.ui.graphics.Color

enum class AppTheme {
    Light, Dark, Pink, Blue, Purple
}

data class ThemeColors(
    val backgroundColor: Color,
    val contentColor: Color,
    val buttonColor: Color
)

fun getThemeColors(theme: AppTheme): ThemeColors {
    return when (theme) {
        AppTheme.Light -> ThemeColors(Color.White, Color.Black, Color(0xFF2196F3))
        AppTheme.Dark -> ThemeColors(Color(0xFF121212), Color.White, Color(0xFF2196F3))
        AppTheme.Pink -> ThemeColors(Color(0xFFE91E63), Color.White, Color(0xFF2196F3))
        AppTheme.Blue -> ThemeColors(Color(0xFF81D4FA), Color.Black, Color(0xFF2196F3))
        AppTheme.Purple -> ThemeColors(Color(0xFFCE93D8), Color.Black, Color(0xFFAB47BC))
    }
}

