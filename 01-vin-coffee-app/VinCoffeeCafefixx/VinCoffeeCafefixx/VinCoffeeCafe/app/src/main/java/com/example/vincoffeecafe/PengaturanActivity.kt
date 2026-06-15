package com.example.vincoffeecafe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SwitchCompat

class PengaturanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengaturan)
        supportActionBar?.hide()

        val sharedPref = getSharedPreferences("SesiUser", Context.MODE_PRIVATE)

        // Inisialisasi ID Komponen Baru
        val btnBack = findViewById<ImageView>(R.id.btn_back_pengaturan)
        val btnEditProfil = findViewById<RelativeLayout>(R.id.btn_edit_profil)
        val btnGantiPassword = findViewById<RelativeLayout>(R.id.btn_ganti_password)
        val btnLokasiKedai = findViewById<RelativeLayout>(R.id.btn_lokasi_kedai)
        val btnKebijakan = findViewById<RelativeLayout>(R.id.btn_kebijakan_privasi)
        val btnHapusCache = findViewById<RelativeLayout>(R.id.btn_hapus_cache)

        val switchHematData = findViewById<SwitchCompat>(R.id.switch_hemat_data)
        val switchPromo = findViewById<SwitchCompat>(R.id.switch_notif_promo)

        // Set status awal switch dari penyimpanan lokal agar tidak reset
        switchHematData?.isChecked = sharedPref.getBoolean("status_hemat", false)
        switchPromo?.isChecked = sharedPref.getBoolean("status_promo", true)

        // 1. Tombol Back Kembali ke Profile secara aman
        btnBack?.setOnClickListener {
            finish()
        }

        // 2. Edit Profil Nyata (Membuka Halaman Edit)
        btnEditProfil?.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }

        // 3. Ganti Password (Simulasi Pop-up)
        btnGantiPassword?.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Ubah Keamanan")
                .setMessage("Link konfirmasi perubahan password akun berhasil dikirimkan ke email: keysha123@gmail.com")
                .setPositiveButton("Selesai", null)
                .show()
        }

        // 4. SWITCH HEMAT DATA (Berfungsi Menyimpan Status)
        switchHematData?.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref.edit()
            editor.putBoolean("status_hemat", isChecked)
            editor.apply()

            val teksInfo = if (isChecked) "Mode hemat data aktif. Gambar menu dioptimalkan." else "Mode standar aktif."
            Toast.makeText(this, teksInfo, Toast.LENGTH_SHORT).show()
        }

        // 5. FITUR LOKASI KEDAI (Membuka Google Maps Nyata lewat Intent)
        btnLokasiKedai?.setOnClickListener {
            // Koordinat Google Maps Kedai (Contoh: Monas Jakarta, silakan ganti dengan koordinat tokomu jika mau)
            val latitude = "-6.175392"
            val longitude = "106.827153"
            val labelLokasi = "Vin Coffee & Cafe"

            val gmapsUri = Uri.parse("geo:$latitude,$longitude?q=" + Uri.encode(labelLokasi))
            val mapIntent = Intent(Intent.ACTION_VIEW, gmapsUri)
            mapIntent.setPackage("com.google.android.apps.maps") // Paksa buka pakai aplikasi Google Maps resmi

            if (mapIntent.resolveActivity(packageManager) != null) {
                startActivity(mapIntent)
            } else {
                // Jika aplikasi Gmaps tidak ada, buka lewat browser HP
                val webMapsUri = Uri.parse("https://www.google.com/maps/search/?api=1&query=$latitude,$longitude")
                startActivity(Intent(Intent.ACTION_VIEW, webMapsUri))
            }
        }

        // 6. Kebijakan Privasi
        btnKebijakan?.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Kebijakan Privasi")
                .setMessage("Aplikasi Vin Coffee berkomitmen menjaga kerahasiaan data transaksi, nomor WhatsApp, serta informasi autentikasi pelanggan.")
                .setPositiveButton("Tutup", null)
                .show()
        }

        // 7. Bersihkan Cache
        btnHapusCache?.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Bersihkan Data Cache")
                .setMessage("Apakah kamu ingin menghapus data sementara aplikasi? Langkah ini akan membebaskan 12.4 MB ruang penyimpanan.")
                .setPositiveButton("Hapus") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(this, "Cache berhasil dibersihkan!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Batal", null)
                .show()
        }

        // Switch Promo
        switchPromo?.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPref.edit()
            editor.putBoolean("status_promo", isChecked)
            editor.apply()
        }
    }
}