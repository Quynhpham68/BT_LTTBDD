package com.example.bt_tuan5_2

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.graphics.Color
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack

@Composable
fun ProfileScreen(auth: FirebaseAuth, navController: NavController) {
    val user = auth.currentUser
    var displayName by remember { mutableStateOf(user?.displayName ?: "") }
    var email by remember { mutableStateOf(user?.email ?: "") }
    var birthDate by remember { mutableStateOf("") }
    var photoUrl by remember { mutableStateOf(user?.photoUrl) }

    val imagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) {
            photoUrl = uri
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Thanh trên chứa nút back và tiêu đề "Profile"
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("profile") { inclusive = true }
                    }
                },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(Icons.Filled.ArrowBack, contentDescription = "Back", tint = Color(0xFF007AFF))
            }

            Text(
                text = "Profile",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF007AFF),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Ảnh đại diện có thể thay đổi
        Box(
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
                .clickable { imagePickerLauncher.launch("image/*") }
        ) {
            Image(
                painter = rememberAsyncImagePainter(photoUrl ?: "https://via.placeholder.com/120"),
                contentDescription = "Profile Picture",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Trường nhập thông tin
        OutlinedTextField(
            value = displayName,
            onValueChange = { displayName = it },
            label = { Text("Name") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {},
            label = { Text("Email") },
            enabled = false,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            label = { Text("Date of Birth") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Nút quay lại màn hình login
        Button(
            onClick = {
                navController.navigate("login") {
                    popUpTo("profile") { inclusive = true }
                }
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF007AFF)),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Back", color = Color.White)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen() {
    val navController = rememberNavController()
    ProfileScreen(auth = FirebaseAuth.getInstance(), navController = navController)
}
