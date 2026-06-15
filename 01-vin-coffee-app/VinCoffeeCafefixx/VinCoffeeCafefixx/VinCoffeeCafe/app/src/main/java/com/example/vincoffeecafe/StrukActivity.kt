package com.example.vincoffeecafe

import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream
import java.net.URLEncoder

class StrukActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_struk)
        supportActionBar?.hide()

        val layoutStrukKonten = findViewById<LinearLayout>(R.id.layout_struk_konten)
        val btnDownloadPng = findViewById<Button>(R.id.btn_download_png)
        val btnKirimWa = findViewById<Button>(R.id.btn_kirim_wa)
        val btnKembaliHome = findViewById<Button>(R.id.btn_kembali_home)

        // 1. Aksi Tombol Download PNG
        btnDownloadPng.setOnClickListener {
            val bitmapStruk = getScreenShotFromView(layoutStrukKonten)
            if (bitmapStruk != null) {
                simpanKeGaleri(bitmapStruk)
            } else {
                Toast.makeText(this, "Gagal memproses gambar struk", Toast.LENGTH_SHORT).show()
            }
        }

        // 2. Aksi Tombol Kirim Teks Struk ke WhatsApp
        btnKirimWa.setOnClickListener {
            val nomorWa = "628XXXXXXXXXX" // Ganti dengan nomor WA tujuan kasir/pelanggan
            val pesanStruk = """
                *VIN COFFEE & CAFE*
                =========================
                No. Pesanan : #VIN98231
                Pelanggan   : Keysha
                =========================
                - 2x Cappucino Torabika (Rp 40.000)
                - 1x Croissant Coklat   (Rp 15.000)
                =========================
                *TOTAL BAYAR : Rp 55.000*
                Status      : LUNAS / BERHASIL
                
                Terima kasih sudah ngopi di Vin Coffee!
            """.trimIndent()

            try {
                val packageManager = packageManager
                val i = Intent(Intent.ACTION_VIEW)
                val url = "https://api.whatsapp.com/send?phone=$nomorWa&text=" + URLEncoder.encode(pesanStruk, "UTF-8")
                i.setPackage("com.whatsapp")
                i.data = Uri.parse(url)
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                } else {
                    // Jika aplikasi WA tidak terinstall, buka lewat browser bawaan
                    val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(browserIntent)
                }
            } catch (e: Exception) {
                Toast.makeText(this, "WhatsApp tidak terinstall atau terjadi kesalahan", Toast.LENGTH_SHORT).show()
            }
        }

        // 3. Aksi Tombol Kembali ke Beranda Utama
        btnKembaliHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            // Clear task agar menumpuk antrean halaman lama dan kembali bersih ke dashboard
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
            finish()
        }
    }

    // Fungsi Pengonversi XML ke Bitmap Gambar
    private fun getScreenShotFromView(v: View): Bitmap? {
        var screenshot: Bitmap? = null
        try {
            screenshot = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(screenshot)
            v.draw(canvas)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenshot
    }

    // Fungsi Penyimpan File Gambar ke internal MediaStore Pictures
    private fun simpanKeGaleri(bitmap: Bitmap) {
        val namaFile = "Struk_VinCoffee_${System.currentTimeMillis()}.png"
        var fos: OutputStream? = null
        var imageUri: Uri? = null

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, namaFile)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Pictures/VinCoffeeStruk")
            }
        }

        try {
            imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (imageUri != null) {
                fos = contentResolver.openOutputStream(imageUri)
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos!!)
                fos.flush()
                fos.close()
                Toast.makeText(this, "Struk PNG berhasil disimpan ke Galeri HP!", Toast.LENGTH_LONG).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal mengunduh gambar struk", Toast.LENGTH_SHORT).show()
        }
    }
}