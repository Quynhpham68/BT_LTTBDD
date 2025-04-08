package com.example.bt_tuan6.viewmodel

import androidx.lifecycle.ViewModel
import com.example.bt_tuan6.model.TaskData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainViewModel : ViewModel() {
    private val _tasks = MutableStateFlow<List<TaskData>>(
        listOf(
            TaskData(1, "Complete Android Project", "Finish the UI, integrate API, and write documentation"),
            TaskData(2, "Complete Android Project", "Finish the UI, integrate API, and write documentation"),
            TaskData(3, "Complete Android Project", "Finish the UI, integrate API, and write documentation"),
            TaskData(4, "Complete Android Project", "Finish the UI, integrate API, and write documentation")
        )
    )
    val tasks: StateFlow<List<TaskData>> get() = _tasks

    fun addTask(newTask: TaskData) {
        _tasks.update { currentTasks -> listOf(newTask) + currentTasks }
    }
}