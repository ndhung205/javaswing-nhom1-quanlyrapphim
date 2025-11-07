package gui;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DangKyGUI extends JFrame {

    private JTextField txtMaTK, txtMaNV, txtTenTK;
    private JPasswordField txtMatKhau;
    private JComboBox<String> cboVaiTro;
    private JButton btnDangKy, btnThoat;
    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public DangKyGUI() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Đăng ký tài khoản - Quản lý Rạp Chiếu Phim");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());

        // 🔹 Tiêu đề
        JLabel lblTitle = new JLabel("ĐĂNG KÝ TÀI KHOẢN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // 🔹 Panel nhập liệu
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Hàng 1: Mã tài khoản
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã tài khoản:"), gbc);
        gbc.gridx = 1;
        txtMaTK = new JTextField(20);
        formPanel.add(txtMaTK, gbc);

        // Hàng 2: Mã nhân viên
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Mã nhân viên:"), gbc);
        gbc.gridx = 1;
        txtMaNV = new JTextField(20);
        formPanel.add(txtMaNV, gbc);

        // Hàng 3: Tên đăng nhập
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridx = 1;
        txtTenTK = new JTextField(20);
        formPanel.add(txtTenTK, gbc);

        // Hàng 4: Mật khẩu
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridx = 1;
        txtMatKhau = new JPasswordField(20);
        formPanel.add(txtMatKhau, gbc);

        // Hàng 5: Vai trò
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Vai trò:"), gbc);
        gbc.gridx = 1;
        cboVaiTro = new JComboBox<>(new String[]{"NhanVien", "QuanLy", "Admin"});
        formPanel.add(cboVaiTro, gbc);

        add(formPanel, BorderLayout.CENTER);

        // 🔹 Panel nút bấm
        JPanel buttonPanel = new JPanel();
        btnDangKy = new JButton("Đăng ký");
        btnThoat = new JButton("Thoát");
        buttonPanel.add(btnDangKy);
        buttonPanel.add(btnThoat);
        add(buttonPanel, BorderLayout.SOUTH);

        // Sự kiện nút
        btnDangKy.addActionListener(e -> xuLyDangKy());
        btnThoat.addActionListener(e -> {
            dispose();
            new DangNhapGUI().setVisible(true);
        });
    }

    private void xuLyDangKy() {
        String maTK = txtMaTK.getText().trim();
        String maNV = txtMaNV.getText().trim();
        String tenTK = txtTenTK.getText().trim();
        String matKhau = new String(txtMatKhau.getPassword()).trim();
        String vaiTro = (String) cboVaiTro.getSelectedItem();

        if (maTK.isEmpty() || maNV.isEmpty() || tenTK.isEmpty() || matKhau.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Thiếu dữ liệu", JOptionPane.WARNING_MESSAGE);
            return;
        }

        TaiKhoan tk = new TaiKhoan(maTK, maNV, tenTK, matKhau, vaiTro, true);
        boolean ok = taiKhoanDAO.dangKy(tk);

        if (ok) {
            JOptionPane.showMessageDialog(this, "Đăng ký tài khoản thành công!");
            dispose();
            new DangNhapGUI().setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "Đăng ký thất bại! Kiểm tra lại thông tin.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DangKyGUI().setVisible(true));
    }
}
