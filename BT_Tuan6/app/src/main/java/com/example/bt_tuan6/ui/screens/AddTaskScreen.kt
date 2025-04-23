package com.example.bt_tuan6.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bt_tuan6.R
import com.example.bt_tuan6.model.TaskData
import com.example.bt_tuan6.ui.theme.Blue
import com.example.bt_tuan6.viewmodel.MainViewModel
import androidx.compose.ui.text.font.FontWeight


@Composable
fun AddTaskScreen(viewModel: MainViewModel, onBack: () -> Unit) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = onBack,
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    modifier = Modifier.size(40.dp)
                )
            }

            Text(
                text = "Add New",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Blue
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text("Task", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(start = 4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Do homework") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Description", fontWeight = FontWeight.Bold, modifier = Modifier.fillMaxWidth().padding(start = 4.dp))
        Spacer(modifier = Modifier.height(4.dp))
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Don't give up") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = RoundedCornerShape(10.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (title.isNotBlank() && description.isNotBlank()) {
                    viewModel.addTask(TaskData((0..1000).random(), title, description))
                    onBack()
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Blue),
            shape = RoundedCornerShape(50),
            modifier = Modifier.width(200.dp).height(48.dp)
        ) {
            Text("Add", fontWeight = FontWeight.Bold)
        }
    }
}
