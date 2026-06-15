package com.example.vincoffeecafe

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.graphics.Color
import android.content.Intent // Pastikan import Intent ini sudah ada

class DetailProdukActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_produk)

        supportActionBar?.hide()

        val btnBack = findViewById<ImageView>(R.id.mingcute_arrow_left)
        val btnHeart = findViewById<ImageView>(R.id.mdi_heart_outline)
        val btnKecil = findViewById<Button>(R.id.btn_ukuran_kecil)
        val btnSedang = findViewById<Button>(R.id.btn_ukuran_sedang)
        val btnBesar = findViewById<Button>(R.id.btn_ukuran_besar)
        val btnTambahKeranjang = findViewById<Button>(R.id.btn_tambah_keranjang)

        // Set awal dibuka: Sedang Aktif (Biru), Kecil & Besar Pasif (Abu-abu/Biru Muda)
        setButtonActive(btnSedang)
        setButtonInactive(btnKecil)
        setButtonInactive(btnBesar)

        btnBack.setOnClickListener { finish() }
        btnHeart.setOnClickListener { Toast.makeText(this, "Sukses masuk daftar favorit!", Toast.LENGTH_SHORT).show() }

        // Atur klik agar warna berpindah secara benar (tidak kebalik)
        btnKecil.setOnClickListener {
            setButtonActive(btnKecil)
            setButtonInactive(btnSedang)
            setButtonInactive(btnBesar)
            Toast.makeText(this, "Ukuran Kecil Dipilih", Toast.LENGTH_SHORT).show()
        }

        btnSedang.setOnClickListener {
            setButtonInactive(btnKecil)
            setButtonActive(btnSedang)
            setButtonInactive(btnBesar)
            Toast.makeText(this, "Ukuran Sedang Dipilih", Toast.LENGTH_SHORT).show()
        }

        btnBesar.setOnClickListener {
            setButtonInactive(btnKecil)
            setButtonInactive(btnSedang)
            setButtonActive(btnBesar)
            Toast.makeText(this, "Ukuran Besar Dipilih", Toast.LENGTH_SHORT).show()
        }

        // ==================== REVISI HUBUNGAN ALUR PINDAH KE KERANJANG ====================
        btnTambahKeranjang.setOnClickListener {
            Toast.makeText(this, "Pesanan berhasil masuk keranjang!", Toast.LENGTH_LONG).show()

            // Alur: Pindah langsung ke Layer 6 (KeranjangActivity)
            val intent = Intent(this, KeranjangActivity::class.java)
            startActivity(intent)
        }
    }

    // Fungsi pembantu untuk membuat tombol berwarna BIRU AKTIF
    private fun setButtonActive(button: Button) {
        button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#0056D2")))
        button.setTextColor(Color.parseColor("#FFFFFF"))
    }

    // Fungsi pembantu untuk membuat tombol berwarna PUDAR/TIDAK AKTIF
    private fun setButtonInactive(button: Button) {
        button.setBackgroundTintList(android.content.res.ColorStateList.valueOf(Color.parseColor("#E3F2FD")))
        button.setTextColor(Color.parseColor("#0056D2"))
    }
}