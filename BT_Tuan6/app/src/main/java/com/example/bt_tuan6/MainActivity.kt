package com.example.bt_tuan6

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bt_tuan6.ui.theme.BT_Tuan6Theme
import com.example.bt_tuan6.viewmodel.MainViewModel
import com.example.bt_tuan6.ui.MainApp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BT_Tuan6Theme {
                val viewModel: MainViewModel = viewModel()
                MainApp(viewModel)
            }
        }
    }
}