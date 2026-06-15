package com.example.vincoffeecafe

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import android.content.Intent
import android.graphics.Color

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        val chipCoffee = findViewById<TextView>(R.id.chip_coffee)
        val chipNonCoffee = findViewById<TextView>(R.id.chip_non_coffee)
        val chipSnack = findViewById<TextView>(R.id.chip_snack)
        val ivBannerPromo = findViewById<ImageView>(R.id.iv_banner_promo)

        val navHome = findViewById<ImageView>(R.id.nav_home)
        val navMaps = findViewById<ImageView>(R.id.nav_maps)
        val navVouchers = findViewById<ImageView>(R.id.nav_vouchers)
        val navProfile = findViewById<ImageView>(R.id.nav_profile)

        val cardMenu1 = findViewById<CardView>(R.id.card_menu_1)
        val cardMenu2 = findViewById<CardView>(R.id.card_menu_2)
        val etSearchCoffee = findViewById<EditText>(R.id.et_search_coffee)

        updateChipSelection(chipCoffee, chipNonCoffee, chipSnack)

        // LOGIKA KLIK CHIP KATEGORI & ADAPTASI BANNER DINAMIS (REVISI)
        chipCoffee.setOnClickListener {
            updateChipSelection(chipCoffee, chipNonCoffee, chipSnack)
            ivBannerPromo.setImageResource(R.drawable.kopi_home)
            Toast.makeText(this, "Menampilkan Menu Kopi", Toast.LENGTH_SHORT).show()
        }

        chipNonCoffee.setOnClickListener {
            updateChipSelection(chipNonCoffee, chipCoffee, chipSnack)
            ivBannerPromo.setImageResource(R.drawable.kopi_hitam)
            Toast.makeText(this, "Menampilkan Menu Non-Kopi", Toast.LENGTH_SHORT).show()
        }

        chipSnack.setOnClickListener {
            updateChipSelection(chipSnack, chipCoffee, chipNonCoffee)
            ivBannerPromo.setImageResource(R.drawable.resource_new)
            Toast.makeText(this, "Menampilkan Menu Snack", Toast.LENGTH_SHORT).show()
        }

        // ==================== KOORDINASI NAVIGASI NAVBAR BAWAH ====================
        navHome.setOnClickListener {
            Toast.makeText(this, "Anda sedang di Beranda", Toast.LENGTH_SHORT).show()
        }

        navMaps.setOnClickListener {
            val intent = Intent(this, LokasiOutletActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        navVouchers.setOnClickListener {
            val intent = Intent(this, VoucherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        cardMenu1.setOnClickListener {
            val intent = Intent(this, DetailProdukActivity::class.java)
            startActivity(intent)
        }

        cardMenu2.setOnClickListener {
            val intent = Intent(this, DetailProdukActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateChipSelection(selected: TextView, unselected1: TextView, unselected2: TextView) {
        try {
            selected.setBackgroundResource(R.drawable.bg_chip_selected)
            unselected1.setBackgroundResource(R.drawable.bg_chip_normal)
            unselected2.setBackgroundResource(R.drawable.bg_chip_normal)
        } catch (e: Exception) {
            selected.setBackgroundColor(Color.BLUE)
            unselected1.setBackgroundColor(Color.LTGRAY)
            unselected2.setBackgroundColor(Color.LTGRAY)
        }

        selected.setTextColor(Color.parseColor("#FFFFFF"))
        selected.setTypeface(null, android.graphics.Typeface.BOLD)

        unselected1.setTextColor(Color.parseColor("#757575"))
        unselected1.setTypeface(null, android.graphics.Typeface.NORMAL)

        unselected2.setTextColor(Color.parseColor("#757575"))
        unselected2.setTypeface(null, android.graphics.Typeface.NORMAL)
    }
}