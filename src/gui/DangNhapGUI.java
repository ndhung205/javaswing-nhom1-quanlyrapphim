package gui;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;

public class DangNhapGUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit, btnQuenMK;

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public DangNhapGUI() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Đăng nhập - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 450);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel bên trái (thương hiệu)
        JPanel brandPanel = new JPanel(new GridBagLayout());
        brandPanel.setBackground(new Color(30, 39, 73));
        brandPanel.setPreferredSize(new Dimension(280, 0));

        GridBagConstraints gbcBrand = new GridBagConstraints();
        gbcBrand.insets = new Insets(10, 10, 10, 10);
        gbcBrand.gridx = 0;

        JLabel lblIcon = new JLabel("🎬");
        lblIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
        lblIcon.setForeground(Color.WHITE);
        gbcBrand.gridy = 0;
        brandPanel.add(lblIcon, gbcBrand);

        JLabel lblTitle = new JLabel("QUẢN LÝ RẠP CHIẾU PHIM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        gbcBrand.gridy = 1;
        brandPanel.add(lblTitle, gbcBrand);

        add(brandPanel, BorderLayout.WEST);

        // Panel đăng nhập
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblLoginTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(lblLoginTitle, gbc);

        // Username
        gbc.gridy++;
        gbc.gridwidth = 1;
        loginPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        txtUsername = new JTextField(20);
        loginPanel.add(txtUsername, gbc);

        // Password
        gbc.gridy++;
        gbc.gridwidth = 1;
        loginPanel.add(new JLabel("Mật khẩu:"), gbc);
        gbc.gridy++;
        gbc.gridwidth = 2;
        txtPassword = new JPasswordField(20);
        loginPanel.add(txtPassword, gbc);

        // Nút Đăng nhập + Thoát
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.WHITE);
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");

        styleButton(btnLogin);
        styleButton(btnExit);

        panelBtn.add(btnLogin);
        panelBtn.add(btnExit);
        gbc.gridy++;
        loginPanel.add(panelBtn, gbc);

        // Quên mật khẩu
        JPanel panelExtra = new JPanel();
        panelExtra.setBackground(Color.WHITE);
        btnQuenMK = new JButton("Quên mật khẩu?");
        btnQuenMK.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btnQuenMK.setForeground(Color.BLUE);
        btnQuenMK.setBorderPainted(false);
        btnQuenMK.setContentAreaFilled(false);
        panelExtra.add(btnQuenMK);
        gbc.gridy++;
        loginPanel.add(panelExtra, gbc);

        add(loginPanel, BorderLayout.CENTER);

        // Sự kiện
        btnExit.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> xuLyDangNhap());
        btnQuenMK.addActionListener(e -> moQuenMatKhau());
    }

    private void styleButton(JButton btn) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(new Color(0, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(6, 15, 6, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private void xuLyDangNhap() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }

        TaiKhoan tk = taiKhoanDAO.dangNhap(user, pass);
        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!\nXin chào " + tk.getTenTK() + " (" + tk.getVaiTro() + ")");
            new MenuChinh(tk.getTenTK(), tk.getVaiTro()).setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Sai tên đăng nhập hoặc mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void moQuenMatKhau() {
        String username = JOptionPane.showInputDialog(this, "Nhập tên tài khoản để lấy lại mật khẩu:");
        if (username != null && !username.trim().isEmpty()) {
            String mk = taiKhoanDAO.layMatKhau(username.trim());
            if (mk != null)
                JOptionPane.showMessageDialog(this, "Mật khẩu của bạn là: " + mk);
            else
                JOptionPane.showMessageDialog(this, "Không tìm thấy tài khoản!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new DangNhapGUI().setVisible(true);
    }
}

