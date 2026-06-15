package com.example.hitungstok

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class BarangAdapter(
    private var listBarang: List<Barang>,
    private val onEditClick: (Barang) -> Unit,
    private val onDeleteClick: (Barang) -> Unit
) : RecyclerView.Adapter<BarangAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Menghubungkan komponen pembungkus utama dari activity_main.xml
        val formInput: View = itemView.findViewById(R.id.layout_form_input)
        val itemCard: CardView = itemView.findViewById(R.id.layout_item_card)

        // Menghubungkan komponen teks & tombol yang ada di dalam objek kartu
        val tvNama: TextView = itemView.findViewById(R.id.tvNama)
        val tvJumlah: TextView = itemView.findViewById(R.id.tvJumlah)
        val tvHarga: TextView = itemView.findViewById(R.id.tvHarga)
        val btnEdit: ImageView = itemView.findViewById(R.id.btnEdit)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Tetap melakukan inflate ke activity_main sesuai permintaan Anda
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val barang = listBarang[position]

        // TRIK UTAMA: Sembunyikan bagian form input, lalu munculkan bentuk kartu belanjaan
        holder.formInput.visibility = View.GONE
        holder.itemCard.visibility = View.VISIBLE

        // Mengisi data model barang ke dalam komponen TextView di dalam kartu
        holder.tvNama.text = barang.nama
        holder.tvJumlah.text = "Jumlah: ${barang.jumlah}"
        holder.tvHarga.text = "Harga: ${barang.harga}"

        // Mengatur aksi ketika tombol ikon Pensil (Edit) ditekan
        holder.btnEdit.setOnClickListener {
            onEditClick(barang)
        }

        // Mengatur aksi ketika tombol ikon Tempat Sampah (Delete) ditekan
        holder.btnDelete.setOnClickListener {
            onDeleteClick(barang)
        }
    }

    override fun getItemCount(): Int = listBarang.size

    // Fungsi helper untuk memperbarui daftar data secara dinamis dari MainActivity
    fun updateData(newList: List<Barang>) {
        this.listBarang = newList
        notifyDataSetChanged()
    }
}