# Universe — Aplikasi Manajemen Seminar

<p align="center">
  <img src="https://img.shields.io/badge/Platform-Android-green?style=for-the-badge&logo=android" />
  <img src="https://img.shields.io/badge/Language-Kotlin%20%2F%20Java-orange?style=for-the-badge&logo=kotlin" />
  <img src="https://img.shields.io/badge/Database-SQLite-blue?style=for-the-badge&logo=sqlite" />
  <img src="https://img.shields.io/badge/Status-Active-brightgreen?style=for-the-badge" />
</p>

---

## Tentang Aplikasi

**Universe** adalah aplikasi Android untuk manajemen seminar yang dapat diakses oleh dua jenis pengguna, yaitu **Admin** dan **Mahasiswa**.

- **Admin** dapat mengelola event seminar (tambah, edit, hapus)
- **Mahasiswa** dapat melihat daftar seminar yang tersedia dan mendaftarkan diri

---

## Fitur Utama

### Admin
| Fitur | Keterangan |
|-------|------------|
| Login Admin | Akses khusus dengan kredensial admin |
| Tambah Event | Membuat event seminar baru |
| Edit Event | Mengubah informasi event yang sudah ada |
| Hapus Event | Menghapus event dari sistem |
| Kelola Peserta | Melihat mahasiswa yang mendaftar |

### Mahasiswa
| Fitur | Keterangan |
|-------|------------|
| Register & Login | Daftar dan masuk sebagai mahasiswa |
| Lihat Event | Melihat daftar seminar yang tersedia |
| Detail Event | Melihat informasi lengkap seminar |
| Daftar Seminar | Mendaftarkan diri ke seminar |
| Riwayat | Melihat riwayat seminar yang pernah diikuti |

---

## Struktur Project

```
com.app.uts.universe
├── AdminActivity.kt          # Dashboard admin
├── MainActivity.kt           # Entry point / splash
├── HomeActivity.kt           # Halaman utama mahasiswa
├── RegisterActivity.kt       # Registrasi mahasiswa
├── DetailEventActivity.kt    # Detail informasi event
├── RiwayatActivity.kt        # Riwayat pendaftaran
├── Event.kt                  # Model data event
├── Riwayat.kt                # Model data riwayat
├── EventAdapter.kt           # Adapter list event
├── MahasiswaEventAdapter.kt  # Adapter event untuk mahasiswa
├── RiwayatAdapter.kt         # Adapter riwayat
└── DatabaseHelper.kt         # Helper SQLite database
```

---

## Teknologi yang Digunakan

- **Bahasa:** Kotlin / Java
- **Platform:** Android (Android Studio)
- **Database:** SQLite (lokal)
- **UI:** RecyclerView, CardView, Material Design
- **Min SDK:** 21 (Android 5.0 Lollipop)

---

## Cara Menjalankan

### Prasyarat
- Android Studio (versi terbaru)
- JDK 11 atau lebih baru
- Perangkat Android / Emulator (API 21+)

### Langkah-langkah

```bash
# 1. Clone repository
git clone https://github.com/Camii1i/P9-Mobile-RecyclerView-Mode-Grid-dan-Card-2410501123-Novry-Nanda

# 2. Buka project di Android Studio
File → Open → Pilih folder project

# 3. Sync Gradle
Klik "Sync Now" jika muncul notifikasi

# 4. Jalankan aplikasi
Klik tombol ▶️ Run atau Shift+F10
```

---

## Akun Default

| Role | Username | Password |
|------|----------|----------|
| Admin | admin | admin123 |

> Mahasiswa dapat membuat akun sendiri melalui fitur **Register**

---

## Screenshot

> *Coming soon...*

---

## Tim Pengembang

| Nama | NIM |
|------|-----|
| Novry Nanda | 2410501123 |
| Syakira Nensha | 2410501136 |
| Angel | 2410501132 |
| Zhaliqa | 2410501129 |
| Don Bosco | 2410501130 |

---

## Lisensi

Project ini dibuat untuk keperluan **UAS Mata Kuliah Pemrograman Mobile**  
Universitas Pembangunan Nasional Veteran Jakarta © 2025
