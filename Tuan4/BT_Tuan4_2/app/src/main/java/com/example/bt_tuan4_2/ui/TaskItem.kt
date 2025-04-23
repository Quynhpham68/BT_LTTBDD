//package com.example.bt_tuan4_2.ui
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.bt_tuan4_2.data.Task
//
//@Composable
//fun TaskItem(task: Task, onClick: () -> Unit) {
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp)
//            .clickable { onClick() },
//        elevation = 4.dp
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(task.title, style = MaterialTheme.typography.h6)
//            Text(task.description, style = MaterialTheme.typography.body2)
//            Text("Status: ${task.status}", color = MaterialTheme.colors.primary)
//        }
//    }
//}
