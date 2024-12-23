import java.sql.*;

public class ReservationDAO {

    // Konfigurasi database
    private static final String URL = "jdbc:mysql://localhost:3306/reservasi";
    private static final String USER = "root"; // Ganti jika menggunakan user lain
    private static final String PASSWORD = "";  // Ganti jika ada password

    // Metode untuk menghubungkan ke database
    private static Connection connect() throws SQLException {
        try {
            // Memastikan Driver MySQL terdaftar
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke database
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver MySQL tidak ditemukan.", e);
        }
    }

    // Metode untuk menyimpan reservasi ke database
    public void saveReservation(String name, String phone, String service, String style, String schedule,
                                String payment, int totalPrice, String imageName) throws SQLException {
        // Konversi schedule (String) menjadi java.sql.Date
        java.sql.Date sqlDate = java.sql.Date.valueOf(schedule);

        String sql = "INSERT INTO reservations (name, phone, service, style, schedule, payment, total_price, image_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        // Menggunakan try-with-resources untuk menutup koneksi secara otomatis
        try (Connection connection = connect(); // Mendapatkan koneksi dari metode connect
             PreparedStatement statement = connection.prepareStatement(sql)) {
            // Menyiapkan data untuk disisipkan ke dalam query
            statement.setString(1, name);
            statement.setString(2, phone);
            statement.setString(3, service);
            statement.setString(4, style);
            statement.setDate(5, sqlDate);
            statement.setString(6, payment);
            statement.setInt(7, totalPrice);
            statement.setString(8, imageName);

            // Menjalankan query untuk menyimpan data
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new SQLException("Gagal menyimpan reservasi ke database.", e);
        }
    }

    public static void main(String[] args) {
        // Contoh penggunaan metode saveReservation
        try {
            ReservationDAO dao = new ReservationDAO();
            dao.saveReservation("John Doe", "123456789", "Manicure", "Classic", "2025-12-02", "Cash", 150000, "manicure_image.jpg");
            System.out.println("Reservasi berhasil disimpan.");
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
