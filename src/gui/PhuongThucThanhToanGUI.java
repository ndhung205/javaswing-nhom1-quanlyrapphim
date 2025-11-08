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

import dao.PhuongThucThanhToanDAO;
import entity.PhuongThucThanhToan;

public class PhuongThucThanhToanGUI extends JPanel {

	private JPanel formFields;
	private JTextField txtMaPhuongThuc;
	private JTextField txtTenPhuongThuc;
	private JTextField txtMoTa;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimKiem;
	private JButton btnTimKiem;
	private DefaultTableModel tableModel;
	private JTable tablePTTT;
	private PhuongThucThanhToanDAO ptttDAO;

	public PhuongThucThanhToanGUI() {
		ptttDAO = new PhuongThucThanhToanDAO();
		intitComponent();
		loadData();
	}

	private void loadData() {
		tableModel.setRowCount(0);
		List<PhuongThucThanhToan> list = ptttDAO.getAllPhuongThucThanhToan();
		for (PhuongThucThanhToan pttt : list) {
			tableModel.addRow(new Object[] { 
					pttt.getMaPhuongThuc(), 
					pttt.getTenPhuongThuc(), 
					pttt.getMoTa() 
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
		JLabel lblTitle = new JLabel("QUẢN LÝ PHƯƠNG THỨC THANH TOÁN");
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
		searchPanel.add(new JLabel("Tìm kiếm theo tên phương thức:"));
		txtTimKiem = new JTextField(20);
		searchPanel.add(txtTimKiem);
		btnTimKiem = new JButton("Tìm");
		btnTimKiem.addActionListener(e -> handleTimKiem());
		searchPanel.add(btnTimKiem);
		JButton btnShowAll = new JButton("Hiện tất cả");
		btnShowAll.addActionListener(e -> loadData());
		searchPanel.add(btnShowAll);

		// Table
		String[] columns = "Mã PTTT;Tên phương thức;Mô tả".split(";");
		tableModel = new DefaultTableModel(columns, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		tablePTTT = new JTable(tableModel);
		tablePTTT.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tablePTTT.setRowHeight(25);

		// Double click to edit
		tablePTTT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					txtMaPhuongThuc.setEditable(false);
					loadFormFromTable();
				}
			}
		});
		JScrollPane scrollPane = new JScrollPane(tablePTTT);

		panel.add(searchPanel, BorderLayout.NORTH);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Info label
		JLabel lblInfo = new JLabel("Double-click vào phương thức để chỉnh sửa");
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
		List<PhuongThucThanhToan> list = ptttDAO.search(keyword);
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy PTTT nào");
		} else {
			for (PhuongThucThanhToan pttt : list) {
				tableModel.addRow(new Object[] { 
						pttt.getMaPhuongThuc(), 
						pttt.getTenPhuongThuc(),
						pttt.getMoTa() 
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
		JLabel lblFormTitle = new JLabel("Thông tin phương thức thanh toán");
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
		formFields.add(new JLabel("Mã PTTT: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtMaPhuongThuc = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Tên phương thức: *"), gbc);
		gbc.gridx = 1;
		formFields.add(txtTenPhuongThuc = new JTextField(15), gbc);

		row++;
		gbc.gridx = 0; gbc.gridy = row;
		formFields.add(new JLabel("Mô tả: *"), gbc);
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
		txtMaPhuongThuc.setText("");
		txtTenPhuongThuc.setText("");
		txtMoTa.setText("");
		txtMaPhuongThuc.setEditable(true);
		tablePTTT.clearSelection();
	}

	private void handleXoa() {
		int row = tablePTTT.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 PTTT để xóa");
			return;
		}
		String maPTTT = tableModel.getValueAt(row, 0).toString();
		String tenPTTT = tableModel.getValueAt(row, 1).toString();
		int choice = JOptionPane.showConfirmDialog(this,
				"Bạn có chắc muốn xóa PTTT \"" + tenPTTT + "\"?\n" + "⚠️ Lưu ý", "Xác nhận xóa",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
		if (choice == JOptionPane.YES_OPTION) {
			if (ptttDAO.delete(maPTTT)) {
				JOptionPane.showMessageDialog(this, "✅ Xóa PTTT thành công!");
				loadData();
				clearForm();
			} else {
				JOptionPane.showMessageDialog(this, "❌ Không thể xóa PTTT!\n", "Lỗi",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleSua() {
		int row = tablePTTT.getSelectedRow();
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn PTTT để sửa");
			return;
		}
		
		if (!validateFrom()) return;
		
		PhuongThucThanhToan pttt = getPhuongThucThanhToanFromForm();
		if (ptttDAO.update(pttt)) {
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
		PhuongThucThanhToan pttt = getPhuongThucThanhToanFromForm();
		if (ptttDAO.insert(pttt)) {
			JOptionPane.showMessageDialog(this, "Thêm PTTT thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this,
					"Thêm PTTT thất bại do bị trùng mã: " + pttt.getMaPhuongThuc() + "!", "Lỗi",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private PhuongThucThanhToan getPhuongThucThanhToanFromForm() {
		PhuongThucThanhToan pttt = new PhuongThucThanhToan();
		pttt.setMaPhuongThuc(txtMaPhuongThuc.getText().trim());
		pttt.setTenPhuongThuc(txtTenPhuongThuc.getText().trim());
		pttt.setMoTa(txtMoTa.getText().trim());
		return pttt;
	}

	private boolean validateFrom() {
		if (txtMaPhuongThuc.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã PTTT!");
			txtMaPhuongThuc.requestFocus();
			return false;
		}
		if (txtTenPhuongThuc.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên PTTT!");
			txtTenPhuongThuc.requestFocus();
			return false;
		}
		if (txtMoTa.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mô tả!");
			txtMoTa.requestFocus();
			return false;
		}
		return true;
	}

	private void loadFormFromTable() {
		int selectedRow = tablePTTT.getSelectedRow();
		if (selectedRow == -1) {
			return;
		}
		String maPTTT = tableModel.getValueAt(selectedRow, 0).toString();
		PhuongThucThanhToan pttt = ptttDAO.getById(maPTTT);
		if (pttt != null) {
			txtMaPhuongThuc.setText(pttt.getMaPhuongThuc());
			txtTenPhuongThuc.setText(pttt.getTenPhuongThuc());
			txtMoTa.setText(pttt.getMoTa());
			txtMaPhuongThuc.setEditable(false);
		}
	}
}