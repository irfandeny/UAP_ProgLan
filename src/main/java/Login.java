import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login {
    public void showLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLayout(new GridLayout(4, 1));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        loginFrame.add(usernameLabel);
        loginFrame.add(usernameField);
        loginFrame.add(passwordLabel);
        loginFrame.add(passwordField);
        loginFrame.add(loginButton);

        loginFrame.setVisible(true);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (validateLogin(username, password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                loginFrame.dispose();
                NailArtReservation.showReservationFrame();
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Login gagal! Periksa username dan password Anda.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        try (Connection connection = DriverManager.getConnection("jdbc:mysql://172.16.251.37:3306/nail_art_reservation", "keysya", "key011223@");
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {

            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
