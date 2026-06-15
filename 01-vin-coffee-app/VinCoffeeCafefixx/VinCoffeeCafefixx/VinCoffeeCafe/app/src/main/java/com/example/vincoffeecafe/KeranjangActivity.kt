package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class KeranjangActivity : AppCompatActivity() {

    // Kuantitas Awal untuk 4 Produk
    private var kuantitas1 = 1
    private var kuantitas2 = 1
    private var kuantitas3 = 1
    private var kuantitas4 = 1 // Tambahan Produk 4

    // Daftar harga masing-masing produk sesuai urutan di XML
    private val hargaProduk1 = 20000
    private val hargaProduk2 = 20000
    private val hargaProduk3 = 20000
    private val hargaProduk4 = 20000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_keranjang)

        supportActionBar?.hide()

        // Inisialisasi Komponen Utama
        val btnBack = findViewById<ImageView>(R.id.btn_back_keranjang)
        val btnKonfirmasi = findViewById<Button>(R.id.btn_konfirmasi_pesanan)
        val tvTotalPembayaran = findViewById<TextView>(R.id.tv_total_pembayaran)

        // ==================== INISIALISASI ITEM 1 ====================
        val btnMinus1 = findViewById<TextView>(R.id.tanda_minus_1)
        val btnPlus1 = findViewById<TextView>(R.id.tanda_plus_1)
        val tvKuantitas1 = findViewById<TextView>(R.id.text_kuantitas_1)
        val btnTrash1 = findViewById<ImageView>(R.id.tabler_trash_1)

        // ==================== INISIALISASI ITEM 2 ====================
        val btnMinus2 = findViewById<TextView>(R.id.tanda_minus_2)
        val btnPlus2 = findViewById<TextView>(R.id.tanda_plus_2)
        val tvKuantitas2 = findViewById<TextView>(R.id.text_kuantitas_2)
        val btnTrash2 = findViewById<ImageView>(R.id.tabler_trash_2)

        // ==================== INISIALISASI ITEM 3 ====================
        val btnMinus3 = findViewById<TextView>(R.id.tanda_minus_3)
        val btnPlus3 = findViewById<TextView>(R.id.tanda_plus_3)
        val tvKuantitas3 = findViewById<TextView>(R.id.text_kuantitas_3)
        val btnTrash3 = findViewById<ImageView>(R.id.tabler_trash_3)

        // ==================== INISIALISASI ITEM 4 ====================
        val btnMinus4 = findViewById<TextView>(R.id.tanda_minus_4)
        val btnPlus4 = findViewById<TextView>(R.id.tanda_plus_4)
        val tvKuantitas4 = findViewById<TextView>(R.id.text_kuantitas_4)
        val btnTrash4 = findViewById<ImageView>(R.id.tabler_trash_4)

        // Hitung total harga pertama kali saat activity dimuat
        updateTotalHarga(tvTotalPembayaran)

        btnBack.setOnClickListener { finish() }

        // ==================== AKSI ITEM 1 ====================
        btnPlus1.setOnClickListener {
            kuantitas1++
            tvKuantitas1.text = kuantitas1.toString()
            updateTotalHarga(tvTotalPembayaran)
        }
        btnMinus1.setOnClickListener {
            if (kuantitas1 > 1) {
                kuantitas1--
                tvKuantitas1.text = kuantitas1.toString()
                updateTotalHarga(tvTotalPembayaran)
            }
        }
        btnTrash1.setOnClickListener {
            kuantitas1 = 0
            findViewById<RelativeLayout>(R.id.pesanan_1).visibility = android.view.View.GONE
            updateTotalHarga(tvTotalPembayaran)
            Toast.makeText(this, "Torabika Latte dihapus", Toast.LENGTH_SHORT).show()
        }

        // ==================== AKSI ITEM 2 ====================
        btnPlus2.setOnClickListener {
            kuantitas2++
            tvKuantitas2.text = kuantitas2.toString()
            updateTotalHarga(tvTotalPembayaran)
        }
        btnMinus2.setOnClickListener {
            if (kuantitas2 > 1) {
                kuantitas2--
                tvKuantitas2.text = kuantitas2.toString()
                updateTotalHarga(tvTotalPembayaran)
            }
        }
        btnTrash2.setOnClickListener {
            kuantitas2 = 0
            findViewById<RelativeLayout>(R.id.pesanan_2).visibility = android.view.View.GONE
            updateTotalHarga(tvTotalPembayaran)
            Toast.makeText(this, "Espresso Machiato dihapus", Toast.LENGTH_SHORT).show()
        }

        // ==================== AKSI ITEM 3 ====================
        btnPlus3.setOnClickListener {
            kuantitas3++
            tvKuantitas3.text = kuantitas3.toString()
            updateTotalHarga(tvTotalPembayaran)
        }
        btnMinus3.setOnClickListener {
            if (kuantitas3 > 1) {
                kuantitas3--
                tvKuantitas3.text = kuantitas3.toString()
                updateTotalHarga(tvTotalPembayaran)
            }
        }
        btnTrash3.setOnClickListener {
            kuantitas3 = 0
            findViewById<RelativeLayout>(R.id.pesanan_3).visibility = android.view.View.GONE
            updateTotalHarga(tvTotalPembayaran)
            Toast.makeText(this, "Caramel Latte dihapus", Toast.LENGTH_SHORT).show()
        }

        // ==================== AKSI ITEM 4 ====================
        btnPlus4.setOnClickListener {
            kuantitas4++
            tvKuantitas4.text = kuantitas4.toString()
            updateTotalHarga(tvTotalPembayaran)
        }
        btnMinus4.setOnClickListener {
            if (kuantitas4 > 1) {
                kuantitas4--
                tvKuantitas4.text = kuantitas4.toString()
                updateTotalHarga(tvTotalPembayaran)
            }
        }
        btnTrash4.setOnClickListener {
            kuantitas4 = 0
            findViewById<RelativeLayout>(R.id.pesanan_4).visibility = android.view.View.GONE
            updateTotalHarga(tvTotalPembayaran)
            Toast.makeText(this, "Americano Ice dihapus", Toast.LENGTH_SHORT).show()
        }

        // TOMBOL KONFIRMASI (PINDAH KE CHECKOUT)
        btnKonfirmasi.setOnClickListener {
            Toast.makeText(this, "Pesanan Dikonfirmasi!", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, CheckoutActivity::class.java)
            startActivity(intent)
        }
    }

    // FUNGSI MATEMATIKA TOTAL HARGA 4 PRODUK
    private fun updateTotalHarga(tvTotal: TextView) {
        val total = (kuantitas1 * hargaProduk1) +
                (kuantitas2 * hargaProduk2) +
                (kuantitas3 * hargaProduk3) +
                (kuantitas4 * hargaProduk4)

        // Output otomatis diformat dengan pemisah ribuan (Contoh: Rp 85.000)
        tvTotal.text = "Rp %,d".format(total).replace(',', '.')
    }
}