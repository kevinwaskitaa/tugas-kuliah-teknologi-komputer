package com.example.hitungstok

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var etNama: EditText
    private lateinit var etJumlah: EditText
    private lateinit var etHarga: EditText
    private lateinit var btnSimpan: Button
    private lateinit var rvBarang: RecyclerView
    private lateinit var adapter: BarangAdapter

    private val viewModel: BarangViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inisialisasi form input utama (Bagian A yang nempel di atas layar)
        etNama = findViewById(R.id.etNama)
        etJumlah = findViewById(R.id.etJumlah)
        etHarga = findViewById(R.id.etHarga)
        btnSimpan = findViewById(R.id.btnSimpan)
        rvBarang = findViewById(R.id.rvBarang)

        // Set up adapter
        adapter = BarangAdapter(
            listBarang = emptyList(),
            onEditClick = { barang ->
                etNama.setText(barang.nama)
                etJumlah.setText(barang.jumlah.toString())
                etHarga.setText(barang.harga.toString())
            },
            onDeleteClick = { barang ->
                viewModel.delete(barang)
            }
        )

        rvBarang.layoutManager = LinearLayoutManager(this)
        rvBarang.adapter = adapter

        // Ambil data database secara realtime
        viewModel.allBarang.observe(this, Observer { listBarang ->
            listBarang?.let { adapter.updateData(it) }
        })

        // Tombol simpan data
        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val jumlah = etJumlah.text.toString().toIntOrNull() ?: 0
            val harga = etHarga.text.toString().toIntOrNull() ?: 0

            if (nama.isNotEmpty()) {
                val barang = Barang(nama = nama, jumlah = jumlah, harga = harga)
                viewModel.insert(barang)

                etNama.text.clear()
                etJumlah.text.clear()
                etHarga.text.clear()
            }
        }
    }
}