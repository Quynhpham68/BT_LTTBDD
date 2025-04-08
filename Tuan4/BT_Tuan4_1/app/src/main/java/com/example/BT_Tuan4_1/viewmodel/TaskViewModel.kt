package com.example.BT_Tuan4_1.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BT_Tuan4_1.api.RetrofitInstance
import com.example.BT_Tuan4_1.model.Task
import com.example.BT_Tuan4_1.model.TaskResponse
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {
    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    init {
        getTasks()
    }

    private fun getTasks() {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTasks()
                if (response.isSuccessful) {
                    val taskResponse: TaskResponse? = response.body()
                    _tasks.value = taskResponse?.data
                } else {
                    // Handle the error (e.g., log the error)
                    println("Error: ${response.code()}")
                }
            } catch (e: Exception) {
                // Handle exceptions (e.g., log the exception)
                println("Exception: ${e.message}")
            }
        }
    }
}