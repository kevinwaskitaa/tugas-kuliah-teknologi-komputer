package com.example.hitungstok

import androidx.room.Entity
import androidx.room.PrimaryKey

// Anotasi @Entity memberi tahu Room bahwa ini adalah tabel database
@Entity(tableName = "barang")
data class Barang(
    // Primary Key autoGenerate agar ID bertambah otomatis (1, 2, 3...)
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val nama: String,
    val jumlah: Int,
    val harga: Int
)