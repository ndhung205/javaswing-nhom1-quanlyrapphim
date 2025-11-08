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

import dao.LoaiPhongDAO;
import entity.LoaiPhong;

public class LoaiPhongGUI extends JPanel {

	private JPanel formFields;
	private JTextField txtMaLoaiPhong;
	private JTextField txtTenLoaiPhong;
	private JTextField txtMoTa;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimKiem;
	private JButton btnTimKiem;
	private DefaultTableModel tableModel;
	private JTable tableLoaiPhong;
	private LoaiPhongDAO loaiPhongDAO;

	public LoaiPhongGUI() {
		loaiPhongDAO = new LoaiPhongDAO();
		intitComponent();
		loadData();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		List<LoaiPhong> list = loaiPhongDAO.getAllLoaiPhong();
		for (LoaiPhong loaiPhong : list) {
			tableModel.addRow(new Object[] { 
					loaiPhong.getMaLoaiPhong(), 
					loaiPhong.getTenLoaiPhong(),
					loaiPhong.getMoTa() 
			});
		}
	}

	private void intitComponent() {
		setSize(HEIGHT, WIDTH);
		setLayout(new BorderLayout(10, 10));

		// Header
		JPanel headerPanel = createHeaderPanel();
		add(headerPanel, BorderLayout.NORTH);

		// Left Panel - Form nhập liệu
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
		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI PHÒNG");
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
		searchPanel.add(new JLabel("Tìm kiếm theo tên loại phòng:"));
		txtTimKiem = new JTextField(20);
		searchPanel.add(txtTimKiem);
		btnTimKiem = new JButton("Tìm");
		btnTimKiem.addActionListener(e -> handleTimKiem());
		searchPanel.add(btnTimKiem);
		JButton btnShowAll = new JButton("Hiện tất cả");
		btnShowAll.addActionListener(e -> loadData());
		searchPanel.add(btnShowAll);

		// Table
		String[] columns = "Mã loại phòng;Tên loại phòng;Mô tả".split(";");
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tableLoaiPhong = new JTable(tableModel);
		tableLoaiPhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tableLoaiPhong.setRowHeight(25);

		// Double click to edit
		tableLoaiPhong.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtMaLoaiPhong.setEditable(false);
					loadFormFromTable();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tableLoaiPhong);

		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Info label
		JLabel lblInfo = new JLabel("Double-click vào loại phòng để chỉnh sửa");
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
		List<LoaiPhong> list = loaiPhongDAO.search(keyword);
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy loại phòng nào");
		} else {
			for (LoaiPhong loaiPhong : list) {
				tableModel.addRow(new Object[] { 
						loaiPhong.getMaLoaiPhong(), 
						loaiPhong.getTenLoaiPhong(),
						loaiPhong.getMoTa() 
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
		JLabel lblFormTitle = new JLabel("Thông tin loại phòng");
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
		formFields.add(new JLabel("Mã loại phòng: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtMaLoaiPhong = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Tên loại phòng: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtTenLoaiPhong = new JTextField(15), gbc);

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
		txtMaLoaiPhong.setText("");
		txtTenLoaiPhong.setText("");
		txtMoTa.setText("");
		txtMaLoaiPhong.setEditable(true);
		tableLoaiPhong.clearSelection();
	}

	private void handleXoa() {
		int row = tableLoaiPhong.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 loại phòng để xóa");
			return;
		}
		String maLP = tableModel.getValueAt(row, 0).toString();
		String tenLP = tableModel.getValueAt(row, 1).toString();
		int choice = JOptionPane.showConfirmDialog(this,
				"Bạn có chắc muốn xóa loại phòng \"" + tenLP + "\"?\n" + "⚠️ Lưu ý", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			if (loaiPhongDAO.delete(maLP)) {
				JOptionPane.showMessageDialog(this, "✅ Xóa loại phòng thành công!");
				loadData();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "❌ Không thể xóa Loại phòng!\n", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleSua() {
		int row = tableLoaiPhong.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn loại phòng để sửa");
			return;
		}
		
		if (!validateFrom()) return;
		
		LoaiPhong loaiPhong = getLoaiPhongFromForm();
		if (loaiPhongDAO.update(loaiPhong)) {
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
		LoaiPhong loaiPhong = getLoaiPhongFromForm();
		if (loaiPhongDAO.insert(loaiPhong)) {
			JOptionPane.showMessageDialog(this, "Thêm loại phòng thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this,
					"Thêm loại phòng thất bại do bị trùng mã: " + loaiPhong.getMaLoaiPhong() + "!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private LoaiPhong getLoaiPhongFromForm() {
		LoaiPhong loaiPhong = new LoaiPhong();
		loaiPhong.setMaLoaiPhong(txtMaLoaiPhong.getText().trim());
		loaiPhong.setTenLoaiPhong(txtTenLoaiPhong.getText().trim());
		loaiPhong.setMoTa(txtMoTa.getText().trim());
		return loaiPhong;
	}

	private boolean validateFrom() {
		if (txtMaLoaiPhong.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã loại phòng!");
			txtMaLoaiPhong.requestFocus();
			return false;
		}
		if (txtTenLoaiPhong.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại phòng!");
			txtTenLoaiPhong.requestFocus();
			return false;
		}
		return true;
	}

	private void loadFormFromTable() {
		int selectedRow = tableLoaiPhong.getSelectedRow();
		if (selectedRow == -1) {
			// Bỏ qua nếu không có hàng nào được chọn (trường hợp double-click vào header)
			return;
		}
		String maLPhong = tableModel.getValueAt(selectedRow, 0).toString();
		LoaiPhong loaiPhong = loaiPhongDAO.getById(maLPhong);
		if (loaiPhong != null) {
			txtMaLoaiPhong.setText(loaiPhong.getMaLoaiPhong());
			txtTenLoaiPhong.setText(loaiPhong.getTenLoaiPhong());
			txtMoTa.setText(loaiPhong.getMoTa());
			txtMaLoaiPhong.setEditable(false); // Khi sửa thì không cho sửa mã
		}
	}
}