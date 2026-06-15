package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        supportActionBar?.hide()

        val btnDaftar = findViewById<Button>(R.id.btn_daftar)
        val tvMasukDisini = findViewById<TextView>(R.id.tv_masuk_disini)

        // Ketika tombol daftar ditekan, lari ke Dashboard Utama (MainActivity)
        btnDaftar.setOnClickListener {
            Toast.makeText(this, "Pendaftaran Berhasil!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, HomeActivity::class.java) // Diubah ke HomeActivity
            startActivity(intent)
            finish()
        }

        // Ketika teks "Masuk disini" ditekan, kembali ke Halaman Login
        tvMasukDisini.setOnClickListener {
            finish() // Menutup activity ini agar otomatis kembali ke tumpukan LoginActivity di belakang
        }
    }
}