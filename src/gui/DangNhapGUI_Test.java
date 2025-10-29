package gui;

//============================================
//ĐĂNG NHẬP GUI - MOCK VERSION (HÙNG TẠO TẠM)
//TODO: Tuần 3 thay bằng DangNhapGUI thật của Nam
//============================================

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
* Form đăng nhập MOCK - chỉ để test
* Tuần 3 sẽ thay bằng form thật của Nam (có kết nối TaiKhoanDAO)
* 
* @author Hùng (Mock version)
*/
public class DangNhapGUI_Test extends JFrame {
 
 // Components
 private JTextField txtUsername;
 private JPasswordField txtPassword;
 private JButton btnLogin;
 private JButton btnExit;
 
 // MOCK DATA - Hardcode tài khoản
 private static final String MOCK_USERNAME = "admin";
 private static final String MOCK_PASSWORD = "123456";
 
 public DangNhapGUI_Test() {
     initComponents();
     setLocationRelativeTo(null);
 }
 
 private void initComponents() {
     // Cấu hình JFrame
     setTitle("Đăng nhập - Quản lý Rạp Chiếu Phim");
     setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
     setSize(600, 700);
     setResizable(false);
     setLayout(new BorderLayout());
     
     // Main Panel
     JPanel mainPanel = new JPanel();
     mainPanel.setLayout(new GridBagLayout());
     mainPanel.setBackground(Color.WHITE);
     mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
     
     GridBagConstraints gbc = new GridBagConstraints();
     gbc.insets = new Insets(10, 10, 10, 10);
     gbc.fill = GridBagConstraints.HORIZONTAL;
     
     // Logo/Title
     JLabel lblTitle = new JLabel("🎬 QUẢN LÝ RẠP CHIẾU PHIM");
     lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
     lblTitle.setForeground(new Color(51, 51, 51));
     lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
     gbc.gridx = 0;
     gbc.gridy = 0;
     gbc.gridwidth = 2;
     mainPanel.add(lblTitle, gbc);
     
     // Subtitle (Mock notice)
     JLabel lblSubtitle = new JLabel("(Mock version - Tuần 1)");
     lblSubtitle.setFont(new Font("Arial", Font.ITALIC, 12));
     lblSubtitle.setForeground(Color.GRAY);
     lblSubtitle.setHorizontalAlignment(SwingConstants.CENTER);
     gbc.gridy = 1;
     mainPanel.add(lblSubtitle, gbc);
     
     // Username label
     gbc.gridwidth = 1;
     gbc.gridy = 2;
     gbc.gridx = 0;
     JLabel lblUsername = new JLabel("Tên đăng nhập:");
     lblUsername.setFont(new Font("Arial", Font.PLAIN, 14));
     mainPanel.add(lblUsername, gbc);
     
     // Username field
     gbc.gridx = 1;
     txtUsername = new JTextField(15);
     txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
     txtUsername.setText("admin"); // Pre-fill for testing
     mainPanel.add(txtUsername, gbc);
     
     // Password label
     gbc.gridy = 3;
     gbc.gridx = 0;
     JLabel lblPassword = new JLabel("Mật khẩu:");
     lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
     mainPanel.add(lblPassword, gbc);
     
     // Password field
     gbc.gridx = 1;
     txtPassword = new JPasswordField(15);
     txtPassword.setFont(new Font("Arial", Font.PLAIN, 14));
     txtPassword.setText("123456"); // Pre-fill for testing
     mainPanel.add(txtPassword, gbc);
     
     // Mock info panel
     JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
     infoPanel.setBackground(new Color(255, 243, 205));
     infoPanel.setBorder(BorderFactory.createLineBorder(new Color(255, 193, 7), 1));
     JLabel lblInfo = new JLabel("<html>💡 <b>Mock Account:</b><br/>" +
                                  "&nbsp;&nbsp;&nbsp;Username: admin<br/>" +
                                  "&nbsp;&nbsp;&nbsp;Password: 123456</html>");
     lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
     infoPanel.add(lblInfo);
     
     gbc.gridy = 4;
     gbc.gridx = 0;
     gbc.gridwidth = 2;
     mainPanel.add(infoPanel, gbc);
     
     // Button panel
     JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
     buttonPanel.setBackground(Color.WHITE);
     
     btnLogin = new JButton("Đăng nhập");
     btnLogin.setFont(new Font("Arial", Font.BOLD, 14));
     btnLogin.setPreferredSize(new Dimension(120, 35));
     btnLogin.setBackground(new Color(0, 123, 255));
     btnLogin.setForeground(Color.WHITE);
     btnLogin.setFocusPainted(false);
     btnLogin.addActionListener(e -> handleLogin());
     
     btnExit = new JButton("Thoát");
     btnExit.setFont(new Font("Arial", Font.PLAIN, 14));
     btnExit.setPreferredSize(new Dimension(120, 35));
     btnExit.setBackground(new Color(220, 53, 69));
     btnExit.setForeground(Color.WHITE);
     btnExit.setFocusPainted(false);
     btnExit.addActionListener(e -> System.exit(0));
     
     buttonPanel.add(btnLogin);
     buttonPanel.add(btnExit);
     
     gbc.gridy = 5;
     gbc.gridx = 0;
     gbc.gridwidth = 2;
     mainPanel.add(buttonPanel, gbc);
     
     add(mainPanel, BorderLayout.CENTER);
     
     // Enter key listener
     txtPassword.addKeyListener(new KeyAdapter() {
         @Override
         public void keyPressed(KeyEvent e) {
             if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                 handleLogin();
             }
         }
     });
     
