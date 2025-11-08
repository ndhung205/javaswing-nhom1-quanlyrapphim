package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.LoaiGheDAO;
import entity.LoaiGhe;

public class LoaiGheGUI extends JPanel {

	private JPanel formFields;
	private JTextField txtMaLoaiGhe;
	private JTextField txtTenLoaiGhe;
	private JTextField txtPhuThu;
	private JTextField txtMoTa;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimKiem;
	private JButton btnTimKiem;
	private DefaultTableModel tableModel;
	private JTable tableLoaiGhe;
	private LoaiGheDAO loaiGheDAO;

	public LoaiGheGUI() {
		loaiGheDAO = new LoaiGheDAO();
		intitComponent();
		loadData();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		List<LoaiGhe> list = loaiGheDAO.getAllLoaiGhe();
		for (LoaiGhe loaiGhe : list) {
			tableModel.addRow(new Object[] { 
					loaiGhe.getMaLoaiGhe(), 
					loaiGhe.getTenLoaiGhe(), 
					loaiGhe.getPhuThu(),
					loaiGhe.getMoTa() 
			});
		}
	}

	private void intitComponent() {
		setSize(HEIGHT, WIDTH);
		setLayout(new BorderLayout(10, 10));

		// Header
		JPanel headerPanel = createHeaderPanel();
		add(headerPanel, BorderLayout.NORTH);

		// Left Panel - Form
		JPanel leftPanel = createFormPanel();

		// Right Panel - Table
		JPanel rightPanel = createTablePanel();

		// Split Pane
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
		splitPane.setDividerLocation(420);
		add(splitPane, BorderLayout.CENTER);
	}

