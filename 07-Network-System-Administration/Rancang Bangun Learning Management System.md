# Rancang Bangun Learning Management System (LMS) Moodle Berbasis Ubuntu Server 20.04 LTS & Enterprise Network Gateway

## 📌 Deskripsi Proyek
Repositori ini berisi dokumentasi teknis komprehensif, arsitektur topologi, dan berkas konfigurasi esensial dari proyek implementasi infrastruktur jaringan terintegrasi. Proyek ini menggabungkan kontrol gateway **MikroTik RouterOS**, sistem manajemen jaringan nirkabel enterprise menggunakan **Ubiquiti UniFi**, serta deployment penuh aplikasi **LMS Moodle** di atas fondasi sistem operasi **Ubuntu Server 20.04 LTS** menggunakan arsitektur **LAMP Stack**.

Tujuan utama dari proyek laboratorium ini adalah menciptakan ekosistem server pembelajaran digital lokal (*e-learning*) yang stabil, aman, memiliki jangkauan distribusi sinyal nirkabel yang luas, serta didukung oleh alokasi IP dinamis yang otomatis bagi seluruh perangkat klien.

---

## 🛠️ Spesifikasi Perangkat & Perangkat Lunak
- **Core Router Gateway:** MikroTik Routerboard RB951Ui-2nd
- **Access Point Wireless:** Ubiquiti AP AC Lite
- **Controller Software:** UniFi Network Server v9.5.21
- **Hypervisor Environment:** VMware Workstation Pro
- **Server OS:** Ubuntu Server 20.04 LTS
- **Technology Web Stack:** LAMP Stack (Apache2, MariaDB 10.3, PHP 7.4)
- **Application Platform:** Moodle Core v3.8+

---

## 📐 Topologi Infrastruktur Jaringan (Star Topology)
Jaringan diimplementasikan menggunakan **Topologi Star** terpusat untuk mempermudah pemantauan jalur data tunggal dan isolasi penanganan masalah (*troubleshooting*):

```text
                     [ ISP / INTERNET ]
                              │
                              ▼ (Ether1 - DHCP Client)
                    ┌──────────────────┐
                    │ MIKROTIK ROUTER  │◄─── (Winbox Management)
                    └─┬──────────────┬─┘
                      │ (Ether2)     │ (Ether3)
                      ▼              ▼
               ┌──────────────┐┌──────────────┐
               │UBUNTU SERVER ││ UBIQUITI AP  │
               │ (LAMP + LMS) ││   AC LITE    │
               └──────────────┘└──────┬───────┘
                                      │
                                      ▼ (Wireless SSID: POLITAMA)
                              [ SMARTPHONE CLIENT ]
```

---

## 🚀 Panduan Langkah Demi Langkah Deployment Sistem

### BAB I: Konfigurasi Jaringan & Layanan DHCP Server
1. **Instalasi Paket DHCP:** Menginstal paket `isc-dhcp-server` pada Ubuntu Server untuk mengotomatisasi distribusi IP ke klien.
   ```bash
   sudo apt update && sudo apt install isc-dhcp-server -y
   ```
2. **Konfigurasi Interface Jaringan:** Mengatur kartu jaringan internal (`ens34`) agar memancarkan segmen IP statis melalui Netplan di `/etc/netplan/00-installer-config.yaml`.
3. **Konfigurasi DHCP Daemon:** Mengedit berkas `/etc/dhcp/dhcpd.conf` untuk menentukan *range* alokasi IP dinamis, netmask, DNS, dan gateway default.
4. **Membuka Akses Jarak Jauh (SSH):** Mengaktifkan `ssh.service` agar administrasi server dapat dikendalikan dari jarak jauh menggunakan aplikasi PuTTY secara aman terenkripsi.

### BAB II: Deployment LAMP Stack (Linux, Apache, MySQL/MariaDB, PHP)
1. **Web Server Apache2:** Mengonfigurasi web server utama untuk melayani *traffic* HTTP data aplikasi web.
   ```bash
   sudo apt install apache2 -y
   ```
2. **Database Engine MariaDB:** Menginstal sistem manajemen database relasional.
   ```bash
   sudo apt install mariadb-server mariadb-client -y
   ```
