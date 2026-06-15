package com.example.vincoffeecafe

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.net.URLEncoder

class ProfileActivity : AppCompatActivity() {

    // Deklarasi variabel teks komponen profil agar bisa diakses secara dinamis dari onResume
    private lateinit var tvNamaProfil: TextView
    private lateinit var tvEmailProfil: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        supportActionBar?.hide()

        // Inisialisasi komponen teks header profil dari XML
        tvNamaProfil = findViewById(R.id.tv_profile_name)
        tvEmailProfil = findViewById<TextView>(R.id.tv_profile_email)

        // 1. Inisialisasi Tombol Navigasi Atas & Bawah
        val btnBack = findViewById<ImageView>(R.id.btn_back)
        val navHome = findViewById<ImageView>(R.id.nav_home)

        // 2. Inisialisasi Komponen Daftar Menu Opsi Vertikal sesuai ID XML kamu
        val menuRiwayat = findViewById<LinearLayout>(R.id.menu_riwayat)
        val menuPembayaran = findViewById<LinearLayout>(R.id.menu_pembayaran)
        val menuVoucher = findViewById<LinearLayout>(R.id.menu_voucher)
        val menuPengaturan = findViewById<LinearLayout>(R.id.menu_pengaturan)
        val menuBantuan = findViewById<LinearLayout>(R.id.menu_bantuan)
        val menuInfo = findViewById<LinearLayout>(R.id.menu_info)
        val menuKeluar = findViewById<LinearLayout>(R.id.menu_keluar)

        // ================= LOGIKA AKSI KLIK =================

        // Tombol Back (Panah Kiri Atas) -> Kembali ke halaman sebelumnya
        btnBack.setOnClickListener {
            finish()
        }

        // Ikon Home di Bottom Navbar -> Kembali ke HomeActivity
        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // 1. MENU RIWAYAT PESANAN (Membuka halaman Struk Resmi terakhir)
        menuRiwayat.setOnClickListener {
            Toast.makeText(this, "Membuka Struk Transaksi Terakhir", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, StrukActivity::class.java)
            startActivity(intent)
        }

        // 2. MENU METODE PEMBAYARAN (Memunculkan pilihan simulasi Pop-up)
        menuPembayaran.setOnClickListener {
            val opsiMetode = arrayOf("Dana (Default)", "OVO", "GoPay")
            AlertDialog.Builder(this)
                .setTitle("Metode Pembayaran Digital")
                .setItems(opsiMetode) { _, posisi ->
                    Toast.makeText(
                        this,
                        "Metode Utama Diubah ke: ${opsiMetode[posisi]}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .setPositiveButton("Tutup", null)
                .show()
        }

        // 3. MENU VOUCHER SAYA (Membuka Halaman VoucherActivity)
        menuVoucher.setOnClickListener {
            Toast.makeText(this, "Membuka Voucher Kamu 🎟️", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, VoucherActivity::class.java)
            startActivity(intent)
        }

        // 4. MENU PENGATURAN (Membuka Halaman PengaturanActivity Baru yang Kompleks)
        menuPengaturan.setOnClickListener {
            val intent = Intent(this, PengaturanActivity::class.java)
            startActivity(intent)
        }

        // 5. PUSAT BANTUAN (Mengarahkan otomatis ke chat WhatsApp pribadi kamu)
        menuBantuan.setOnClickListener {
            val nomorTujuanWa = "6281998234704" // Format internasional tanpa angka 0 di depan
            val drafPesan =
                "*Halo Layanan Pelanggan Vin Coffee,*\nSaya butuh bantuan informasi teknis mengenai aplikasi/pesanan."

            try {
                // Encode teks agar aman dilempar via URL web protocol
                val urlIntent =
                    "https://api.whatsapp.com/send?phone=$nomorTujuanWa&text=" + URLEncoder.encode(
                        drafPesan,
                        "UTF-8"
                    )
                val kirimWaIntent = Intent(Intent.ACTION_VIEW)
                kirimWaIntent.data = Uri.parse(urlIntent)
                kirimWaIntent.setPackage("com.whatsapp") // Memaksa sistem membuka aplikasi resmi WhatsApp

                if (kirimWaIntent.resolveActivity(packageManager) != null) {
                    startActivity(kirimWaIntent)
                } else {
                    // Jika WA utama tidak terinstall, lempar via browser web / WA Business
                    startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(urlIntent)))
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Gagal membuka aplikasi WhatsApp", Toast.LENGTH_SHORT).show()
            }
        }

        // 6. INFO APLIKASI (Pop-up Detail Info Proyek Kuliah/Tugas)
        menuInfo.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Informasi Sistem")
                .setMessage("Nama Aplikasi: Vin Coffee App\nVersi: 1.0.4\n\nDikembangkan oleh: Kevin Andrian\nProyek Tugas Akhir D3 Teknologi Komputer.\n\nAll Rights Reserved © 2026.")
                .setPositiveButton("Selesai", null)
                .show()
        }

        // 7. MENU KELUAR (Konfirmasi Pop-up & Reset Sesi ke Halaman Login)
        menuKeluar.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Konfirmasi Keluar")
                .setMessage("Apakah Anda yakin ingin keluar?")
                .setPositiveButton("Ya, Keluar") { dialog, _ ->
                    dialog.dismiss()
                    Toast.makeText(this, "Sesi Berakhir", Toast.LENGTH_SHORT).show()

                    // Kembali ke LoginActivity dan bersihkan tumpukan halaman dashboard dari memori RAM
                    val intentLogOut = Intent(this, LoginActivity::class.java)
                    intentLogOut.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intentLogOut)
                    finish()
                }
                .setNegativeButton("Batal") { dialog, _ ->
                    dialog.dismiss()
                }
                .setCancelable(false)
                .show()
        }
    }

    // ================= SIKLUS HIDUP REFRESH DATA OTOMATIS =================
    override fun onResume() {
        super.onResume()

        // Buka database SharedPreferences lokal
        val sharedPref = getSharedPreferences("SesiUser", Context.MODE_PRIVATE)

        // Render ulang teks nama & email sesuai data modifikasi terakhir di Edit Profil
        tvNamaProfil.text = sharedPref.getString("nama_user", "Keysha")
        tvEmailProfil.text = sharedPref.getString("email_user", "keysha123@gmail.com")
    }
}