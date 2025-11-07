package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DangNhapGUI extends JFrame {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin, btnExit;

    private static final String MOCK_USERNAME = "admin";
    private static final String MOCK_PASSWORD = "123456";

    private static final Color COLOR_BRAND_BACKGROUND = new Color(33, 37, 41);
    private static final Color COLOR_FORM_BACKGROUND = Color.WHITE;
    private static final Color COLOR_PRIMARY = new Color(0, 123, 255);
    private static final Color COLOR_PRIMARY_HOVER = new Color(0, 100, 220);
    private static final Color COLOR_DANGER = new Color(220, 53, 69);
    private static final Color COLOR_DANGER_HOVER = new Color(200, 30, 40);
    private static final Color COLOR_TEXT_FIELD = new Color(51, 51, 51);
    private static final Color COLOR_PLACEHOLDER = Color.GRAY;
    private static final Font FONT_LABEL = new Font("Arial", Font.BOLD, 14);
    private static final Font FONT_FIELD = new Font("Arial", Font.PLAIN, 16);
    private static final Font FONT_BUTTON = new Font("Arial", Font.BOLD, 14);

    private static final String PLACEHOLDER_USERNAME = "(admin)";
    private static final String PLACEHOLDER_PASSWORD = "(123456)";

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
        brandPanel.setBackground(COLOR_BRAND_BACKGROUND);
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

        JLabel lblSubtitle = new JLabel("(Mock version)");
        lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 12));
        lblSubtitle.setForeground(Color.LIGHT_GRAY);
        gbcBrand.gridy = 2;
        brandPanel.add(lblSubtitle, gbcBrand);

        add(brandPanel, BorderLayout.WEST);

        // Panel phải - Form
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(COLOR_FORM_BACKGROUND);
        loginPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblLoginTitle = new JLabel("ĐĂNG NHẬP HỆ THỐNG");
        lblLoginTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblLoginTitle.setForeground(COLOR_TEXT_FIELD);
        lblLoginTitle.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        loginPanel.add(lblLoginTitle, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 2;
        JLabel lblUsername = new JLabel("Tên đăng nhập:");
        lblUsername.setFont(FONT_LABEL);
        loginPanel.add(lblUsername, gbc);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        txtUsername = new JTextField(20);
        txtUsername.setFont(FONT_FIELD);
        txtUsername.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        addPlaceholder(txtUsername, PLACEHOLDER_USERNAME);
        loginPanel.add(txtUsername, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 4;
        JLabel lblPassword = new JLabel("Mật khẩu:");
        lblPassword.setFont(FONT_LABEL);
        loginPanel.add(lblPassword, gbc);

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        txtPassword = new JPasswordField(20);
        txtPassword.setFont(FONT_FIELD);
        txtPassword.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        addPlaceholder(txtPassword, PLACEHOLDER_PASSWORD);
        loginPanel.add(txtPassword, gbc);

        // Nút bấm
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(COLOR_FORM_BACKGROUND);
        GridBagConstraints gbcBtn = new GridBagConstraints();
        gbcBtn.fill = GridBagConstraints.HORIZONTAL;
        gbcBtn.insets = new Insets(5, 5, 5, 5);

        btnLogin = new JButton("Đăng nhập");
        styleButton(btnLogin, COLOR_PRIMARY, COLOR_PRIMARY_HOVER, Color.WHITE);
        btnLogin.addActionListener(e -> handleLogin());
        gbcBtn.gridx = 0;
        buttonPanel.add(btnLogin, gbcBtn);

        btnExit = new JButton("Thoát");
        styleButton(btnExit, COLOR_DANGER, COLOR_DANGER_HOVER, Color.WHITE);
        btnExit.addActionListener(e -> System.exit(0));
        gbcBtn.gridx = 1;
        buttonPanel.add(btnExit, gbcBtn);

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        loginPanel.add(buttonPanel, gbc);

        add(loginPanel, BorderLayout.CENTER);

        // ENTER để đăng nhập
        KeyAdapter enterLoginAdapter = new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        };
        txtUsername.addKeyListener(enterLoginAdapter);
        txtPassword.addKeyListener(enterLoginAdapter);
    }

    private void styleButton(JButton button, Color bg, Color bgHover, Color fg) {
        button.setBackground(bg);
        button.setForeground(fg);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgHover);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bg);
            }
        });
    }

    private void addPlaceholder(JTextField field, String placeholder) {
        JPasswordField passField = (field instanceof JPasswordField) ? (JPasswordField) field : null;
        char echoChar = (passField != null) ? passField.getEchoChar() : 0;

        field.setText(placeholder);
        field.setForeground(COLOR_PLACEHOLDER);
        if (passField != null) passField.setEchoChar((char) 0);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getForeground() == COLOR_PLACEHOLDER) {
                    field.setText("");
                    field.setForeground(COLOR_TEXT_FIELD);
                    if (passField != null) passField.setEchoChar(echoChar);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                String text = (passField != null)
                        ? new String(passField.getPassword())
                        : field.getText();
                if (text.isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(COLOR_PLACEHOLDER);
                    if (passField != null) passField.setEchoChar((char) 0);
                }
            }
        });
    }

    // ✅ Xử lý đăng nhập mock hoàn chỉnh (giống bản test)
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword()).trim();

        // Kiểm tra trống hoặc placeholder
        if (username.isEmpty() || username.equals(PLACEHOLDER_USERNAME)
                || password.isEmpty() || password.equals(PLACEHOLDER_PASSWORD)) {
            JOptionPane.showMessageDialog(this,
                    "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
                    "Cảnh báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Kiểm tra thông tin
        if (username.equals(MOCK_USERNAME) && password.equals(MOCK_PASSWORD)) {
            JOptionPane.showMessageDialog(this,
                    "✅ Đăng nhập thành công!\n\n" +
                            "Xin chào " + "taiKhoan.getVaiTro(): " + "taiKhoan.getHoTen()" + "!", //Sửa chỗ này nữa
                    "Thành công",
                    JOptionPane.INFORMATION_MESSAGE);

            // Mở MenuChinh (giống bản test)
            MenuChinh menuChinh = new MenuChinh("Admin"); // Nam khi tài khoản lấy thành công thì đưa cái vaitro vào chỗ "Amdin" ví dụ là MenuChinh(taiKhoan.getVaiTro())
            menuChinh.setVisible(true);
            this.dispose();

        } else {
            JOptionPane.showMessageDialog(this,
                    "❌ Tên đăng nhập hoặc mật khẩu không đúng!\n\n" +
                            "Mock account:\nUsername: admin\nPassword: 123456",
                    "Lỗi đăng nhập",
                    JOptionPane.ERROR_MESSAGE);
            txtPassword.setText("");
            addPlaceholder(txtPassword, PLACEHOLDER_PASSWORD);
            txtPassword.requestFocus();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DangNhapGUI gui = new DangNhapGUI();
            gui.setVisible(true);
        });
    }
}