3. **Runtime PHP 7.4:** Menginstal PHP beserta seluruh modul ekstensi pendukung wajib Moodle seperti `php-xml`, `php-gd`, `php-mysql`, `php-intl`, dan `php-mbstring`.

### BAB III: Instalasi & Konfigurasi Platform LMS Moodle
1. **Download Source Code:** Mengunduh dan mengekstrak berkas arsip Moodle Core ke direktori dokumen web server `/var/www/html/moodle`.
2. **Direktori Data Khusus:** Membuat folder `/var/www/moodledata` di luar direktori web publik demi alasan keamanan penyimpanan data pengguna.
3. **Setup Database Relasional:** Membuat database bernama `politama` dengan format encoding `utf8mb4_unicode_ci` melalui SQL Engine MariaDB.
4. **Alokasi Hak Akses (Permissions):** Menyerahkan kepemilikan penuh folder aplikasi kepada pengguna *runtime* web server (`www-data`).

---

## ⚙️ Berkas Konfigurasi Esensial

### 1. Jaringan Lokal & IP Statis Server (`/etc/netplan/00-installer-config.yaml`)
```yaml
network:
  ethernets:
    ens33:
      dhcp4: true
    ens34:
      dhcp4: false
      addresses: [192.168.50.1/24]
  version: 2
```

### 2. Layanan Distribusi IP Dinamis (`/etc/dhcp/dhcpd.conf`)
```text
subnet 192.168.50.0 netmask 255.255.255.0 {
  range 192.168.50.3 192.168.50.100;
  option domain-name-servers 192.168.50.1;
  option domain-name "politama.com";
  option subnet-mask 255.255.255.0;
  option routers 192.168.50.1;
  option broadcast-address 192.168.50.254;
  default-lease-time 600;
  max-lease-time 7200;
}
```

### 3. Inisialisasi Basis Data Relasional (`MariaDB Engine`)
```sql
CREATE DATABASE politama DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; 
CREATE USER 'kevin'@'localhost' IDENTIFIED BY 'kevin';
GRANT ALL PRIVILEGES ON politama.* TO 'kevin'@'localhost';
FLUSH PRIVILEGES;
EXIT;
```

---

## 🔐 Implementasi Pengerasan Keamanan (Security Hardening)
1. **Remote Shell Encryption:** Jalur kendali jarak jauh diproteksi menggunakan protokol kriptografi aman via `ssh.service` untuk mencegah penyadapan data teks mentah (*plaintext*).
2. **Database Hardening:** Mengeksekusi utilitas skrip keamanan `mysql_secure_installation` untuk menghapus akun *anonymous user*, memblokir akses login root jarak jauh, serta membersihkan database uji bawaan.
3. **Directory Access Control Isolation:** Mengunci hak akses sistem file direktori aplikasi web agar tidak dapat dieksploitasi oleh pihak luar menggunakan perintah:
   ```bash
   sudo chown -R www-data:www-data /var/www/html/moodle
   sudo chmod -R 755 /var/www/html/moodle
   sudo chown -R www-data:www-data /var/www/moodledata
   ```

---

## 📈 Hasil Pengujian & Validasi Sistem
- **Konektivitas Gateway MikroTik:** Berhasil menerapkan metode *NAT Masquerade* sehingga seluruh interface lokal sukses melakukan interkoneksi ping penuh ke internet luar.
- **Adopsi Perangkat Ubiquiti AP AC Lite:** Titik akses nirkabel enterprise teradopsi 100% (*Provisioned*) pada perangkat lunak UniFi Network Server dan sukses memancarkan sinyal Wi-Fi.
- **Validasi Alokasi IP Klien:** Perangkat laptop/smartphone klien terbukti berhasil mendapatkan sewa alamat IP dinamis secara otomatis dari server DHCP Ubuntu.
- **Fungsionalitas Web LMS Moodle:** Halaman aplikasi web berjalan dengan sangat responsif saat diakses melalui peramban (*browser*) klien dan siap digunakan untuk kebutuhan kelas digital.

---
*Dokumentasi komprehensif ini disusun oleh **Kevin Andrian Dwi Putra Waskita** sebagai representasi bukti kompetensi teknis di bidang Administrasi Server Jaringan dan Infrastruktur IT.*
