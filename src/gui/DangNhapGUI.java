

package gui;

import dao.TaiKhoanDAO;
import entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DangNhapGUI extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit, btnDangKy, btnQuenMK;

    private TaiKhoanDAO taiKhoanDAO = new TaiKhoanDAO();

    public DangNhapGUI() {
        initComponents();
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        setTitle("Đăng nhập - Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 500);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel trái
        JPanel brandPanel = new JPanel(new GridBagLayout());
        brandPanel.setBackground(new Color(33, 37, 41));
        brandPanel.setPreferredSize(new Dimension(300, 0));
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

        // Panel phải
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblLoginTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(lblLoginTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        JLabel lblUser = new JLabel("Tên đăng nhập:");
        loginPanel.add(lblUser, gbc);

        gbc.gridy = 2;
        gbc.gridwidth = 2;
        txtUsername = new JTextField(20);
        loginPanel.add(txtUsername, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        JLabel lblPass = new JLabel("Mật khẩu:");
        loginPanel.add(lblPass, gbc);

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        txtPassword = new JPasswordField(20);
        loginPanel.add(txtPassword, gbc);

        // Nút bấm
        JPanel panelBtn = new JPanel();
        panelBtn.setBackground(Color.WHITE);
        btnLogin = new JButton("Đăng nhập");
        btnExit = new JButton("Thoát");
        panelBtn.add(btnLogin);
        panelBtn.add(btnExit);
        gbc.gridy = 5;
        loginPanel.add(panelBtn, gbc);

        // Đăng ký + Quên mật khẩu
        JPanel panelExtra = new JPanel();
        panelExtra.setBackground(Color.WHITE);
        btnDangKy = new JButton("Đăng ký tài khoản");
        btnQuenMK = new JButton("Quên mật khẩu?");
        panelExtra.add(btnDangKy);
        panelExtra.add(btnQuenMK);
        gbc.gridy = 6;
        loginPanel.add(panelExtra, gbc);

        add(loginPanel, BorderLayout.CENTER);

        // Hành động
        btnExit.addActionListener(e -> System.exit(0));
        btnLogin.addActionListener(e -> xuLyDangNhap());
        btnDangKy.addActionListener(e -> moDangKy());
        btnQuenMK.addActionListener(e -> moQuenMatKhau());
    }

    private void xuLyDangNhap() {
        String user = txtUsername.getText().trim();
        String pass = new String(txtPassword.getPassword()).trim();

        TaiKhoan tk = taiKhoanDAO.dangNhap(user, pass);
        if (tk != null) {
            JOptionPane.showMessageDialog(this, "Đăng nhập thành công!\nXin chào " + tk.getTenTK() + " (" + tk.getVaiTro() + ")");
            new MenuChinh(tk.getVaiTro()).setVisible(true);
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
    private void moDangKy() {
        new DangKyGUI().setVisible(true);
        dispose();
    }


    public static void main(String[] args) {
        new DangNhapGUI().setVisible(true);
    }
}
