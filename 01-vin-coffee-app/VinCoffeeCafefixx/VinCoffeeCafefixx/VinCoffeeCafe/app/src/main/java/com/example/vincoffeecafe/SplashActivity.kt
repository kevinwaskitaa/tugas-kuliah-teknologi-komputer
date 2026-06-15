package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        // Sembunyikan ActionBar bawaan Android agar full screen bersih
        supportActionBar?.hide()

        // Timer 3 detik (3000ms) untuk pindah otomatis ke LoginActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish() // Menutup Splash agar tidak bisa di-back oleh user
        }, 3000)
    }
}