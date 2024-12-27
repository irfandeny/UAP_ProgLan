# Dokumentasi Proyek Reservasi Nail Art

## Deskripsi Proyek
Proyek ini adalah sebuah aplikasi reservasi jasa nail art yang dibangun menggunakan Java Swing. Aplikasi ini memungkinkan pelanggan untuk memesan layanan seperti manicure, pedicure, dan nail art dengan berbagai opsi desain, serta memungkinkan admin untuk mengelola data reservasi.

---

## Fitur Utama
1. *Login*
   - Sistem autentikasi untuk memastikan hanya pengguna yang terdaftar yang dapat mengakses aplikasi.

2. *Reservasi*
   - Pelanggan dapat memasukkan data seperti nama, nomor telepon, jenis layanan, desain, jadwal, metode pembayaran, dan gambar desain.

3. *Manajemen Data Reservasi*
   - Admin dapat menambah, menghapus, dan memperbarui status reservasi.
   - Data disimpan dalam database MySQL.

4. *Tabel Reservasi*
   - Menampilkan data reservasi dengan fitur CRUD (Create, Read, Update, Delete).

5. *Validasi Data*
   - Validasi input untuk memastikan semua data yang dimasukkan valid.

---

## Struktur Folder

'''
|-- src/
|   |-- NailArtReservation.java  # Kelas utama untuk frame reservasi.
|   |-- Main.java                # Kelas untuk memulai aplikasi.
|   |-- ReservationDAO.java      # Kelas untuk operasi database terkait reservasi.
|   |-- Login.java               # Kelas untuk frame login.
|-- resources/
|   |-- database.sql             # Skrip untuk membuat tabel database.
'''

---

## Cara Menggunakan
### 1. Persiapan
- Pastikan Java JDK terinstal.
- Pastikan MySQL Server aktif.
- Buat database dengan nama nail_art_reservation dan jalankan skrip database.sql yang tersedia.
- Sesuaikan *DB_URL, **DB_USER, dan **DB_PASSWORD* di kelas ReservationDAO sesuai konfigurasi lokal Anda.

### 2. Menjalankan Aplikasi
- Buka proyek ini di IDE seperti IntelliJ IDEA.
- Jalankan kelas Main.java.

### 3. Login
Gunakan kredensial yang telah dibuat di tabel users untuk login.

---

## Teknologi yang Digunakan
- *Java*: Untuk pengembangan aplikasi desktop.
- *Swing*: Untuk antarmuka pengguna.
- *MySQL*: Untuk penyimpanan data.
- *JDBC*: Untuk koneksi database.

---

## Catatan
- Format tanggal yang didukung adalah dd-MM-yyyy.
- Pastikan gambar desain yang diunggah berada di direktori yang dapat diakses.

---

## Kontak
Jika ada pertanyaan atau masalah, hubungi kami di:
- Email: support@nailartreservation.com
- Telepon: +62-123-4567-890
