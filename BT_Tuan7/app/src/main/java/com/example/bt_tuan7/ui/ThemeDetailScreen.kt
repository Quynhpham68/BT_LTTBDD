package com.example.bt_tuan7.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.bt_tuan7.model.AppTheme
import com.example.bt_tuan7.model.getThemeColors
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign


@Composable
fun ThemeDetailScreen(selectedTheme: AppTheme, navController: NavController) {
    val themeColors = getThemeColors(selectedTheme)

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
            Text(
                text = selectedTheme.name,
                color = themeColors.contentColor,
                fontSize = 24.sp
            )

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Choosing the right theme sets the tone\n" +
                        "and personality of your app, enhancing\n" +
                        "user experience and reinforcing your\n" +
                        "brand identity.",
                color = themeColors.contentColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = { navController.popBackStack() },
                colors = ButtonDefaults.buttonColors(backgroundColor = themeColors.buttonColor)
            ) {
                Text("Back", color = Color.White)
            }
        }
    }
}
