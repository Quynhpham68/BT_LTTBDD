package com.example.bt_tuan3_th01

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var btnSay: Button
    private lateinit var tvResult: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        btnSay = findViewById(R.id.btnSay)
        tvResult = findViewById(R.id.tvResult)

        btnSay.setOnClickListener {
            tvResult.text = "Phạm Thị Diễm Quỳnh"
        }
    }
}