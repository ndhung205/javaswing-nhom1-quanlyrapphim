
package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import dao.TaiKhoanDAO;
import entity.TaiKhoan;
import dao.NhanVienDAO;
import entity.NhanVien;
public class TaiKhoanGUI extends JPanel {
	private TaiKhoanDAO taiKhoanDAO;
	private JTable table;
	private DefaultTableModel model;
	private JTextField txtMaTK, txtMaNV, txtTenTK, txtMatKhau, txtTimKiem;
	private JCheckBox chkTrangThai;
	private JButton btnThem, btnXoa, btnTaiLai, btnTim, btnSua;
	private JComboBox<String> cboVaiTro; 

	public TaiKhoanGUI() {
		taiKhoanDAO = new TaiKhoanDAO();
		initComponents();
		loadData();
	}

	private void initComponents() {
		setLayout(new BorderLayout(10, 10));
		setBackground(new Color(245, 247, 250));

		JLabel lblTitle = new JLabel("QUẢN LÝ TÀI KHOẢN", SwingConstants.CENTER);
		lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
		lblTitle.setForeground(new Color(0, 102, 204));
		lblTitle.setBorder(new EmptyBorder(10, 0, 10, 0));
		add(lblTitle, BorderLayout.NORTH);

		JPanel pnlCenter = new JPanel(new GridLayout(1, 2, 10, 0));
		pnlCenter.setBorder(new EmptyBorder(0, 10, 10, 10));
		pnlCenter.setBackground(Color.WHITE);

		// ===== LEFT FORM =====
		JPanel pnlForm = new JPanel(new GridBagLayout());
		pnlForm.setBackground(Color.WHITE);
		pnlForm.setBorder(BorderFactory.createTitledBorder(new LineBorder(new Color(180, 200, 240), 1, true),
				"Thông tin tài khoản", TitledBorder.LEADING, TitledBorder.TOP, new Font("Segoe UI", Font.BOLD, 14),
				new Color(0, 102, 204)));

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(8, 8, 8, 8);
		gbc.anchor = GridBagConstraints.WEST;

		txtMaTK = new JTextField(15);
		txtMaNV = new JTextField(15);
		txtTenTK = new JTextField(15);
		txtMatKhau = new JTextField(15);
		cboVaiTro = new JComboBox<>(new String[] { "Admin", "NhanVien", "QuanLy" });
		chkTrangThai = new JCheckBox("Hoạt động");
		chkTrangThai.setBackground(Color.WHITE);

		// Row 1
		gbc.gridx = 0;
		gbc.gridy = 0;
		pnlForm.add(new JLabel("Mã tài khoản:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(txtMaTK, gbc);

		// Row 2
		gbc.gridx = 0;
		gbc.gridy++;
		pnlForm.add(new JLabel("Mã nhân viên:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(txtMaNV, gbc);

		// Row 3
		gbc.gridx = 0;
		gbc.gridy++;
		pnlForm.add(new JLabel("Tên tài khoản:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(txtTenTK, gbc);

		// Row 4
		gbc.gridx = 0;
		gbc.gridy++;
		pnlForm.add(new JLabel("Mật khẩu:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(txtMatKhau, gbc);

		// Row 5 (ComboBox Vai trò)
		gbc.gridx = 0;
		gbc.gridy++;
		pnlForm.add(new JLabel("Vai trò:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(cboVaiTro, gbc);

		// Row 6
		gbc.gridx = 0;
		gbc.gridy++;
		pnlForm.add(new JLabel("Trạng thái:", JLabel.RIGHT), gbc);
		gbc.gridx = 1;
		pnlForm.add(chkTrangThai, gbc);

		pnlCenter.add(pnlForm);

		// ===== RIGHT TABLE =====
		String[] columnNames = { "Mã TK", "Mã NV", "Tên TK", "Mật khẩu", "Vai trò", "Trạng thái" };
		model = new DefaultTableModel(columnNames, 0);
		table = new JTable(model);
		table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
		table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
		table.setRowHeight(25);
		table.setSelectionBackground(new Color(220, 235, 255));
		JScrollPane scroll = new JScrollPane(table);
		scroll.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 240)));
		pnlCenter.add(scroll);

		add(pnlCenter, BorderLayout.CENTER);

		// ===== BOTTOM BUTTON BAR =====
		JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
		pnlBottom.setBackground(Color.WHITE);
		txtTimKiem = new JTextField(15);
		btnTim = new JButton("Tìm");
		btnThem = new JButton("Thêm");
		btnXoa = new JButton("Xóa");
		btnTaiLai = new JButton("Tải lại");
		btnSua = new JButton("Sửa");

		JButton[] buttons = { btnTim, btnThem, btnXoa, btnTaiLai, btnSua };
		for (JButton b : buttons) {
			b.setFont(new Font("Segoe UI", Font.BOLD, 13));
			b.setBackground(new Color(0, 102, 204));
			b.setForeground(Color.WHITE);
			b.setFocusPainted(false);
			b.setBorder(new EmptyBorder(5, 15, 5, 15));
			b.setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		pnlBottom.add(new JLabel("Tìm theo tên:"));
		pnlBottom.add(txtTimKiem);
		pnlBottom.add(btnTim);
		pnlBottom.add(btnThem);
		pnlBottom.add(btnXoa);
		pnlBottom.add(btnTaiLai);
		pnlBottom.add(btnSua);

		add(pnlBottom, BorderLayout.SOUTH);

		// ===== SỰ KIỆN =====
		btnThem.addActionListener(e -> themTaiKhoan());
		btnXoa.addActionListener(e -> xoaTaiKhoan());
		btnTaiLai.addActionListener(e -> {
			loadData();
			clearForm();
		});

		btnTim.addActionListener(e -> timTaiKhoan());
		btnSua.addActionListener(e -> suaTaiKhoan());

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			@Override
			public void mouseClicked(java.awt.event.MouseEvent e) {
				int row = table.getSelectedRow();
				if (row != -1) {
					txtMaTK.setText(model.getValueAt(row, 0).toString());
					txtMaNV.setText(model.getValueAt(row, 1).toString());
					txtTenTK.setText(model.getValueAt(row, 2).toString());
					txtMatKhau.setText(model.getValueAt(row, 3).toString());
					cboVaiTro.setSelectedItem(model.getValueAt(row, 4).toString());
					chkTrangThai.setSelected("Hoạt động".equals(model.getValueAt(row, 5).toString()));
				}
			}
		});
	}

	private void loadData() {
		model.setRowCount(0);
		try (var con = connectDB.DatabaseConnection.getInstance().getConnection();
				var stmt = con.prepareStatement("SELECT * FROM TaiKhoan");
				var rs = stmt.executeQuery()) {
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("maTaiKhoan"), rs.getString("maNhanVien"),
						rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("vaiTro"),
						rs.getBoolean("trangThai") ? "Hoạt động" : "Khóa" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi tải dữ liệu: " + e.getMessage());
		}
	}

//	private void themTaiKhoan() {
//		try {
//			TaiKhoan tk = new TaiKhoan(txtMaTK.getText().trim(), txtMaNV.getText().trim(), txtTenTK.getText().trim(),
//					txtMatKhau.getText().trim(), cboVaiTro.getSelectedItem().toString(), chkTrangThai.isSelected());
//
//			if (taiKhoanDAO.dangKy(tk)) {
//				JOptionPane.showMessageDialog(this, "Thêm thành công!");
//				loadData();
//			} else {
//				JOptionPane.showMessageDialog(this, "Thêm thất bại!");
//			}
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
//		}
//	}
	private void themTaiKhoan() {
	    try {
	        String maTK = txtMaTK.getText().trim();
	        String maNV = txtMaNV.getText().trim();
	        String tenTK = txtTenTK.getText().trim();
	        String matKhau = txtMatKhau.getText().trim();
	        String vaiTro = cboVaiTro.getSelectedItem().toString();
	        boolean trangThai = chkTrangThai.isSelected();

	        
	        if (maTK.isEmpty() || maNV.isEmpty() || tenTK.isEmpty() || matKhau.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Không được bỏ trống dữ liệu!");
	            return;
	        }

	        NhanVienDAO nvDao = new NhanVienDAO();
	        NhanVien nv = nvDao.findNhanVienById(maNV);
	        if (nv == null) {
	            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại!");
	            return;
	        }

	        TaiKhoan tk = new TaiKhoan(maTK, nv, tenTK, matKhau, vaiTro, trangThai);

	        if (taiKhoanDAO.dangKy(tk)) {
	            JOptionPane.showMessageDialog(this, "Thêm thành công!");
	            loadData();
	            clearForm();
	        } else {
	            JOptionPane.showMessageDialog(this, "Thêm thất bại!");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
	    }
	}

	private void xoaTaiKhoan() {
		int row = table.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Chọn tài khoản cần xóa!");
			return;
		}

		String maTK = model.getValueAt(row, 0).toString();
		int confirm = JOptionPane.showConfirmDialog(this, "Xóa tài khoản " + maTK + "?", "Xác nhận",
				JOptionPane.YES_NO_OPTION);
		if (confirm == JOptionPane.YES_OPTION) {
			try (var con = connectDB.DatabaseConnection.getInstance().getConnection();
					var stmt = con.prepareStatement("DELETE FROM TaiKhoan WHERE maTaiKhoan = ?")) {
				stmt.setString(1, maTK);
				stmt.executeUpdate();
				JOptionPane.showMessageDialog(this, "Đã xóa");
				loadData();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(this, "Lỗi xóa: " + e.getMessage());
			}
		}
	}

//	private void suaTaiKhoan() {
//		int row = table.getSelectedRow();
//		if (row == -1) {
//			JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản cần sửa!");
//			return;
//		}
//
//		try {
//			TaiKhoan tk = new TaiKhoan(txtMaTK.getText().trim(), txtMaNV.getText().trim(), txtTenTK.getText().trim(),
//					txtMatKhau.getText().trim(), cboVaiTro.getSelectedItem().toString(), chkTrangThai.isSelected());
//
//			if (taiKhoanDAO.capNhatTaiKhoan(tk)) {
//				JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
//				loadData();
//			} else {
//				JOptionPane.showMessageDialog(this, "Cập nhật thất bại!");
//			}
//		} catch (Exception e) {
//			JOptionPane.showMessageDialog(this, "Lỗi: " + e.getMessage());
//		}
//	}
	private void suaTaiKhoan() {
	    int row = table.getSelectedRow();
	    if (row == -1) {
	        JOptionPane.showMessageDialog(this, "Vui lòng chọn tài khoản");
	        return;
	    }

	    try {
	        String maTK = txtMaTK.getText().trim();
	        String maNV = txtMaNV.getText().trim();
	        String tenTK = txtTenTK.getText().trim();
	        String matKhau = txtMatKhau.getText().trim();
	        String vaiTro = cboVaiTro.getSelectedItem().toString();
	        boolean trangThai = chkTrangThai.isSelected();

	        if (maTK.isEmpty() || maNV.isEmpty() || tenTK.isEmpty() || matKhau.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Không được bỏ trống ");
	            return;
	        }

	        NhanVienDAO nvDAO = new NhanVienDAO();
	        NhanVien nv = nvDAO.findNhanVienById(maNV);
	        
	        if (nv == null) {
	            JOptionPane.showMessageDialog(this, "Mã nhân viên không tồn tại");
	            return;
	        }

	        
	        TaiKhoan tk = new TaiKhoan(maTK, nv, tenTK, matKhau, vaiTro, trangThai);

	        if (taiKhoanDAO.capNhatTaiKhoan(tk)) {
	            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
	            loadData();
	        } else {
	            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Lỗi" + e.getMessage());
	    }
	}

	private void timTaiKhoan() {
		String keyword = txtTimKiem.getText().trim();
		if (keyword.isEmpty()) {
			loadData();
			return;
		}

		model.setRowCount(0);
		try (var con = connectDB.DatabaseConnection.getInstance().getConnection();
				var stmt = con.prepareStatement("SELECT * FROM TaiKhoan WHERE tenTaiKhoan LIKE ?")) {
			stmt.setString(1, "%" + keyword + "%");
			var rs = stmt.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString("maTaiKhoan"), rs.getString("maNhanVien"),
						rs.getString("tenTaiKhoan"), rs.getString("matKhau"), rs.getString("vaiTro"),
						rs.getBoolean("trangThai") ? "Hoạt động" : "Khóa" });
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Lỗi tìm kiếm: " + e.getMessage());
		}
	}

	private void clearForm() {
		txtMaTK.setText("");
		txtMaNV.setText("");
		txtTenTK.setText("");
		txtMatKhau.setText("");
		cboVaiTro.setSelectedIndex(0); 
		chkTrangThai.setSelected(false);
	}

	public static void main(String[] args) {
		JFrame f = new JFrame("Test TaiKhoanGUI");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(1000, 550);
		f.setLocationRelativeTo(null);
		f.setContentPane(new TaiKhoanGUI());
		f.setVisible(true);
	}
}
