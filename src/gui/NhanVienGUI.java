package gui;

import entity.NhanVien;
import entity.ChucVu;
import dao.NhanVienDAO;
import dao.ChucVuDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVienGUI extends JPanel {
    private JTextField txtMaNV, txtTenNV, txtSDT, txtEmail, txtNgayVaoLam;
    private JComboBox<ChucVu> cboChucVu;
    private JTable table;
    private DefaultTableModel model;
    private NhanVienDAO nvDAO = new NhanVienDAO();
    private ChucVuDAO cvDAO = new ChucVuDAO();

    public NhanVienGUI() {
        setLayout(new BorderLayout(10, 10));

        // ===== PANEL NHẬP LIỆU =====
        JPanel pnlTop = new JPanel(new GridLayout(3, 4, 10, 10));
        pnlTop.setBorder(BorderFactory.createTitledBorder("Thông tin nhân viên"));

        pnlTop.add(new JLabel("Mã NV:"));
        txtMaNV = new JTextField();
        pnlTop.add(txtMaNV);

        pnlTop.add(new JLabel("Tên NV:"));
        txtTenNV = new JTextField();
        pnlTop.add(txtTenNV);

        pnlTop.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        pnlTop.add(txtSDT);

        pnlTop.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        pnlTop.add(txtEmail);

        pnlTop.add(new JLabel("Chức vụ:"));
        cboChucVu = new JComboBox<>();
        pnlTop.add(cboChucVu);

        pnlTop.add(new JLabel("Ngày vào làm (yyyy-MM-dd):"));
        txtNgayVaoLam = new JTextField();
        pnlTop.add(txtNgayVaoLam);

        add(pnlTop, BorderLayout.NORTH);

        // ===== BẢNG =====
        String[] columns = {"Mã NV", "Tên NV", "SĐT", "Email", "Mã Chức vụ", "Ngày vào làm"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ===== NÚT =====
        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        // ===== SỰ KIỆN =====
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnLamMoi.addActionListener(e -> loadData());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaNV.setText(model.getValueAt(row, 0).toString());
                    txtTenNV.setText(model.getValueAt(row, 1).toString());
                    txtSDT.setText(model.getValueAt(row, 2).toString());
                    txtEmail.setText(model.getValueAt(row, 3).toString());
                    String maCV = model.getValueAt(row, 4).toString();
                    cboChucVu.setSelectedItem(findChucVuByMa(maCV));
                    txtNgayVaoLam.setText(model.getValueAt(row, 5) != null ? model.getValueAt(row, 5).toString() : "");
                }
            }
        });

        // Load dữ liệu
        loadChucVu();
        loadData();
    }

    private void loadChucVu() {
        cboChucVu.removeAllItems();
        ArrayList<ChucVu> dsCV = cvDAO.getAllChucVu();
        for (ChucVu cv : dsCV) {
            cboChucVu.addItem(cv);
        }
    }

    private void loadData() {
        model.setRowCount(0);
        ArrayList<NhanVien> ds = nvDAO.getAllNhanVien();
        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNV(),
                    nv.getHoTen(),
                    nv.getsDT(),
                    nv.getEmail(),
                    nv.getChucVu() != null ? nv.getChucVu().getMaChucVu() : "",
                    nv.getNgayVaoLam()
            });
        }
    }

    private void themNhanVien() {
        try {
            ChucVu cv = (ChucVu) cboChucVu.getSelectedItem();
            NhanVien nv = new NhanVien(
                    txtMaNV.getText(),
                    txtTenNV.getText(),
                    txtSDT.getText(),
                    txtEmail.getText(),
                    cv,
                    txtNgayVaoLam.getText().isEmpty() ? null : LocalDate.parse(txtNgayVaoLam.getText())
            );

            if (nvDAO.addNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Thêm thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể thêm (trùng mã hoặc lỗi SQL).");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void suaNhanVien() {
        try {
            ChucVu cv = (ChucVu) cboChucVu.getSelectedItem();
            NhanVien nv = new NhanVien(
                    txtMaNV.getText(),
                    txtTenNV.getText(),
                    txtSDT.getText(),
                    txtEmail.getText(),
                    cv,
                    txtNgayVaoLam.getText().isEmpty() ? null : LocalDate.parse(txtNgayVaoLam.getText())
            );

            if (nvDAO.updateNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể cập nhật (mã không tồn tại?).");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa.");
            return;
        }

        String ma = model.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this, "Xóa nhân viên " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (nvDAO.removeNhanVien(ma)) {
                JOptionPane.showMessageDialog(this, "Đã xóa!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa (lỗi SQL).");
            }
        }
    }

    private ChucVu findChucVuByMa(String ma) {
        for (int i = 0; i < cboChucVu.getItemCount(); i++) {
            ChucVu cv = cboChucVu.getItemAt(i);
            if (cv.getMaChucVu().equals(ma)) return cv;
        }
        return null;
    }

    // ===== TEST CHẠY =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Quản lý nhân viên");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 500);
            frame.add(new NhanVienGUI());
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
