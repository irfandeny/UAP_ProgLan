import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class Login {
    public void showLoginFrame() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(400, 300);
        loginFrame.setLocationRelativeTo(null);

        JPanel mainpanel = new JPanel();
        mainpanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        loginFrame.add(mainpanel);

        JLabel titleLabel = new JLabel("Login",SwingConstants.CENTER);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 24));

        JLabel usernameLabel = new JLabel("Username :");
        usernameLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JTextField usernameField = new JTextField(20);
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setFont(new Font("Times New Roman", Font.BOLD, 18));
        JPasswordField passwordField = new JPasswordField();

        JButton loginButton = new JButton("Login");

        Dimension labelSize = new Dimension(100, 25);
        usernameLabel.setPreferredSize(labelSize);
        passwordLabel.setPreferredSize(labelSize);

        GroupLayout layout = new GroupLayout(mainpanel);
        mainpanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                        .addComponent(titleLabel)
                        .addGroup(
                                layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                                                .addComponent(usernameLabel)
                                                .addComponent(passwordLabel))
                                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                .addComponent(usernameField)
                                                .addComponent(passwordField))
                        )
                        .addComponent(loginButton)
        );


        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(usernameLabel)
                                        .addComponent(usernameField)
                        )
                        .addGroup(
                                layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(passwordLabel)
                                        .addComponent(passwordField)
                        )
                        .addComponent(loginButton)
        );


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
