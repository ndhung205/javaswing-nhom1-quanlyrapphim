package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dao.DatVeDAO;
import dao.PhimDAO;
import dao.PhongDAO;

public class MenuChinh extends JFrame{
	// Components
    private JMenuBar menuBar;
    private JPanel mainPanel;
    
    public MenuChinh() {
    	mainPanel = new JPanel();
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        // Cấu hình JFrame
        setTitle("Quản lý Rạp Chiếu Phim");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 700);
        setLayout(new BorderLayout());
        
        // Tạo Menu Bar
        createMenuBar();
        
        // Tạo Main Panel
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        // Welcome panel
        mainPanel.add(createWelcomePanel());

        add(mainPanel, BorderLayout.CENTER);
        
        // Status bar
        JPanel statusBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusBar.setBorder(BorderFactory.createEtchedBorder());
        JLabel statusLabel = new JLabel("✅ Sẵn sàng | Database: Connected");
        statusBar.add(statusLabel);
        add(statusBar, BorderLayout.SOUTH);
    }
    
    private void createMenuBar() {
        menuBar = new JMenuBar();
        
        // ========== MENU CỦA HÙNG ==========
        JMenu menuPhim = new JMenu("🎬 Quản lý Phim");
        JMenuItem itemPhim = new JMenuItem("Danh sách Phim");
        itemPhim.addActionListener(e -> openPhimGUI());
        menuPhim.add(itemPhim);
        
        JMenu menuPhong = new JMenu("🏛️ Quản lý Phòng");
        JMenuItem itemPhong = new JMenuItem("Danh sách Phòng");
        itemPhong.addActionListener(e -> openPhongGUI());
        menuPhong.add(itemPhong);
        
        JMenuItem itemGhe = new JMenuItem("Quản lý Ghế");
        itemGhe.addActionListener(e -> openGheGUI());
        menuPhong.add(itemGhe);
        
        JMenuItem itemSoDoGhe = new JMenuItem("Sơ đồ Ghế");
        itemSoDoGhe.addActionListener(e -> openSoDoGheGUI());
        menuPhong.add(itemSoDoGhe);
        
        JMenu menuLichChieu = new JMenu("📅 Lịch Chiếu");
        JMenuItem itemLichChieu = new JMenuItem("Quản lý Lịch Chiếu");
        itemLichChieu.addActionListener(e -> openLichChieuGUI());
        menuLichChieu.add(itemLichChieu);
        
        // ========== MENU CỦA ĐẠT ==========
        JMenu menuVe = new JMenu("🎟️ Quản lý Vé");
        JMenuItem itemDatVe = new JMenuItem("Đặt vé");
        itemDatVe.addActionListener(e -> openDatVeGUI());
        menuVe.add(itemDatVe);
        
        JMenuItem itemHoaDon = new JMenuItem("Hóa đơn");
        itemHoaDon.addActionListener(e -> showNotImplemented("Hóa đơn - Module của Đạt"));
        menuVe.add(itemHoaDon);
        
        JMenuItem itemThongKe = new JMenuItem("Thống kê");
        itemThongKe.addActionListener(e -> showNotImplemented("Thống kê - Module của Đạt"));
        menuVe.add(itemThongKe);
        
        // ========== MENU CỦA NAM ==========
        JMenu menuHeThong = new JMenu("⚙️ Hệ thống");
        JMenuItem itemDanhMuc = new JMenuItem("Danh mục");
        itemDanhMuc.addActionListener(e -> showNotImplemented("Danh mục - Module của Nam"));
        menuHeThong.add(itemDanhMuc);
        
        JMenuItem itemNhanVien = new JMenuItem("Nhân viên");
        itemNhanVien.addActionListener(e -> showNotImplemented("Nhân viên - Module của Nam"));
        menuHeThong.add(itemNhanVien);
        
        JMenuItem itemKhachHang = new JMenuItem("Khách hàng");
        itemKhachHang.addActionListener(e -> showNotImplemented("Khách hàng - Module của Nam"));
        menuHeThong.add(itemKhachHang);
        
        menuHeThong.addSeparator();
        
        JMenuItem itemDangXuat = new JMenuItem("Đăng xuất");
        itemDangXuat.addActionListener(e -> logout());
        menuHeThong.add(itemDangXuat);
        
        // Add menus to menubar
        menuBar.add(menuPhim);
        menuBar.add(menuPhong);
        menuBar.add(menuLichChieu);
        menuBar.add(menuVe);
        menuBar.add(menuHeThong);
        
        setJMenuBar(menuBar);
    }
    
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        
        JLabel lblWelcome = new JLabel("Chào mừng đến với Hệ thống Quản lý Rạp Chiếu Phim");
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 24));
        lblWelcome.setForeground(new Color(51, 51, 51));
        
        JLabel lblVersion = new JLabel("Version 1.0 - Module Hùng: Phim, Phòng, Ghế, Lịch chiếu");
        lblVersion.setFont(new Font("Arial", Font.PLAIN, 14));
        lblVersion.setForeground(Color.GRAY);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        panel.add(lblWelcome, gbc);
        
        gbc.gridy = 1;
        panel.add(lblVersion, gbc);
        
        return panel;
    }
    
    // ========== ACTION HANDLERS - MODULE HÙNG ==========
    
    private void openPhimGUI() {
        // TODO: Tuần 2 - Tạo PhimGUI
	    mainPanel.removeAll();
	    mainPanel.add(new PhimGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
    }
    
    private void openPhongGUI() {
        // TODO: Tuần 2 - Tạo PhongGUI
    	mainPanel.removeAll();
	    mainPanel.add(new PhongGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
    }
    
    private void openGheGUI() {
        // TODO: Tuần 2 - Tạo GheGUI
        showNotImplemented("GheGUI - Đang phát triển (Tuần 2)");
    }
    
    private void openSoDoGheGUI() {
        // TODO: Tuần 2 - Tạo SoDoGheGUI
        showNotImplemented("SoDoGheGUI - Đang phát triển (Tuần 2)");
    }
    
    private void openLichChieuGUI() {
        // TODO: Tuần 3 - Tạo LichChieuGUI
    	mainPanel.removeAll();
	    mainPanel.add(new LichChieuGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
    }
    private void openDatVeGUI() {
    	DatVeDAO datve = new DatVeDAO();
	    PhongDAO phong = new PhongDAO();
	    PhimDAO phim = new PhimDAO();
	    datve.connectDatabase();
	    mainPanel.removeAll();
	    mainPanel.add(new DatVeGUI(datve, phong, phim));
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
		
    }
    
    private void logout() {
        int choice = JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc muốn đăng xuất?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );
        
        if (choice == JOptionPane.YES_OPTION) {
            dispose();
            // TODO: Mở lại DangNhapGUI (của Nam)
            System.exit(0);
        }
    }
    
    private void showNotImplemented(String feature) {
        JOptionPane.showMessageDialog(
            this,
            "Tính năng \"" + feature + "\" đang được phát triển",
            "Thông báo",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    // ========== MAIN METHOD (FOR TESTING) ==========
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MenuChinh menu = new MenuChinh();
            menu.setVisible(true);
        });
    }
}
