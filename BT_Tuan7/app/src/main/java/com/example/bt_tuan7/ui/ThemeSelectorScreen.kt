package com.example.bt_tuan7.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bt_tuan7.model.*

@Composable
fun ThemeSelectorScreen(
    selectedTheme: AppTheme,
    onThemeSelected: (AppTheme) -> Unit,
    navController: NavController
) {
    // Nền trắng
    val themeColors = if (selectedTheme == AppTheme.Light) {
        ThemeColors(Color.White, Color.Black, Color(0xFF2196F3))
    } else {
        getThemeColors(selectedTheme)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = themeColors.backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Setting", color = themeColors.contentColor, fontSize = 24.sp)

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                "Choosing the right theme sets the tone and personality of your app.",
                color = themeColors.contentColor
            )

            Spacer(modifier = Modifier.height(24.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ThemeButton(Color(0xFF121212), selectedTheme == AppTheme.Dark) {
                    onThemeSelected(AppTheme.Dark)
                }
                ThemeButton(Color(0xD2CC0B9C), selectedTheme == AppTheme.Pink) {
                    onThemeSelected(AppTheme.Pink)
                }
                ThemeButton(Color(0xFF319EDE), selectedTheme == AppTheme.Blue) {
                    onThemeSelected(AppTheme.Blue)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.navigate("detail") },
                colors = ButtonDefaults.buttonColors(backgroundColor = themeColors.buttonColor)
            ) {
                Text("Apply", color = Color.White)
            }
        }
    }
}
