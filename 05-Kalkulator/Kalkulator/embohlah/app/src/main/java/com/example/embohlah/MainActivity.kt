package com.example.embohlah

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 1. Pengaturan padding sistem (Agar tidak tertutup Status Bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- PRAKTEK 1 ---
        val txt = findViewById<EditText>(R.id.txt)
        val btn = findViewById<Button>(R.id.btn)
        val lbl = findViewById<TextView>(R.id.lbl)

        btn.setOnClickListener {
            val isiTeks = txt.text.toString()
            lbl.text = if (isiTeks.isNotEmpty()) isiTeks else "Teks kosong!"
        }

        // --- PRAKTEK 2 ---
        // Komponen Input Biasa
        val etInput = findViewById<EditText>(R.id.etInput)
        val btnTampil = findViewById<Button>(R.id.btnTampil)
        val tvOutput = findViewById<TextView>(R.id.tvOutput)

        // Komponen Aritmatika
        val etA = findViewById<EditText>(R.id.etA)
        val etB = findViewById<EditText>(R.id.etB)
        val btnTambah = findViewById<Button>(R.id.btnTambah)
        val btnKurang = findViewById<Button>(R.id.btnKurang)
        val btnKali = findViewById<Button>(R.id.btnKali)
        val btnBagi = findViewById<Button>(R.id.btnBagi)
        val tvHasil = findViewById<TextView>(R.id.tvHasil)

        // Aksi Tampilkan Teks
        btnTampil.setOnClickListener {
            tvOutput.text = etInput.text.toString()
        }
        // --- LOGIKA ARITMATIKA MENGGUNAKAN INT ---

        // Tambah
        btnTambah.setOnClickListener {
            val a = etA.text.toString().toIntOrNull()
            val b = etB.text.toString().toIntOrNull()
            if (a != null && b != null) {
                tvHasil.text = "Hasil: ${a + b}"
            } else {
                tvHasil.text = "Input harus angka!"
            }
        }

        // Kurang
        btnKurang.setOnClickListener {
            val a = etA.text.toString().toIntOrNull()
            val b = etB.text.toString().toIntOrNull()
            if (a != null && b != null) {
                tvHasil.text = "Hasil: ${a - b}"
            } else {
                tvHasil.text = "Input harus angka!"
            }
        }

        // Kali
        btnKali.setOnClickListener {
            val a = etA.text.toString().toIntOrNull()
            val b = etB.text.toString().toIntOrNull()
            if (a != null && b != null) {
                tvHasil.text = "Hasil: ${a * b}"
            } else {
                tvHasil.text = "Input harus angka!"
            }
        }

        // Bagi
        btnBagi.setOnClickListener {
            val a = etA.text.toString().toDoubleOrNull()
            val b = etB.text.toString().toDoubleOrNull()

            if (a != null && b != null) {
                if (b != 0.0) { // Gunakan 0.0 untuk tipe Double
                    val hasil = a / b
                    tvHasil.text = "Hasil: $hasil"
                } else {
                    tvHasil.text = "Tidak bisa bagi 0!"
                }
            } else {
                tvHasil.text = "Input tidak valid"
            }
        }

    }
}