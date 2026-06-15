package com.example.vincoffeecafe

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LokasiOutletActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mapView: MapView
    private var googleMap: GoogleMap? = null

    // Launcher untuk meminta izin lokasi
    private val requestLocationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineLocationGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        val coarseLocationGranted = permissions[Manifest.permission.ACCESS_COARSE_LOCATION] ?: false

        if (fineLocationGranted || coarseLocationGranted) {
            enableMyLocationOnMap()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lokasi_outlet)
        supportActionBar?.hide()

        // 1. Inisialisasi MapView dan panggil onCreate bawaannya
        mapView = findViewById(R.id.mv_peta_outlet)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        // Inisialisasi tombol kontrol atas dan rute
        val btnBack = findViewById<ImageView>(R.id.btn_back_lokasi)
        val btnRute1 = findViewById<Button>(R.id.btn_rute_1)

        // Inisialisasi komponen lengkap Navbar Bawah
        val navHome = findViewById<ImageView>(R.id.nav_home)
        val navMaps = findViewById<ImageView>(R.id.nav_maps)
        val navVouchers = findViewById<ImageView>(R.id.nav_vouchers)
        val navProfile = findViewById<ImageView>(R.id.nav_profile)

        btnBack.setOnClickListener { finish() }

        btnRute1.setOnClickListener {
            Toast.makeText(this, "Membuka Navigasi Rute...", Toast.LENGTH_SHORT).show()
        }

        // ==================== NAVIGASI NAVBAR BAWAH ====================

        // 1. KEMBALI KE BERANDA
        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0) // Transisi mulus tanpa kedipan
            finish() // Tutup activity agar hemat memori RAM laptop
        }

        // Karena ini halaman Lokasi, cukup beri indikasi Toast jika disentuh kembali
        navMaps.setOnClickListener {
            Toast.makeText(this, "Anda sedang melihat peta lokasi outlet", Toast.LENGTH_SHORT).show()
        }

        // 2. PINDAH KE HALAMAN VOUCHER (VoucherActivity)
        navVouchers.setOnClickListener {
            val intent = Intent(this, VoucherActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        // 3. PINDAH KE HALAMAN PROFIL (ProfileActivity)
        navProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
            finish()
        }

        checkAndRequestLocationPermissions()
    }

    private fun checkAndRequestLocationPermissions() {
        val fineLocationCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
        val coarseLocationCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)

        if (fineLocationCheck == PackageManager.PERMISSION_GRANTED || coarseLocationCheck == PackageManager.PERMISSION_GRANTED) {
            enableMyLocationOnMap()
        } else {
            requestLocationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun enableMyLocationOnMap() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            googleMap?.isMyLocationEnabled = true
        }
    }

    // 2. Fungsi pemicu saat peta siap render grafis buminya
    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Titik Koordinat Cabang Mojokerto
        val lokasiMojokerto = LatLng(-7.4726, 112.4381)
        googleMap?.addMarker(
            MarkerOptions()
                .position(lokasiMojokerto)
                .title("Vin Coffee - Cabang Mojokerto")
                .snippet("Buka: 09:00 - 22:00 WIB")
        )
        googleMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiMojokerto, 15f))
        googleMap?.uiSettings?.isZoomControlsEnabled = true

        enableMyLocationOnMap()
    }

    // 3. Meneruskan siklus hidup activity ke MapView agar grafisnya ter-render
    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}