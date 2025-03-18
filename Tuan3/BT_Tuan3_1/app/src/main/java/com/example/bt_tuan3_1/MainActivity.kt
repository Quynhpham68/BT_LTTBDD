package com.example.bt_tuan3_1

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent.getStringExtra("screen")) {
            "screen2" -> showScreen2()
            "screen3" -> showScreen3()
            else -> showMainScreen()
        }
    }

    // Màn hình 1 (Màn hình chính)
    private fun showMainScreen() {
        setContentView(R.layout.activity_main)

        val readyButton: TextView = findViewById(R.id.readyButton)
        readyButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("screen", "screen2")
            startActivity(intent)
            finish()
        }
    }

    // Màn hình 2 - Danh sách UI Components
    private fun showScreen2() {
        setContentView(R.layout.activity_splash)

        val textField: TextView = findViewById(R.id.textFieldTitle)
        val passwordField: TextView = findViewById(R.id.passwordFieldTitle)
        val columnLayout: TextView = findViewById(R.id.columnTitle)
        val rowLayout: TextView = findViewById(R.id.rowTitle)

        textField.setOnClickListener { openScreen3("TextField") }
        passwordField.setOnClickListener { openScreen3("PasswordField") }
        columnLayout.setOnClickListener { openScreen3("Column Layout") }
        rowLayout.setOnClickListener { openScreen3("Row Layout") }
    }

    // Chuyển sang màn hình 3 với nội dung chi tiết
    private fun openScreen3(content: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("screen", "screen3")
        intent.putExtra("content", content)
        startActivity(intent)
        finish()
    }

    // Màn hình 3 - Hiển thị thông tin chi tiết
    private fun showScreen3() {
        setContentView(R.layout.activity_text_detail)

        val backButton: ImageView = findViewById(R.id.backButton)

        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("screen", "screen2")
            startActivity(intent)
            finish()
        }
    }
}
