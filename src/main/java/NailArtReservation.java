import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NailArtReservation {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reservasi Jasa Nail Art");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());

        // Panel Utama
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(240, 248, 255));

        ArrayList<String[]> reservations = new ArrayList<>();

        // Input Components
        JLabel nameLabel = new JLabel("Nama Pelanggan:");
        JTextField nameField = new JTextField();

        JLabel phoneLabel = new JLabel("Nomor Telepon:");
        JTextField phoneField = new JTextField();

        JLabel serviceLabel = new JLabel("Jenis Layanan:");
        String[] services = {"Manicure", "Pedicure", "Nail Art"};
        JComboBox<String> serviceCombo = new JComboBox<>(services);

        JLabel styleLabel = new JLabel("Gaya Desain:");
        String[] styles = {"Easy Art", "Medium Art", "Hard Art"};
        JComboBox<String> styleCombo = new JComboBox<>(styles);
        styleCombo.setEnabled(false);

        JLabel scheduleLabel = new JLabel("Jadwal (dd-MM-yyyy):");
        JTextField scheduleField = new JTextField();

        JLabel paymentLabel = new JLabel("Metode Pembayaran:");
        JRadioButton cashButton = new JRadioButton("Cash");
        JRadioButton cardButton = new JRadioButton("Kartu");

        // Set ActionCommand untuk tombol pembayaran
        cashButton.setActionCommand("Cash");
        cardButton.setActionCommand("Kartu");

        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashButton);
        paymentGroup.add(cardButton);

        JLabel imageLabel = new JLabel("Gambar Desain:");
        JButton uploadButton = new JButton("Upload Gambar");
        JLabel imagePreview = new JLabel();

        JButton submitButton = new JButton("Reservasi");
        JButton clearButton = new JButton("Reset");
        JButton updateStatusButton = new JButton("Ubah Status");
        JButton deleteButton = new JButton("Hapus Pesanan");

        // Tabel untuk CRUD
        String[] columnNames = {"Nama", "Telepon", "Layanan", "Desain", "Jadwal", "Pembayaran", "Harga Total", "Gambar", "Status"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable reservationTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(reservationTable);

        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 5, 5));
        inputPanel.setBackground(new Color(252, 225, 228));
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(phoneLabel);
        inputPanel.add(phoneField);
        inputPanel.add(serviceLabel);
        inputPanel.add(serviceCombo);
        inputPanel.add(styleLabel);
        inputPanel.add(styleCombo);
        inputPanel.add(scheduleLabel);
        inputPanel.add(scheduleField);
        inputPanel.add(paymentLabel);
        inputPanel.add(cashButton);
        inputPanel.add(new JLabel());
        inputPanel.add(cardButton);
        inputPanel.add(imageLabel);
        inputPanel.add(uploadButton);
        inputPanel.add(submitButton);
        inputPanel.add(clearButton);
        inputPanel.add(updateStatusButton);
        inputPanel.add(deleteButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(mainPanel);

        //tombol hapus
        deleteButton.addActionListener(e -> {
            int selectedRow = reservationTable.getSelectedRow();
            if (selectedRow >= 0) {
                int confirmation = JOptionPane.showConfirmDialog(
                        frame, "Apakah Anda yakin ingin menghapus pesanan ini?", "Konfirmasi",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirmation == JOptionPane.YES_OPTION) {
                    String reservationId = (String) tableModel.getValueAt(selectedRow, 0); // ID reservasi
                    ReservationDAO reservationDAO = new ReservationDAO();
                    reservationDAO.deleteReservation(reservationId);

                    tableModel.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(frame, "Pesanan berhasil dihapus!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang ingin dihapus!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        //update status
        updateStatusButton.addActionListener(e -> {
            int selectedRow = reservationTable.getSelectedRow();
            if (selectedRow >= 0) {
                String currentStatus = (String) tableModel.getValueAt(selectedRow, 8); // Kolom Status
                String[] statusOptions = {"Pending", "Confirmed", "Canceled"};
                String newStatus = (String) JOptionPane.showInputDialog(
                        frame, "Pilih Status Baru:", "Ubah Status",
                        JOptionPane.QUESTION_MESSAGE, null, statusOptions, currentStatus
                );

                if (newStatus != null && !newStatus.equals(currentStatus)) {
                    tableModel.setValueAt(newStatus, selectedRow, 8);

                    // Update status di database
                    String reservationId = (String) tableModel.getValueAt(selectedRow, 0); // ID reservasi
                    ReservationDAO reservationDAO = new ReservationDAO();
                    reservationDAO.updateReservationStatus(reservationId, newStatus);

                    JOptionPane.showMessageDialog(frame, "Status berhasil diubah!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(frame, "Pilih pesanan yang ingin diubah statusnya!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Upload Gambar
        uploadButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(frame);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                imagePreview.setText(selectedFile.getName());
            }
        });

        // Logika enable/disable styleCombo
        serviceCombo.addActionListener(e -> {
            String selectedService = (String) serviceCombo.getSelectedItem();
            if (selectedService != null && selectedService.contains("Nail Art")) {
                styleCombo.setEnabled(true);
            } else {
                styleCombo.setEnabled(false);
                styleCombo.setSelectedIndex(0);
            }
        });

        // Action untuk tombol submit
        // Di dalam submitButton ActionListener
        submitButton.addActionListener(e -> {
            try {
                if (!validateInput(nameField, phoneField, scheduleField, paymentGroup, frame)) return;

                String name = nameField.getText();
                String phone = phoneField.getText();
                String service = (String) serviceCombo.getSelectedItem();
                String style = styleCombo.isEnabled() ? (String) styleCombo.getSelectedItem() : "-";
                String payment = paymentGroup.getSelection().getActionCommand();
                String schedule = scheduleField.getText(); // Jadwal dalam format dd-MM-yyyy

                // Debugging output
                System.out.println("Nama: " + name);
                System.out.println("Telepon: " + phone);
                System.out.println("Service: " + service);
                System.out.println("Style: " + style);
                System.out.println("Payment: " + payment);
                System.out.println("Schedule: " + schedule);

                // Mengonversi schedule ke format yang benar untuk MySQL (yyyy-MM-dd)
                Date parsedDate = DATE_FORMAT.parse(schedule);
                String formattedSchedule = new SimpleDateFormat("yyyy-MM-dd").format(parsedDate);

                int totalPrice = calculatePrice(service, style);
                String imageName = imagePreview.getText();

                // Simpan ke dalam database atau lakukan proses lainnya
                ReservationDAO reservationDAO = new ReservationDAO();
                reservationDAO.saveReservation(name, phone, service, style, formattedSchedule, payment, totalPrice, imageName);

                String status = "Pending";
                String[] data = {name, phone, service, style, formattedSchedule, payment, String.valueOf(totalPrice), imageName, status};
                reservations.add(data);
                tableModel.addRow(data);

                JOptionPane.showMessageDialog(frame, "Reservasi Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();  // Debugging output
                JOptionPane.showMessageDialog(frame, "Terjadi kesalahan: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        // Action untuk tombol reset
        clearButton.addActionListener(e -> clearFields(
                nameField, phoneField, scheduleField, serviceCombo, styleCombo, paymentGroup, imagePreview
        ));

        frame.setVisible(true);
    }

    // Validasi Input
    private static boolean validateInput(JTextField nameField, JTextField phoneField, JTextField scheduleField,
                                         ButtonGroup paymentGroup, JFrame frame) {
        if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || scheduleField.getText().isEmpty() ||
                paymentGroup.getSelection() == null) {
            JOptionPane.showMessageDialog(frame, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (!phoneField.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String schedule = scheduleField.getText();
        if (schedule == null || schedule.trim().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Jadwal harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            DATE_FORMAT.setLenient(false);
            Date date = DATE_FORMAT.parse(schedule);
            schedule = new SimpleDateFormat("yyyy-MM-dd").format(date); // Ubah format ke MySQL format
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(frame, "Format tanggal salah! Gunakan format dd-MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        return true;
    }

    // Hitung Harga Total
    private static int calculatePrice(String service, String style) {
        int totalPrice = 0;
        if (service.contains("Manicure")) {
            totalPrice += 100000;
        } else if (service.contains("Pedicure")) {
            totalPrice += 150000;
        } else if (service.contains("Nail Art")) {
            if (style.contains("Easy Art")) {
                totalPrice += 50000;
            } else if (style.contains("Medium Art")) {
                totalPrice += 150000;
            } else if (style.contains("Hard Art")) {
                totalPrice += 200000;
            }
        }
        return totalPrice;
    }

    // Reset Fields
    private static void clearFields(JTextField nameField, JTextField phoneField, JTextField scheduleField,
                                    JComboBox<String> serviceCombo, JComboBox<String> styleCombo,
                                    ButtonGroup paymentGroup, JLabel imagePreview) {
        nameField.setText("");
        phoneField.setText("");
        scheduleField.setText("");
        serviceCombo.setSelectedIndex(0);
        styleCombo.setSelectedIndex(0);
        styleCombo.setEnabled(false);
        paymentGroup.clearSelection();
        imagePreview.setText("");
    }
}
