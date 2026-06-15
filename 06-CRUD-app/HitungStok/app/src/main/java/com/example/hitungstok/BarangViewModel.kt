package com.example.hitungstok

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class BarangViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: BarangRepository
    val allBarang: LiveData<List<Barang>>

    init {
        val dao = AppDatabase.getDatabase(application).barangDao()
        repository = BarangRepository(dao)
        allBarang = repository.allBarang
    }

    fun insert(barang: Barang) = viewModelScope.launch {
        repository.insert(barang)
    }

    fun update(barang: Barang) = viewModelScope.launch {
        repository.update(barang)
    }

    fun delete(barang: Barang) = viewModelScope.launch {
        repository.delete(barang)
    }
}