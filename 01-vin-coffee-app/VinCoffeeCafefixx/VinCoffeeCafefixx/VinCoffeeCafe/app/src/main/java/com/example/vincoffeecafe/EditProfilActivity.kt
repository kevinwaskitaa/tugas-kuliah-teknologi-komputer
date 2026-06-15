package com.example.vincoffeecafe

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditProfilActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profil)
        supportActionBar?.hide()

        val btnBack = findViewById<ImageView>(R.id.btn_back_edit)
        val etNama = findViewById<EditText>(R.id.et_edit_nama)
        val etEmail = findViewById<EditText>(R.id.et_edit_email)
        val btnSimpan = findViewById<Button>(R.id.btn_simpan_profil)

        // Buka database SharedPreferences lokal bernama "SesiUser"
        val sharedPref = getSharedPreferences("SesiUser", Context.MODE_PRIVATE)

        // Tampilkan data yang saat ini sedang tersimpan di kolom input (Default: Keysha)
        etNama.setText(sharedPref.getString("nama_user", "Keysha"))
        etEmail.setText(sharedPref.getString("email_user", "keysha123@gmail.com"))

        btnBack.setOnClickListener { finish() }

        // Aksi saat tombol SIMPAN diklik
        btnSimpan.setOnClickListener {
            val namaBaru = etNama.text.toString().trim()
            val emailBaru = etEmail.text.toString().trim()

            if (namaBaru.isNotEmpty() && emailBaru.isNotEmpty()) {
                // Proses penyimpanan ke database lokal
                val editor = sharedPref.edit()
                editor.putString("nama_user", namaBaru)
                editor.putString("email_user", emailBaru)
                editor.apply() // Terapkan perubahan

                Toast.makeText(this, "Profil Berhasil Diperbarui!", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman sebelumnya
            } else {
                Toast.makeText(this, "Data tidak boleh kosong!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}