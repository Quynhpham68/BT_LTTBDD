package com.example.bt_tuan6.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.bt_tuan6.viewmodel.MainViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.*
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


@Composable
fun HomeScreen(viewModel: MainViewModel) {
    val tasks by viewModel.tasks.collectAsState()
    val cardColors = listOf(
        Color(0x4D2196F3),
        Color(0xFFE1BBC1),
        Color(0xFFDDE1B6),
        Color(0xFFE1BBC1)
    )
    Column(modifier = Modifier.padding(16.dp)) {
        tasks.forEachIndexed { index, task ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                colors = CardDefaults.cardColors(containerColor = cardColors[index % cardColors.size]),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(task.title, style = MaterialTheme.typography.titleMedium, color = Color.Black)
                    Text(task.description, color = Color.DarkGray)
                }
            }
        }
    }
}
