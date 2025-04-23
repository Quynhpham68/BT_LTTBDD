package com.example.bt_tuan4_2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

// ✅ 1. Model dữ liệu Task
data class Task(val id: Int, val title: String, val description: String, val status: String)

// ✅ 2. Interface API
interface TaskApi {
    @GET("tasks")
    suspend fun getTasks(): List<Task>

    @GET("task/{id}")
    suspend fun getTask(@Path("id") id: Int): Task

    @DELETE("task/{id}")
    suspend fun deleteTask(@Path("id") id: Int)
}

// ✅ 3. Tạo instance Retrofit
val retrofit = Retrofit.Builder()
    .baseUrl("https://amock.io/api/researchUTH/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()
val api = retrofit.create(TaskApi::class.java)

// ✅ 4. Điều hướng trong ứng dụng
@Composable
fun AppNavigator() {
    var selectedTaskId by remember { mutableStateOf<Int?>(null) }

    if (selectedTaskId == null) {
        HomeScreen(navigateToDetail = { selectedTaskId = it })
    } else {
        TaskDetailScreen(
            taskId = selectedTaskId!!,
            onDelete = { selectedTaskId = null },
            onBack = { selectedTaskId = null }
        )
    }
}

// ✅ 5. Màn hình Trang chủ
@Composable
fun HomeScreen(navigateToDetail: (Int) -> Unit) {
    var tasks by remember { mutableStateOf(emptyList<Task>()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    // Gọi API lấy danh sách công việc
    LaunchedEffect(Unit) {
        try {
            tasks = api.getTasks()
        } catch (e: Exception) {
            errorMessage = "Failed to load tasks!"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("SmartTasks") }) }
    ) { contentPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(contentPadding).padding(16.dp)) {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search tasks") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(10.dp))

            when {
                isLoading -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
                errorMessage != null -> {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(errorMessage!!, color = Color.Red)
                    }
                }
                tasks.isEmpty() -> {
                    NoTaskScreen()
                }
                else -> {
                    LazyColumn {
                        items(tasks.filter { it.title.contains(searchQuery, ignoreCase = true) }) { task ->
                            TaskItem(task = task, onClick = { navigateToDetail(task.id) })
                        }
                    }
                }
            }
        }
    }
}

// ✅ 6. Giao diện khi không có công việc
@Composable
fun NoTaskScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("No Tasks Yet!", fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text("Stay productive—add something to do")
    }
}

// ✅ 7. Hiển thị một công việc trong danh sách
@Composable
fun TaskItem(task: Task, onClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(task.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(task.description, fontSize = 14.sp, color = Color.Gray)
            Text("Status: ${task.status}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ✅ 8. Màn hình Chi tiết công việc
@Composable
fun TaskDetailScreen(taskId: Int, onDelete: () -> Unit, onBack: () -> Unit) {
    var task by remember { mutableStateOf<Task?>(null) }
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(taskId) {
        try {
            task = api.getTask(taskId)
        } catch (e: Exception) {
            errorMessage = "Failed to load task details!"
        } finally {
            isLoading = false
        }
    }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Task Details") }) }
    ) { contentPadding ->
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            errorMessage != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(errorMessage!!, color = Color.Red)
                }
            }
            else -> {
                task?.let {
                    Column(modifier = Modifier.fillMaxSize().padding(contentPadding).padding(16.dp)) {
                        Text(it.title, fontSize = 22.sp, fontWeight = FontWeight.Bold)
                        Text(it.description, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(10.dp))

                        Row {
                            Button(
                                onClick = {
                                    coroutineScope.launch {
                                        try {
                                            api.deleteTask(taskId)
                                            onDelete()
                                        } catch (e: Exception) {
                                            e.printStackTrace()
                                        }
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                            ) {
                                Text("Delete Task", color = Color.White)
                            }

                            Spacer(modifier = Modifier.width(10.dp))

                            Button(onClick = { onBack() }) {
                                Text("Back")
                            }
                        }
                    }
                }
            }
        }
    }
}

// ✅ 9. MainActivity - Khởi chạy ứng dụng
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigator()
        }
    }
}
