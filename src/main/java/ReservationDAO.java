import java.sql.*;

public class ReservationDAO {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/nail_art_reservation";
    private static final String DB_USER = "root"; // Sesuaikan dengan username MySQL Anda
    private static final String DB_PASSWORD = ""; // Sesuaikan dengan password MySQL Anda

    // Metode untuk menyimpan reservasi
    public void saveReservation(String name, String phone, String service, String style, String schedule,
                                String paymentMethod, int totalPrice, String imageName) {
        String sql = "INSERT INTO reservations (name, phone, service, style, schedule, payment_method, total_price, image_name) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setString(3, service);
            pstmt.setString(4, style);
            pstmt.setString(5, schedule);
            pstmt.setString(6, paymentMethod);
            pstmt.setInt(7, totalPrice);
            pstmt.setString(8, imageName);

            pstmt.executeUpdate();
            System.out.println("Reservasi berhasil disimpan ke database.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Gagal menyimpan reservasi: " + e.getMessage());
        }
    }

    public void updateReservationStatus(String id, String newStatus) {
        String query = "UPDATE reservations SET status = ? WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, newStatus);
            statement.setString(2, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteReservation(String id) {
        String query = "DELETE FROM reservations WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, id);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
