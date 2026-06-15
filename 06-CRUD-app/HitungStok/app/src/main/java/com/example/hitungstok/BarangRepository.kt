package com.example.hitungstok

import androidx.lifecycle.LiveData

class BarangRepository(private val dao: BarangDao) {
    val allBarang: LiveData<List<Barang>> = dao.getAll()
    suspend fun insert(barang: Barang) {dao.insert(barang)}
    suspend fun update(barang: Barang) {dao.update(barang)}
    suspend fun delete(barang: Barang) {dao.delete(barang)}
}