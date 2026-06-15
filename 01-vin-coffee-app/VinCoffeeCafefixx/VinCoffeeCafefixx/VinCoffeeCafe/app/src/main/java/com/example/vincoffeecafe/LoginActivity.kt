package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        val btnMasuk = findViewById<Button>(R.id.btn_masuk)
        val tvDaftar = findViewById<TextView>(R.id.tv_daftar)

        btnMasuk.setOnClickListener {
            Toast.makeText(this, "Berhasil Masuk!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java) // Diubah ke HomeActivity
            startActivity(intent)
            finish()
        }

        tvDaftar.setOnClickListener {
            val intentKeRegister = Intent(this, RegisterActivity::class.java)
            startActivity(intentKeRegister)
        }
    }
}