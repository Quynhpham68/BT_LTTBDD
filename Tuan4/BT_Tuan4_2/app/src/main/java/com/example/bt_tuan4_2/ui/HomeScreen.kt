//package com.example.bt_tuan4_2.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.bt_tuan4_2.viewmodel.TaskViewModel
//
//@Composable
//fun HomeScreen(navToDetail: (Int) -> Unit) {
//    val taskViewModel: TaskViewModel = viewModel()
//    val tasks by taskViewModel.tasks.collectAsState()
//
//    Scaffold(
//        topBar = { TopAppBar(title = { Text("SmartTasks") }) }
//    ) { paddingValues ->
//        LazyColumn(
//            contentPadding = paddingValues,
//            modifier = Modifier.fillMaxSize()
//        ) {
//            items(tasks) { task ->
//                TaskItem(task, onClick = { navToDetail(task.id) })
//            }
//        }
//    }
//}
