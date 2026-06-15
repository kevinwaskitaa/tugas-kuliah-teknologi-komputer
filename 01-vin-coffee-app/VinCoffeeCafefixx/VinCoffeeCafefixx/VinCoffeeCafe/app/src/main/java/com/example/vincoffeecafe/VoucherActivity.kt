package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class VoucherActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_voucher)

        // Sembunyikan ActionBar bawaan Android agar mengikuti desain penuh custom header kamu
        supportActionBar?.hide()

        // Inisialisasi komponen menggunakan ImageView sesuai struktur asli layout kamu
        val navHome = findViewById<ImageView>(R.id.nav_home)
        val navMaps = findViewById<ImageView>(R.id.nav_maps)
        val navVouchers = findViewById<ImageView>(R.id.nav_vouchers)
        val navProfile = findViewById<ImageView>(R.id.nav_profile)
        val btnBack = findViewById<ImageView>(R.id.btn_back)

        // ==================== NAVIGASI NAVBAR BAWAH (HUBUNGAN BOLAK-BALIK) ====================

        // 1. Klik Ikon Home -> Berpindah ke HomeActivity
        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) // Menghilangkan kedipan efek transisi antar activity
            finish()
        }

        // 2. Klik Ikon Maps -> Berpindah ke LokasiOutletActivity
        navMaps.setOnClickListener {
            val intent = Intent(this, LokasiOutletActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        // Karena ini halaman Voucher aktif, jika diklik cukup tampilkan Toast penanda posisi halaman
        navVouchers.setOnClickListener {
            Toast.makeText(this, "Anda sedang melihat daftar voucher", Toast.LENGTH_SHORT).show()
        }

        // 3. Klik Ikon Profile -> Berpindah ke ProfileActivity
        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        // ==================== KONTROL HEADER ATAS ====================

        // Tombol Kembali di Pojok Kiri Atas Header
        btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}