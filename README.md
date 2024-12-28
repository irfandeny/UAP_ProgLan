# Dokumentasi Proyek Reservasi Nail Art

## Deskripsi Proyek

Proyek ini adalah sebuah aplikasi reservasi jasa nail art yang dibangun menggunakan Java Swing. Aplikasi ini memungkinkan pelanggan untuk memesan layanan seperti manicure, pedicure, dan nail art dengan berbagai opsi desain, serta memungkinkan admin untuk mengelola data reservasi.

---

## Fitur Utama

1. _Login_

   - Sistem autentikasi untuk memastikan hanya pengguna yang terdaftar yang dapat mengakses aplikasi.

2. _Reservasi_

   - Pelanggan dapat memasukkan data seperti nama, nomor telepon, jenis layanan, desain, jadwal, metode pembayaran, dan gambar desain.

3. _Manajemen Data Reservasi_

   - Admin dapat menambah, menghapus, dan memperbarui status reservasi.
   - Data disimpan dalam database MySQL.

4. _Tabel Reservasi_

   - Menampilkan data reservasi dengan fitur CRUD (Create, Read, Update, Delete).

5. _Validasi Data_
   - Validasi input untuk memastikan semua data yang dimasukkan valid.

---

## Struktur Folder

```puml
UAP_ProgLan [nail-art-reservation]
├── .idea/
├── src/
│   ├── main/
│   │   └── java/
│   │       ├── Login.java                  # Kelas untuk frame login.
│   │       ├── Main.java                   # Kelas untuk memulai aplikasi.
│   │       ├── NailArtReservation.java     # Kelas utama untuk frame reservasi.
│   │       └── ReservationDAO.java         # Kelas untuk operasi database terkait reservasi.
│   └── test/
│       └── java/
│           └── NailArtReservationTest.java # Kelas untuk unit test reservasi nail art.

```

---

## Cara Menggunakan

### 1. Persiapan

- Pastikan Java JDK terinstal.
- Pastikan MySQL Server aktif.
- Buat database dengan nama nail_art_reservation dan jalankan skrip database.sql yang tersedia.
- Sesuaikan **_DB_URL**, **DB_USER**, dan **DB_PASSWORD** di kelas ReservationDAO sesuai konfigurasi lokal Anda.

### 2. Menjalankan Aplikasi

- Buka proyek ini di IDE seperti IntelliJ IDEA.
- Jalankan kelas Main.java.

### 3. Login

Gunakan kredensial yang telah dibuat di tabel users untuk login.

---

## Teknologi yang Digunakan

- _Java_: Untuk pengembangan aplikasi desktop.
- _Swing_: Untuk antarmuka pengguna.
- _MySQL_: Untuk penyimpanan data.
- _JDBC_: Untuk koneksi database.

---

## Catatan

- Format tanggal yang didukung adalah dd-MM-yyyy.
- Pastikan gambar desain yang diunggah berada di direktori yang dapat diakses.

---

## Kontak

Jika ada pertanyaan atau masalah, hubungi kami di:

- Email: keysya.yesanty123@gmail.com
         irfandeny112@gmail.com
- Telepon: +62 852-8248-3177
           +62 878-0558-7738
