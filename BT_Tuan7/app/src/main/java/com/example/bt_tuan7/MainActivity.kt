package com.example.bt_tuan7

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.navigation.compose.*
import com.example.bt_tuan7.model.AppTheme
import com.example.bt_tuan7.ui.ThemeDetailScreen
import com.example.bt_tuan7.ui.ThemeSelectorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var selectedTheme by remember { mutableStateOf(AppTheme.Light) }
            val navController = rememberNavController()

            NavHost(navController = navController, startDestination = "selector") {
                composable("selector") {
                    ThemeSelectorScreen(
                        selectedTheme = selectedTheme,
                        onThemeSelected = { selectedTheme = it },
                        navController = navController
                    )
                }
                composable("detail") {
                    ThemeDetailScreen(selectedTheme = selectedTheme, navController = navController)
                }
            }
        }
    }
}