     // Footer
     JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
     footerPanel.setBackground(new Color(248, 249, 250));
     footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
     JLabel lblFooter = new JLabel("© 2025 - Module Hùng: Phim, Phòng, Lịch chiếu");
     lblFooter.setFont(new Font("Arial", Font.PLAIN, 11));
     lblFooter.setForeground(Color.GRAY);
     footerPanel.add(lblFooter);
     add(footerPanel, BorderLayout.SOUTH);
 }
 
 private void handleLogin() {
     String username = txtUsername.getText().trim();
     String password = new String(txtPassword.getPassword()).trim();
     
     // MOCK VALIDATION - Hardcode
     if (username.isEmpty() || password.isEmpty()) {
         JOptionPane.showMessageDialog(this,
             "Vui lòng nhập đầy đủ tên đăng nhập và mật khẩu!",
             "Cảnh báo",
             JOptionPane.WARNING_MESSAGE);
         return;
     }
     
     // Check mock credentials
     if (username.equals(MOCK_USERNAME) && password.equals(MOCK_PASSWORD)) {
         JOptionPane.showMessageDialog(this,
             "✅ Đăng nhập thành công!\n\n" +
             "Xin chào Admin!\n" +
             "(Mock authentication - Tuần 3 sẽ dùng TaiKhoanDAO của Nam)",
             "Thành công",
             JOptionPane.INFORMATION_MESSAGE);
         
         // Mở MenuChinh
         MenuChinh menuChinh = new MenuChinh();
         menuChinh.setVisible(true);
         
         // Đóng form đăng nhập
         this.dispose();
         
     } else {
         JOptionPane.showMessageDialog(this,
             "❌ Tên đăng nhập hoặc mật khẩu không đúng!\n\n" +
             "Mock account:\n" +
             "Username: admin\n" +
             "Password: 123456",
             "Lỗi đăng nhập",
             JOptionPane.ERROR_MESSAGE);
         
         txtPassword.setText("");
         txtPassword.requestFocus();
     }
 }
 
 // Test
 public static void main(String[] args) {
     SwingUtilities.invokeLater(() -> {
    	 DangNhapGUI_Test gui = new DangNhapGUI_Test();
         gui.setVisible(true);
     });
 }
}