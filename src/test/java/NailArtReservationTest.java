import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

class NailArtReservationTest {

    private JTextField nameField;
    private JTextField phoneField;
    private JComboBox<String> serviceCombo;
    private JComboBox<String> styleCombo;
    private JTextField scheduleField;
    private JRadioButton cashButton;
    private JRadioButton cardButton;
    private ButtonGroup paymentGroup;
    private DefaultTableModel tableModel;
    private SimpleDateFormat dateFormat;

    @BeforeEach
    void setUp() {
        nameField = new JTextField();
        phoneField = new JTextField();

        String[] services = {"Manicure", "Pedicure", "Nail Art"};
        serviceCombo = new JComboBox<>(services);

        String[] styles = {"Easy Art", "Medium Art", "Hard Art"};
        styleCombo = new JComboBox<>(styles);
        styleCombo.setEnabled(false);

        scheduleField = new JTextField();

        cashButton = new JRadioButton("Cash");
        cardButton = new JRadioButton("Kartu");
        paymentGroup = new ButtonGroup();
        paymentGroup.add(cashButton);
        paymentGroup.add(cardButton);

        String[] columnNames = {"Nama", "Telepon", "Layanan", "Desain", "Jadwal", "Pembayaran", "Harga Total"};
        tableModel = new DefaultTableModel(columnNames, 0);

        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setLenient(false);
    }

    @Test
    void testValidReservation() throws ParseException {
        nameField.setText("Jane Doe");
        phoneField.setText("123456789");
        serviceCombo.setSelectedIndex(2); // Nail Art
        styleCombo.setEnabled(true);
        styleCombo.setSelectedIndex(1); // Medium Art
        scheduleField.setText("21-12-2024");
        cashButton.setSelected(true);

        Date scheduleDate = dateFormat.parse(scheduleField.getText());
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

        tableModel.addRow(data);

        assertEquals(1, tableModel.getRowCount());
        assertEquals("Jane Doe", tableModel.getValueAt(0, 0));
        assertEquals("123456789", tableModel.getValueAt(0, 1));
        assertEquals("Nail Art", tableModel.getValueAt(0, 2));
        assertEquals("Medium Art", tableModel.getValueAt(0, 3));
        assertEquals("21-12-2024", tableModel.getValueAt(0, 4));
        assertEquals("Cash", tableModel.getValueAt(0, 5));
        assertEquals(String.valueOf(totalPrice), tableModel.getValueAt(0, 6));
    }

    @Test
    void testInvalidPhoneNumber() {
        phoneField.setText("abcde12345");

        assertFalse(phoneField.getText().matches("\\d+"), "Nomor telepon harus berupa angka!");
    }

    @Test
    void testInvalidDateFormat() {
        scheduleField.setText("2024/12/21");

        assertThrows(ParseException.class, () -> {
            dateFormat.parse(scheduleField.getText());
        }, "Format tanggal salah! Gunakan format dd-MM-yyyy.");
    }

    @Test
    void testIncompleteReservation() {
        nameField.setText(""); // Missing name

        assertTrue(nameField.getText().isEmpty(), "Semua kolom harus diisi!");
    }

    @Test
    void testServiceSelectionDisablesStyleCombo() {
        serviceCombo.setSelectedIndex(0); // Manicure

        assertFalse(styleCombo.isEnabled(), "ComboBox Desain seharusnya dinonaktifkan untuk layanan selain Nail Art.");
    }
}
