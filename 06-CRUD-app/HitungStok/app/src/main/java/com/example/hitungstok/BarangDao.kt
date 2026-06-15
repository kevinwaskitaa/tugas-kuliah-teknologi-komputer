package com.example.hitungstok

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface BarangDao {

    @Query("SELECT * FROM barang ORDER BY id DESC")
    fun getAll(): LiveData<List<Barang>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(barang: Barang): Long

    @Update
    suspend fun update(barang: Barang): Int

    @Delete
    suspend fun delete(barang: Barang): Int
}