	private JPanel createHeaderPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI GHẾ");
		lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
		lblTitle.setForeground(Color.WHITE);
		panel.add(lblTitle, BorderLayout.WEST);
		return panel;
	}

	private JPanel createTablePanel() {
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);

		// Search
		JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		searchPanel.setBackground(Color.WHITE);
		searchPanel.add(new JLabel("Tìm kiếm theo tên loại ghế:"));
		txtTimKiem = new JTextField(20);
		searchPanel.add(txtTimKiem);
		btnTimKiem = new JButton("Tìm");
		btnTimKiem.addActionListener(e -> handleTimKiem());
		searchPanel.add(btnTimKiem);
		JButton btnShowAll = new JButton("Hiện tất cả");
		btnShowAll.addActionListener(e -> loadData());
		searchPanel.add(btnShowAll);

		// Table
		String[] columns = "Mã loại ghế;Tên loại ghế;Phụ thu;Mô tả".split(";");
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableLoaiGhe = new JTable(tableModel);
		tableLoaiGhe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLoaiGhe.setRowHeight(25);

		// Double click to edit
		tableLoaiGhe.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtMaLoaiGhe.setEditable(false);
					loadFormFromTable();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tableLoaiGhe);

		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Info label
		JLabel lblInfo = new JLabel("Double-click vào loại ghế để chỉnh sửa");
		lblInfo.setForeground(Color.GRAY);
		lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
		panel.add(lblInfo, BorderLayout.SOUTH);

		return panel;
	}

	private void handleTimKiem() {
		String keyword = txtTimKiem.getText().trim();
		if (keyword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
			return;
		}
		tableModel.setRowCount(0);
		List<LoaiGhe> list = loaiGheDAO.search(keyword);
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy loại ghế nào");
		} else {
			for (LoaiGhe loaiGhe : list) {
				tableModel.addRow(new Object[] { 
						loaiGhe.getMaLoaiGhe(), 
						loaiGhe.getTenLoaiGhe(),
						loaiGhe.getPhuThu(), 
						loaiGhe.getMoTa() 
				});
			}
		}
	}

	private JPanel createFormPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);

		// Title
		JLabel lblFormTitle = new JLabel("Thông tin loại ghế");
		lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
		lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

		// Form fields
		formFields = new JPanel(new GridBagLayout());
		formFields.setBackground(Color.WHITE);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int row = 0;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Mã loại ghế: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtMaLoaiGhe = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Tên loại ghế: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtTenLoaiGhe = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Phụ thu: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtPhuThu = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Mô tả:"), gbc);
		gbc.gridx = 1;
		formFields.add(txtMoTa = new JTextField(15), gbc);

		// Buttons Panel
		JPanel btnJPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		btnJPanel.setBackground(Color.WHITE);

		btnThem = new JButton("Thêm");
		btnThem.setBackground(new Color(46, 204, 113));
		btnThem.setForeground(Color.WHITE);
		btnThem.setFocusPainted(false);
		btnThem.addActionListener(e -> handleThem());

		btnSua = new JButton("Sửa");
		btnSua.setBackground(new Color(52, 152, 219));
		btnSua.setForeground(Color.WHITE);
		btnSua.setFocusPainted(false);
		btnSua.addActionListener(e -> handleSua());

		btnXoa = new JButton("Xóa");
		btnXoa.setBackground(new Color(231, 76, 60));
		btnXoa.setForeground(Color.WHITE);
		btnXoa.setFocusPainted(false);
		btnXoa.addActionListener(e -> handleXoa());

		btnLamMoi = new JButton("Làm mới");
		btnLamMoi.setBackground(new Color(149, 165, 166));
		btnLamMoi.setForeground(Color.WHITE);
		btnLamMoi.setFocusPainted(false);
		btnLamMoi.addActionListener(e -> clearForm());

		btnJPanel.add(btnThem);
		btnJPanel.add(btnSua);
		btnJPanel.add(btnXoa);
		btnJPanel.add(btnLamMoi);

		// Assemble form panel
		panel.add(lblFormTitle, BorderLayout.NORTH);
		panel.add(formFields, BorderLayout.CENTER);
		panel.add(btnJPanel, BorderLayout.SOUTH);

		return panel;
	}

	private void clearForm() {
		txtMaLoaiGhe.setText("");
		txtTenLoaiGhe.setText("");
		txtPhuThu.setText("");
		txtMoTa.setText("");
		txtMaLoaiGhe.setEditable(true);
		tableLoaiGhe.clearSelection();
	}

	private void handleXoa() {
		int row = tableLoaiGhe.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 loại ghế để xóa");
			return;
		}
		String maLG = tableModel.getValueAt(row, 0).toString();
		String tenLG = tableModel.getValueAt(row, 1).toString();
		int choice = JOptionPane.showConfirmDialog(this,
				"Bạn có chắc muốn xóa loại ghế \"" + tenLG + "\"?\n" + "⚠️ Lưu ý", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			if (loaiGheDAO.delete(maLG)) {
				JOptionPane.showMessageDialog(this, "✅ Xóa loại ghế thành công!");
				loadData();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "❌ Không thể xóa Loại ghế!\n", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleSua() {
		int row = tableLoaiGhe.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn loại ghế để sửa");
			return;
		}
		
		if (!validateFrom()) return;
		
		LoaiGhe loaiGhe = getLoaiGheFromForm();
		if (loaiGheDAO.update(loaiGhe)) {
			JOptionPane.showMessageDialog(this, "Sửa thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this, "Không sửa được!");
		}
	}

	private void handleThem() {
		if (!validateFrom())
			return;
		LoaiGhe loaiGhe = getLoaiGheFromForm();
		if (loaiGheDAO.insert(loaiGhe)) {
			JOptionPane.showMessageDialog(this, "Thêm loại ghế thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this,
					"Thêm loại ghế thất bại do bị trùng mã: " + loaiGhe.getMaLoaiGhe() + "!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private LoaiGhe getLoaiGheFromForm() {
		LoaiGhe loaiGhe = new LoaiGhe();
		loaiGhe.setMaLoaiGhe(txtMaLoaiGhe.getText().trim());
		loaiGhe.setTenLoaiGhe(txtTenLoaiGhe.getText().trim());
		try {
			loaiGhe.setPhuThu(Double.parseDouble(txtPhuThu.getText().trim()));
		} catch (NumberFormatException e) {
			// Validation đã xử lý, nhưng để an toàn
			loaiGhe.setPhuThu(0); 
		}
		loaiGhe.setMoTa(txtMoTa.getText().trim());
		return loaiGhe;
	}

	private boolean validateFrom() {
		if (txtMaLoaiGhe.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã loại ghế!");
			txtMaLoaiGhe.requestFocus();
			return false;
		}
		if (txtTenLoaiGhe.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại ghế!");
			txtTenLoaiGhe.requestFocus();
			return false;
		}
		if (txtPhuThu.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập phụ thu!");
			txtPhuThu.requestFocus();
			return false;
		}
		try {
			double phuThu = Double.parseDouble(txtPhuThu.getText().trim());
			if (phuThu < 0) {
				JOptionPane.showMessageDialog(this, "Phụ thu phải lớn hơn hoặc bằng 0!");
				txtPhuThu.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Phụ thu phải là một con số hợp lệ!");
			txtPhuThu.requestFocus();
			return false;
		}

		return true;
	}

	private void loadFormFromTable() {
		int selectedRow = tableLoaiGhe.getSelectedRow();
		if (selectedRow == -1) {
			return;
		}
		String maLGhe = tableModel.getValueAt(selectedRow, 0).toString();
		LoaiGhe loaiGhe = loaiGheDAO.getById(maLGhe);
		if (loaiGhe != null) {
			txtMaLoaiGhe.setText(loaiGhe.getMaLoaiGhe());
			txtTenLoaiGhe.setText(loaiGhe.getTenLoaiGhe());
			txtPhuThu.setText(String.valueOf(loaiGhe.getPhuThu()));
			txtMoTa.setText(loaiGhe.getMoTa());
			txtMaLoaiGhe.setEditable(false);
		}
	}
}