//package com.example.bt_tuan4_2.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import com.example.bt_tuan4_2.data.RetrofitInstance
//import com.example.bt_tuan4_2.data.Task
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//
//@Composable
//fun DetailScreen(taskId: Int, onBack: () -> Unit) {
//    var task by remember { mutableStateOf<Task?>(null) }
//
//    LaunchedEffect(taskId) {
//        task = RetrofitInstance.api.getTaskDetail(taskId)
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Task Detail") },
//                navigationIcon = {
//                    IconButton(onClick = { onBack() }) {
//                        Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { paddingValues ->
//        task?.let {
//            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
//                Text(it.title, style = MaterialTheme.typography.h5)
//                Text(it.description, style = MaterialTheme.typography.body1)
//                Text("Category: ${it.category}")
//                Text("Status: ${it.status}")
//                Text("Priority: ${it.priority}")
//            }
//        }
//    }
//}
