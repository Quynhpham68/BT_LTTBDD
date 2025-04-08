package com.example.bt_tuan5_2

import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import androidx.navigation.compose.rememberNavController


@Composable
fun LoginScreen(auth: FirebaseAuth, googleSignInClient: GoogleSignInClient, navController: NavController) {
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf("") }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == android.app.Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        navController.navigate("profile") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        errorMessage = "Đăng nhập thất bại!"
                    }
                }
            } catch (e: Exception) {
                Log.e("GoogleSignInScreen", "Google Sign-In failed", e)
                errorMessage = "Google Sign-In thất bại!"
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Spacer để đẩy nội dung xuống giữa màn hình
        Spacer(modifier = Modifier.height(100.dp))

        // Logo
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.size(200.dp) // Điều chỉnh kích thước theo nhu cầu
        )
        Spacer(modifier = Modifier.height(16.dp))

        // App Name and Tagline
        Text("SmartTasks", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color(0xFF007AFF))
        Text("A simple and efficient to-do app", fontSize = 14.sp, color = Color(0xFF007AFF))

        Spacer(modifier = Modifier.height(120.dp)) // Tạo khoảng cách lớn hơn để căn giữa

        // Welcome Text and Description
        Text("Welcome", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Text("Ready to explore? Log in to get started.", fontSize = 14.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(24.dp)) // Lùi phần button xuống xa hơn

        // Sign In Button
        Button(
            onClick = {
                googleSignInClient.signOut().addOnCompleteListener {
                    launcher.launch(googleSignInClient.signInIntent)
                }
            },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(0.8f),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                containerColor = Color(0xFFB3E5FC) // Màu xanh nhạt
            )
        ) {
            Image(
                painter = painterResource(R.drawable.ic_google),
                contentDescription = "Google",
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text("SIGN IN WITH GOOGLE", color = Color.Black) // Chữ màu đậm hơn để dễ nhìn
        }

        // Display Error Message
        if (errorMessage.isNotEmpty()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(errorMessage, color = Color.Red, fontSize = 14.sp)
        }

        // Spacer để đẩy footer xuống đáy màn hình
        Spacer(modifier = Modifier.weight(1f))

        // Footer
        Text("© UTHSmartTasks", fontSize = 12.sp, color = Color.Gray)
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    val mockAuth = FirebaseAuth.getInstance()
    val mockGoogleSignInClient: GoogleSignInClient = GoogleSignIn.getClient(LocalContext.current, GoogleSignInOptions.DEFAULT_SIGN_IN)
    val mockNavController = rememberNavController()

    LoginScreen(auth = mockAuth, googleSignInClient = mockGoogleSignInClient, navController = mockNavController)
}