import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class NailArtReservation {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Reservasi Jasa Nail Art");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(255, 228, 225));

        ArrayList<String[]> reservations = new ArrayList<>();

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
        ButtonGroup paymentGroup = new ButtonGroup();
        paymentGroup.add(cashButton);
        paymentGroup.add(cardButton);

        JButton submitButton = new JButton("Reservasi");
        JButton clearButton = new JButton("Reset");

        String[] columnNames = {"Nama", "Telepon", "Layanan", "Desain", "Jadwal", "Pembayaran", "Harga Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable reservationTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(reservationTable);

        JPanel inputPanel = new JPanel(new GridLayout(8, 2, 5, 5));
        inputPanel.setBackground(new Color(255, 228, 225));
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
        inputPanel.add(submitButton);
        inputPanel.add(clearButton);

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(tableScrollPane, BorderLayout.CENTER);

        frame.add(mainPanel);

        serviceCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedService = (String) serviceCombo.getSelectedItem();
                if (selectedService != null && selectedService.contains("Nail Art")) {
                    styleCombo.setEnabled(true);
                } else {
                    styleCombo.setEnabled(false);
                    styleCombo.setSelectedIndex(0);
                }
            }
        });

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (nameField.getText().isEmpty() || phoneField.getText().isEmpty() || scheduleField.getText().isEmpty() ||
                        (!cashButton.isSelected() && !cardButton.isSelected())) {
                    JOptionPane.showMessageDialog(frame, "Semua kolom harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (!phoneField.getText().matches("\\d+")) {
                    JOptionPane.showMessageDialog(frame, "Nomor telepon harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String scheduleText = scheduleField.getText();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    dateFormat.setLenient(false);
                    try {
                        Date scheduleDate = dateFormat.parse(scheduleText);
                        String name = nameField.getText();
                        String phone = phoneField.getText();
                        String service = (String) serviceCombo.getSelectedItem();
                        String style = styleCombo.isEnabled() ? (String) styleCombo.getSelectedItem() : "-";
                        String payment = cashButton.isSelected() ? "Cash" : "Kartu";

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

                        String[] data = {name, phone, service, style, dateFormat.format(scheduleDate), payment, String.valueOf(totalPrice)};
                        reservations.add(data);

                        tableModel.addRow(data);

                        JOptionPane.showMessageDialog(frame, "Reservasi Berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(frame, "Format tanggal salah! Gunakan format dd-MM-yyyy.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nameField.setText("");
                phoneField.setText("");
                scheduleField.setText("");
                serviceCombo.setSelectedIndex(0);
                styleCombo.setSelectedIndex(0);
                styleCombo.setEnabled(false);
                paymentGroup.clearSelection();
            }
        });
        frame.setVisible(true);
    }
}
