package com.example.bt_tuan3_2

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when (intent.getStringExtra("screen")) {
            "onboarding1" -> showOnboardingScreen1() // Màn hình 2
            "intro" -> showIntroScreen() // Màn hình 3
            "onboarding2" -> showOnboardingScreen2() // Màn hình 4
            else -> showSplashScreen() // Mặc định là màn hình 1
        }
    }

    private fun showSplashScreen() { // Màn hình 1
        setContentView(R.layout.activity_main)

        Handler(Looper.getMainLooper()).postDelayed({
            navigateTo("onboarding1") // Splash xong sẽ vào màn hình 2
        }, 2000)
    }

    private fun showOnboardingScreen1() { // Màn hình 2
        setContentView(R.layout.activity_intro_screen)

        findViewById<TextView>(R.id.skipButton)?.setOnClickListener {
            navigateTo("intro") // Nếu bấm Skip thì vào màn hình 3
        }
        findViewById<Button>(R.id.nextButton)?.setOnClickListener {
            navigateTo("intro") // Sau màn hình 2 vào màn hình 3
        }
    }

    private fun showIntroScreen() { // Màn hình 3
        setContentView(R.layout.activity_intro)

        findViewById<TextView>(R.id.skipButton)?.setOnClickListener {
            navigateTo("onboarding2") // Nếu bấm Skip thì vào màn hình 4
        }

        findViewById<Button>(R.id.nextButton)?.setOnClickListener {
            navigateTo("onboarding2") // Sau màn hình 3 vào màn hình 4
        }

        findViewById<ImageView>(R.id.backButton)?.setOnClickListener {
            navigateTo("onboarding1") // Quay lại màn hình 2
        }
    }

    private fun showOnboardingScreen2() { // Màn hình 4
        setContentView(R.layout.activity_onboarding)

        findViewById<Button>(R.id.getStartedButton)?.setOnClickListener {
            navigateTo("splash") // Kết thúc, quay về màn hình 1 (Splash)
        }

        findViewById<ImageView>(R.id.backButton)?.setOnClickListener {
            navigateTo("intro") // Quay lại màn hình 3
        }
    }

    private fun navigateTo(screen: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("screen", screen)
        startActivity(intent)
        finish()
    }
}
