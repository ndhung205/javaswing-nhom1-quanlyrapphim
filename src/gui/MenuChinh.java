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

// Các import DAO này có thể bạn chưa dùng, nhưng cứ để
// import dao.DatVeDAO;
// import dao.PhimDAO;
// import dao.PhongDAO;
import gui.NhanVienGUI;
public class MenuChinh extends JFrame {
	
	private String currentUserRole; // <-- Thêm biến này để lưu vai trò
	// Components
	private JMenuBar menuBar;
	private JPanel mainPanel;

	public MenuChinh(String role) {
		this.currentUserRole = role;
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

	/**
	 * Phương thức createMenuBar đã được cập nhật
	 * để phản ánh cấu trúc CSDL đầy đủ.
	 */
	private void createMenuBar() {
		menuBar = new JMenuBar();
		
		// ========== CÁC MODULE NGHIỆP VỤ (HÙNG) ==========
		// Chỉ Admin và Quản Lý mới thấy các menu quản lý
		if (currentUserRole.equals("Admin") || currentUserRole.equals("QuanLy")) {
			// --- Menu Phim ---
			JMenu menuPhim = new JMenu("🎬 Quản lý Phim");
			JMenuItem itemPhim = new JMenuItem("Danh sách Phim");
			itemPhim.addActionListener(e -> openPhimGUI());
			menuPhim.add(itemPhim);
			menuBar.add(menuPhim);

			// --- Menu Phòng ---
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
			menuBar.add(menuPhong);

			// --- Menu Lịch Chiếu ---
			JMenu menuLichChieu = new JMenu("📅 Lịch Chiếu");
			JMenuItem itemLichChieu = new JMenuItem("Quản lý Lịch Chiếu");
			itemLichChieu.addActionListener(e -> openLichChieuGUI());
			menuLichChieu.add(itemLichChieu);
			menuBar.add(menuLichChieu);
		}
		
		// ========== MODULE BÁN VÉ (ĐẠT) ==========
		// Mọi người đều thấy menu này, nhưng nội dung bên trong thì khác
		JMenu menuVe = new JMenu("🎟️ Quản lý Vé");

		// Tất cả vai trò đều được Đặt vé và xem Hóa đơn
		JMenuItem itemDatVe = new JMenuItem("Đặt vé");
		itemDatVe.addActionListener(e -> openDatVeGUI());
		menuVe.add(itemDatVe);

		JMenuItem itemHoaDon = new JMenuItem("Hóa đơn");
		itemHoaDon.addActionListener(e -> openHoaDonGUI());
		menuVe.add(itemHoaDon);

		// Chỉ Admin và Quản Lý mới thấy Thống kê và Khuyến mãi
		if (currentUserRole.equals("Admin") || currentUserRole.equals("QuanLy")) {
			menuVe.addSeparator();
			JMenuItem itemThongKe = new JMenuItem("Thống kê");
			itemThongKe.addActionListener(e -> openThongKeGUI());
			menuVe.add(itemThongKe);

			JMenuItem itemKhuyenMai = new JMenuItem("Quản lý Khuyến mãi");
			itemKhuyenMai.addActionListener(e -> openKhuyenMaiGUI());
			menuVe.add(itemKhuyenMai);
		}
		menuBar.add(menuVe); // Thêm menu Vé vào

		// ========== MODULE HỆ THỐNG (NAM) ==========
		// Mọi người đều thấy menu này, nhưng nội dung bên trong thì khác
		JMenu menuHeThong = new JMenu("⚙️ Hệ thống");

		// Chỉ Admin và Quản Lý mới thấy QL Nhân viên, Khách hàng
		if (currentUserRole.equals("Admin") || currentUserRole.equals("QuanLy")) {
			JMenuItem itemNhanVien = new JMenuItem("Quản lý Nhân viên");
			itemNhanVien.addActionListener(e -> openQLNhanVienGUI());
			menuHeThong.add(itemNhanVien);

			JMenuItem itemKhachHang = new JMenuItem("Quản lý Khách hàng");
			itemKhachHang.addActionListener(e -> openQLKhachHangGUI());
			menuHeThong.add(itemKhachHang);

			menuHeThong.addSeparator();
		}
		
		// CHỈ Admin mới thấy các mục cấu hình hệ thống
		if (currentUserRole.equals("Admin")) {
			JMenuItem itemTaiKhoan = new JMenuItem("Quản lý Tài khoản");
			itemTaiKhoan.addActionListener(e -> openQLTaiKhoanGUI());
			menuHeThong.add(itemTaiKhoan);

			JMenuItem itemChucVu = new JMenuItem("Quản lý Chức vụ");
			itemChucVu.addActionListener(e -> openQLChucVuGUI());
			menuHeThong.add(itemChucVu);

			// --- Menu Danh mục (chỉ cho Admin) ---
			JMenu menuDanhMuc = new JMenu("📂 Danh mục");
			JMenuItem itemLoaiPhim = new JMenuItem("Quản lý Loại phim");
			itemLoaiPhim.addActionListener(e -> openQLLoaiPhimGUI());
			menuDanhMuc.add(itemLoaiPhim);
			JMenuItem itemLoaiPhong = new JMenuItem("Quản lý loại phòng");
			itemLoaiPhong.addActionListener(e -> openQLLoaiPhongGUI());
			menuDanhMuc.add(itemLoaiPhong);
			JMenuItem itemLoaiGhe = new JMenuItem("Quản lý loại ghế");
			itemLoaiGhe.addActionListener(e -> openQLLoaiGheGUI());
			menuDanhMuc.add(itemLoaiGhe);
			JMenuItem itemPThucThanhToan = new JMenuItem("Quản lý Phương thức thanh toán");
			itemPThucThanhToan.addActionListener(e -> openQLPPThanhToanGUI());
			menuDanhMuc.add(itemPThucThanhToan);
			JMenuItem itemThue = new JMenuItem("Quản lý Thuế");
			itemThue.addActionListener(e -> openQLThueGUI());
			menuDanhMuc.add(itemThue);
			
			
			menuHeThong.add(menuDanhMuc);
			menuHeThong.addSeparator();
		}

		// Tất cả vai trò đều thấy Đăng xuất
		JMenuItem itemDangXuat = new JMenuItem("Đăng xuất");
		itemDangXuat.addActionListener(e -> logout());
		menuHeThong.add(itemDangXuat);

		menuBar.add(menuHeThong); // Thêm menu Hệ thống vào

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
		mainPanel.removeAll();
		// Giả sử PhimGUI là một JPanel
		mainPanel.removeAll();
	    mainPanel.add(new PhimGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	private void openPhongGUI() {
		mainPanel.removeAll();
		// mainPanel.add(new PhongGUI());
		mainPanel.removeAll();
	    mainPanel.add(new PhongGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	private void openGheGUI() {
		mainPanel.removeAll();
		mainPanel.add(new GheGUI());

		// cập nhật lại giao diện
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private void openSoDoGheGUI() {
		// TODO: Tuần 2 - Tạo SoDoGheGUI
		showNotImplemented("SoDoGheGUI - Đang phát triển (Tuần 2)");
	}

	private void openLichChieuGUI() {
		mainPanel.removeAll();
	    mainPanel.add(new LichChieuGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	private void openDatVeGUI() {
		mainPanel.removeAll();
	    mainPanel.add(new DatVeGUI());
	    
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();

	}
	
	private void openHoaDonGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
		showNotImplemented("Quản lý hóa đơn - Của Đạt");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openThongKeGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
		showNotImplemented("Quản lý thống kê - Của Đạt");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openKhuyenMaiGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
		showNotImplemented("Quản lý KhuyenMai - Của Đạt");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLNhanVienGUI() {
		mainPanel.removeAll();
	   mainPanel.add(new NhanVienGUI());
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLKhachHangGUI() {
		mainPanel.removeAll();
	   mainPanel.add(new KhachHangGUI());
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLTaiKhoanGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý TaiKhoan - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLChucVuGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý ChucVu - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLLoaiPhongGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý loại Phòng - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLLoaiPhimGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý loại phim - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLLoaiGheGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý loại ghế - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLPPThanhToanGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý pp thanh toán - Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}
	
	private void openQLThueGUI() {
		mainPanel.removeAll();
	    //mainPanel.add(new DatVeGUI());
	    showNotImplemented("Quản lý thuế- Của Nam");
	    // cập nhật lại giao diện
	    mainPanel.revalidate();
	    mainPanel.repaint();
	}

	private void logout() {
		int choice = JOptionPane.showConfirmDialog(
				this,
				"Bạn có chắc muốn đăng xuất?",
				"Xác nhận",
				JOptionPane.YES_NO_OPTION);

		if (choice == JOptionPane.YES_OPTION) {
			// TODO: Mở lại DangNhapGUI (của Nam)
			DangNhapGUI dangNhapGUI = new DangNhapGUI();
			dangNhapGUI.setVisible(true);
			this.dispose();
		}
	}

	private void showNotImplemented(String feature) {
		JOptionPane.showMessageDialog(
				this,
				"Tính năng \"" + feature + "\" đang được phát triển",
				"Thông báo",
				JOptionPane.INFORMATION_MESSAGE);
	}

	// ========== MAIN METHOD (FOR TESTING) ==========
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> { 
			MenuChinh menu = new MenuChinh("Admin");
			menu.setVisible(true);
		});
	}
}