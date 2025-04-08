package com.example.bt_tuan5_1

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import androidx.activity.result.IntentSenderRequest
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val loginState by viewModel.loginState.collectAsState()
            val systemUiController = rememberSystemUiController()
            systemUiController.setSystemBarsColor(Color.Black)

            LoginScreen(
                loginState = loginState,
                onLoginClick = { signInWithGoogle() }
            )
        }
    }

    private val googleSignInClient: SignInClient by lazy {
        Identity.getSignInClient(this)
    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data
                val credential = googleSignInClient.getSignInCredentialFromIntent(data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    viewModel.signInWithGoogle(idToken)
                } else {
                    viewModel.updateLoginState(LoginState.Error("User canceled Google sign-in."))
                }
            } else {
                viewModel.updateLoginState(LoginState.Error("Sign-in failed."))
            }
        }

    private fun signInWithGoogle() {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(getString(R.string.default_web_client_id))
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        googleSignInClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                result.pendingIntent?.let { pendingIntent ->
                    val intentSenderRequest = IntentSenderRequest.Builder(pendingIntent).build()
                    signInLauncher.launch(intentSenderRequest)
                } ?: run {
                    viewModel.updateLoginState(LoginState.Error("PendingIntent null!"))
                }
            }
            .addOnFailureListener { e ->
                viewModel.updateLoginState(LoginState.Error("Google Sign-In Failed: ${e.message}"))
            }
    }
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loginState = MutableStateFlow<LoginState>(LoginState.Idle)
    val loginState: StateFlow<LoginState> = _loginState

    fun signInWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { result ->
                val email = result.user?.email ?: "No Email"
                _loginState.value = LoginState.Success(email)
            }
            .addOnFailureListener { exception ->
                _loginState.value = LoginState.Error("Google Sign-In Failed: ${exception.message}")
            }
    }

    fun updateLoginState(state: LoginState) {
        _loginState.value = state
    }
}

sealed class LoginState {
    object Idle : LoginState()
    data class Success(val email: String) : LoginState()
    data class Error(val message: String) : LoginState()
}

@Composable
fun LoginScreen(loginState: LoginState, onLoginClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = onLoginClick,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(50.dp)
        ) {
            Text(text = "Login by Gmail", color = Color.White)
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (loginState) {
            is LoginState.Idle -> {}
            is LoginState.Success -> {
                LoginMessageBox(
                    title = "Success!",
                    message = "Hi ${loginState.email}\nWelcome to UTHSmartTasks",
                    backgroundColor = Color(0xFFD0E8FF) // Màu xanh giống hình 3
                )
            }
            is LoginState.Error -> {
                LoginMessageBox(
                    title = "Google Sign-In Failed",
                    message = loginState.message,
                    backgroundColor = Color(0xFFFFD0D0) // Màu đỏ giống hình 2
                )
            }
        }
    }
}

@Composable
fun LoginMessageBox(title: String, message: String, backgroundColor: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .background(backgroundColor, shape = RoundedCornerShape(12.dp))
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = message,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    LoginScreen(loginState = LoginState.Idle, onLoginClick = {})
}
