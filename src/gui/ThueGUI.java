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

import dao.ThueDAO;
import entity.Thue;

public class ThueGUI extends JPanel {

	private JPanel formFields;
	private JTextField txtMaThue;
	private JTextField txtTenThue;
	private JTextField txtPhanTram;
	private JTextField txtMoTa;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimKiem;
	private JButton btnTimKiem;
	private DefaultTableModel tableModel;
	private JTable tableThue;
	private ThueDAO thueDAO;

	public ThueGUI() {
		thueDAO = new ThueDAO();
		intitComponent();
		loadData();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		List<Thue> list = thueDAO.getAllThue();
		for (Thue thue : list) {
			tableModel.addRow(new Object[] { 
					thue.getMaThue(), 
					thue.getTenThue(), 
					thue.getPhanTram(), 
					thue.getMoTa() 
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
		JLabel lblTitle = new JLabel("QUẢN LÝ THUẾ");
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
		searchPanel.add(new JLabel("Tìm kiếm theo tên thuế:"));
		txtTimKiem = new JTextField(20);
		searchPanel.add(txtTimKiem);
		btnTimKiem = new JButton("Tìm");
		btnTimKiem.addActionListener(e -> handleTimKiem());
		searchPanel.add(btnTimKiem);
		JButton btnShowAll = new JButton("Hiện tất cả");
		btnShowAll.addActionListener(e -> loadData());
		searchPanel.add(btnShowAll);

		// Table
		String[] columns = "Mã thuế;Tên thuế;Phần trăm;Mô tả".split(";");
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableThue = new JTable(tableModel);
		tableThue.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableThue.setRowHeight(25);

		// Double click to edit
		tableThue.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtMaThue.setEditable(false);
					loadFormFromTable();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tableThue);

		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Info label
		JLabel lblInfo = new JLabel("Double-click vào thuế để chỉnh sửa");
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
		List<Thue> list = thueDAO.search(keyword);
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy thuế nào");
		} else {
			for (Thue thue : list) {
				tableModel.addRow(new Object[] { 
						thue.getMaThue(), 
						thue.getTenThue(), 
						thue.getPhanTram(),
						thue.getMoTa() 
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
		JLabel lblFormTitle = new JLabel("Thông tin thuế");
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
		formFields.add(new JLabel("Mã thuế: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtMaThue = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Tên thuế: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtTenThue = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Phần trăm: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtPhanTram = new JTextField(15), gbc);

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
		txtMaThue.setText("");
		txtTenThue.setText("");
		txtPhanTram.setText("");
		txtMoTa.setText("");
		txtMaThue.setEditable(true);
		tableThue.clearSelection();
	}

	private void handleXoa() {
		int row = tableThue.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 thuế để xóa");
			return;
		}
		String maThue = tableModel.getValueAt(row, 0).toString();
		String tenThue = tableModel.getValueAt(row, 1).toString();
		int choice = JOptionPane.showConfirmDialog(this,
				"Bạn có chắc muốn xóa thuế \"" + tenThue + "\"?\n" + "⚠️ Lưu ý", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			if (thueDAO.delete(maThue)) {
				JOptionPane.showMessageDialog(this, "✅ Xóa thuế thành công!");
				loadData();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "❌ Không thể xóa Thuế!\n", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleSua() {
		int row = tableThue.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn thuế để sửa");
			return;
		}
		
		if (!validateFrom()) return;
		
		Thue thue = getThueFromForm();
		if (thueDAO.update(thue)) {
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
		Thue thue = getThueFromForm();
		if (thueDAO.insert(thue)) {
			JOptionPane.showMessageDialog(this, "Thêm thuế thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this, "Thêm thuế thất bại do bị trùng mã: " + thue.getMaThue() + "!",
					"Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}

	private Thue getThueFromForm() {
		Thue thue = new Thue();
		thue.setMaThue(txtMaThue.getText().trim());
		thue.setTenThue(txtTenThue.getText().trim());
		try {
			thue.setPhanTram(Float.parseFloat(txtPhanTram.getText().trim()));
		} catch (NumberFormatException e) {
			thue.setPhanTram(0f);
		}
		thue.setMoTa(txtMoTa.getText().trim());
		return thue;
	}

	private boolean validateFrom() {
		if (txtMaThue.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã thuế!");
			txtMaThue.requestFocus();
			return false;
		}
		if (txtTenThue.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên thuế!");
			txtTenThue.requestFocus();
			return false;
		}
		if (txtPhanTram.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập phần trăm thuế!");
			txtPhanTram.requestFocus();
			return false;
		}
		try {
			float phanTram = Float.parseFloat(txtPhanTram.getText().trim());
			if (phanTram < 0) {
				JOptionPane.showMessageDialog(this, "Phần trăm phải lớn hơn hoặc bằng 0!");
				txtPhanTram.requestFocus();
				return false;
			}
		} catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Phần trăm phải là một con số hợp lệ!");
			txtPhanTram.requestFocus();
			return false;
		}

		return true;
	}

	private void loadFormFromTable() {
		int selectedRow = tableThue.getSelectedRow();
		if (selectedRow == -1) {
			return;
		}
		String maThue = tableModel.getValueAt(selectedRow, 0).toString();
		Thue thue = thueDAO.getById(maThue);
		if (thue != null) {
			txtMaThue.setText(thue.getMaThue());
			txtTenThue.setText(thue.getTenThue());
			txtPhanTram.setText(String.valueOf(thue.getPhanTram()));
			txtMoTa.setText(thue.getMoTa());
			txtMaThue.setEditable(false);
		}
	}
}