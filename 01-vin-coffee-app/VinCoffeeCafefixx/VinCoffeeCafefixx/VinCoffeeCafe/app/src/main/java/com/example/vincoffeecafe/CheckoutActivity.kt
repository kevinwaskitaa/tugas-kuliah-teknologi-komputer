package com.example.vincoffeecafe

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class CheckoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        supportActionBar?.hide()

        val btnBack = findViewById<ImageView>(R.id.btn_back_checkout)
        val btnBayar = findViewById<Button>(R.id.btn_bayar_sekarang)

        btnBack.setOnClickListener {
            finish() // Kembali ke Halaman Keranjang
        }

        btnBayar.setOnClickListener {
            showSuccessDialog()
        }
    }

    private fun showSuccessDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Pembayaran Berhasil! 🎉")
        builder.setMessage("Terima kasih telah melakukan pemesanan di Vin Coffee & Cafe.\n\nSilahkan buka halaman struk resmi untuk mengunduh nota transaksi digital Anda dalam bentuk PNG atau membagikannya ke WhatsApp.")

        // Mengarahkan pengguna ke StrukActivity untuk download PNG
        builder.setPositiveButton("Lihat Struk Resmi") { dialog, _ ->
            dialog.dismiss()

            val intent = Intent(this, StrukActivity::class.java)
            // FLAG CRITICAL: Menghapus jejak history halaman Checkout agar aman dari tumpukan memori RAM
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        builder.setCancelable(false)

        val alertDialog = builder.create()
        alertDialog.show()
    }
}