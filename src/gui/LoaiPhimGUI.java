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
import java.lang.foreign.ValueLayout.OfBoolean;
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

import dao.LoaiPhimDAO;
import entity.LoaiPhim;

public class LoaiPhimGUI extends JPanel{
	
	
	private JPanel formFields;
	private JTextField txtMaloaiPhim;
	private JTextField txtTenloaiPhim;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtTimKiem;
	private JButton btnTimKiem;
	private DefaultTableModel tableModel;
	private JTable tablePhim;
	private LoaiPhimDAO loaiPhimDAO;
	
	public LoaiPhimGUI() {
		// TODO Auto-generated constructor stub
		loaiPhimDAO = new LoaiPhimDAO();
		intitComponent();
		loadData();
	}

	private void loadData() {
		// TODO Auto-generated method stub
		tableModel.setRowCount(0);
		List<LoaiPhim> list = loaiPhimDAO.getAllLoaiPhim();
		
		for (LoaiPhim loaiPhim : list) {
			tableModel.addRow(new Object[] {
					loaiPhim.getMaLoaiPhim(),
					loaiPhim.getTenLoaiPhim()
			});
		}
	}

	private void intitComponent() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout());
		
		panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
		JLabel lblTitle = new JLabel("QUẢN LÝ LOẠI PHIM");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        panel.add(lblTitle, BorderLayout.WEST);
        
        return panel;
	}

	private JPanel createTablePanel() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel(new BorderLayout(10, 10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Search
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        
        searchPanel.add( new JLabel("Tìm kiếm theo tên loại phim:"));
        txtTimKiem = new JTextField(20);
        searchPanel.add(txtTimKiem);
        
        btnTimKiem = new JButton("Tìm");
        btnTimKiem.addActionListener(e -> handleTimKiem());
        searchPanel.add(btnTimKiem);
        
        JButton btnShowAll = new JButton("Hiện tất cả");
        btnShowAll.addActionListener(e -> loadData());
        searchPanel.add(btnShowAll);
        
        // Table
        String[] columns = "Mã loại phim;Tên loại phim".split(";");
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePhim = new JTable(tableModel);
        tablePhim.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePhim.setRowHeight(25);
        
        // Double click to edit
        tablePhim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	txtMaloaiPhim.setEditable(false);
                    loadFormFromTable();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablePhim);
        
        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JLabel lblInfo = new JLabel("Double-click vào loại phim để chỉnh sửa");
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblInfo, BorderLayout.SOUTH);
        
		return panel;
	}

	private void handleTimKiem() {
		// TODO Auto-generated method stub
		String keyword = txtTimKiem.getText().trim();
		
		if (keyword.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
		}
		
		tableModel.setRowCount(0);
		List<LoaiPhim> list = loaiPhimDAO.search(keyword);
		
		if (list.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy loại phim nào");
		} else {
			for (LoaiPhim loaiPhim : list) {
				tableModel.addRow(new Object[] {
						loaiPhim.getMaLoaiPhim(),
						loaiPhim.getTenLoaiPhim()
				});
			}
		}
	}

	private JPanel createFormPanel() {
		// TODO Auto-generated method stub
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(10,10));
		panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		panel.setBackground(Color.WHITE);
		
		// Title 
		JLabel lblFormTitle = new JLabel("Thông tin loại phim");
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
        formFields.add(new JLabel("Mã loại phim: *"), gbc);
        gbc.gridx = 1;
        formFields.add(txtMaloaiPhim = new JTextField(15), gbc);
        
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Tên loại phim: *"), gbc);
        gbc.gridx = 1;
        formFields.add(txtTenloaiPhim = new JTextField(15), gbc);
        
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
		// TODO Auto-generated method stub
		txtMaloaiPhim.setText("");
		txtTenloaiPhim.setText("");
		txtMaloaiPhim.setEditable(true);
	}

	private void handleXoa() {
		// TODO Auto-generated method stub
		int row = tablePhim.getSelectedRow();
		
		if (row == -1 ) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn 1 loại phim để xóa");
			return;
		}
		
		String maLP = tableModel.getValueAt(row, 0).toString();
		String tenLP = tableModel.getValueAt(row, 1).toString();
		
		int choice = JOptionPane.showConfirmDialog(this,
				"Bạn có chắc muốn xóa loại phim \"" + tenLP + "\"?\n" +
	            "⚠️ Lưu ý",
	            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	    
		if (choice == JOptionPane.YES_OPTION) {
			if (loaiPhimDAO.delete(maLP)) {
				JOptionPane.showMessageDialog(this, "✅ Xóa loại phim thành công!");
                loadData();
                clearForm();
			} else {
				JOptionPane.showMessageDialog(this,
	                    "❌ Không thể xóa Loại phim!\n",
	                    "Lỗi", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private void handleSua() {
		// TODO Auto-generated method stub
		int row = tablePhim.getSelectedRow();
		
		if (row == -1) {
			JOptionPane.showMessageDialog(this, "Vui lòng chọn loại phim để sửa");
			return;
		}
		
		LoaiPhim loaiPhim = getLoaiPhimFromForm();
		
		if (loaiPhimDAO.update(loaiPhim)) {
			JOptionPane.showMessageDialog(this, "Sửa thành công!");
			loadData();
			clearForm();
		} else {
			JOptionPane.showMessageDialog(this, "Không sửa được!");
		}
		
	}

	private void handleThem() {
		// TODO Auto-generated method stub
		if (!validateFrom()) return;
		
		LoaiPhim loaiPhim = getLoaiPhimFromForm();
		
		if (loaiPhimDAO.insert(loaiPhim)) {
			JOptionPane.showMessageDialog(this, "Thêm loại phim thành công!");
            loadData();
            clearForm();
		} else {
			JOptionPane.showMessageDialog(this, "Thêm loại phim thất bại do bị trùng mã phim: " + loaiPhim.getMaLoaiPhim() + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	private LoaiPhim getLoaiPhimFromForm() {
		LoaiPhim loaiPhim = new LoaiPhim();
		
		loaiPhim.setMaLoaiPhim(txtMaloaiPhim.getText().trim());
		loaiPhim.setTenLoaiPhim(txtTenloaiPhim.getText().trim());
		return loaiPhim;
	}

	private boolean validateFrom() {
		if (txtMaloaiPhim.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập mã loại phim!");
			txtMaloaiPhim.requestFocus();
            return false;
		}
		
		if (txtTenloaiPhim.getText().trim().isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập tên loại phim!");
			txtTenloaiPhim.requestFocus();
            return false;
		}
		
		return true;
	}

	private void loadFormFromTable() {
		// TODO Auto-generated method stub
		int selectedRow = tablePhim.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại phim cần sửa!");
            return;
        }
        
        String maLPhim = tableModel.getValueAt(selectedRow, 0).toString();
        
        LoaiPhim loaiPhim = loaiPhimDAO.getById(maLPhim);
        
        if (loaiPhim != null) {
        	txtMaloaiPhim.setText(loaiPhim.getMaLoaiPhim());
        	txtTenloaiPhim.setText(loaiPhim.getTenLoaiPhim());
        }
	}
}
