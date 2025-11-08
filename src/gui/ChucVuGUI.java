package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class ChucVuGUI extends JPanel {

    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMa, txtTen;

    public ChucVuGUI() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ======= NORTH: TIÊU ĐỀ =======
        JLabel lblTitle = new JLabel("QUẢN LÝ CHỨC VỤ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(new Color(0, 102, 204));
        add(lblTitle, BorderLayout.NORTH);

        // ======= CENTER: BẢNG HIỂN THỊ =======
        String[] cols = {"Mã chức vụ", "Tên chức vụ"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ======= SOUTH: FORM + BUTTON =======
        JPanel pnlSouth = new JPanel(new BorderLayout());
        pnlSouth.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlForm.add(new JLabel("Mã chức vụ:"));
        txtMa = new JTextField();
        pnlForm.add(txtMa);

        pnlForm.add(new JLabel("Tên chức vụ:"));
        txtTen = new JTextField();
        pnlForm.add(txtTen);

        pnlSouth.add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnThem = new JButton("Thêm");
        JButton btnXoa = new JButton("Xóa");
        JButton btnSua = new JButton("Sửa");
        JButton btnTaiLai = new JButton("Tải lại");

        pnlButton.add(btnThem);
        pnlButton.add(btnSua);
        pnlButton.add(btnXoa);
        pnlButton.add(btnTaiLai);

        pnlSouth.add(pnlButton, BorderLayout.SOUTH);
        add(pnlSouth, BorderLayout.SOUTH);

        // ======= EVENT =======
        btnThem.addActionListener(e -> themChucVu());
        btnXoa.addActionListener(e -> xoaChucVu());
        btnSua.addActionListener(e -> suaChucVu());
        btnTaiLai.addActionListener(e -> taiLai());

        // Thêm dữ liệu mẫu
        model.addRow(new Object[]{"CV01", "Quản lý"});
        model.addRow(new Object[]{"CV02", "Nhân viên bán vé"});
        model.addRow(new Object[]{"CV03", "Nhân viên kỹ thuật"});
    }

    // ======= CÁC HÀM XỬ LÝ =======
    private void themChucVu() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();

        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        model.addRow(new Object[]{ma, ten});
        JOptionPane.showMessageDialog(this, "Thêm chức vụ thành công!");
    }

    private void xoaChucVu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần xóa!");
            return;
        }
        model.removeRow(row);
        JOptionPane.showMessageDialog(this, "Xóa thành công!");
    }

    private void suaChucVu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chọn dòng cần sửa!");
            return;
        }

        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        if (ma.isEmpty() || ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không được để trống!");
            return;
        }

        model.setValueAt(ma, row, 0);
        model.setValueAt(ten, row, 1);
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
    }

    private void taiLai() {
        txtMa.setText("");
        txtTen.setText("");
        table.clearSelection();
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý chức vụ");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(950, 600);
            frame.setLocationRelativeTo(null);
            frame.setContentPane(new ChucVuGUI());
            frame.setVisible(true);
        });
    }
}
