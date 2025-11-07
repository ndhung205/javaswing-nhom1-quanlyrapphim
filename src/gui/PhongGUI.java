// ============================================
// PHONG GUI - HÙNG PHỤ TRÁCH
// ============================================
package gui;

import dao.LoaiPhongDAO;
import dao.PhongDAO;
import entity.Phong;
import entity.LoaiPhim;
import entity.LoaiPhong;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.Set;

/**
 * Giao diện quản lý Phòng chiếu
 * @author Hùng
 */
public class PhongGUI extends JPanel {
    
    // Components
    private JTable tablePhong;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhong, txtTenPhong, txtTimKiem, txtSoLuongGhe;
    private JComboBox<LoaiPhong> cboLoaiPhong;
    private JCheckBox chkTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;
    
    // DAO
    private PhongDAO phongDAO;
    private LoaiPhongDAO loaiPhongDAO;
    
    // Mode: "ADD" hoặc "EDIT"
    private String currentMode = "ADD";
	private JLabel lblLoc;
	private JComboBox<LoaiPhong> cboLocLoaiPhong;
	private JComboBox<String> cboLocTrangThai;
	private JLabel lblSearch;
    
    public PhongGUI() {
        phongDAO = new PhongDAO();
        initComponents();
        loadData();
    }
    
    private void initComponents() {
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
        splitPane.setDividerLocation(380);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(52, 152, 219));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ PHÒNG CHIẾU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        panel.add(lblTitle, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblFormTitle = new JLabel("Thông tin phòng chiếu");
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Form fields
        JPanel formFields = new JPanel(new GridBagLayout());
        formFields.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Mã phòng
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Mã phòng: *"), gbc);
        gbc.gridx = 1;
        txtMaPhong = new JTextField(15);
        txtMaPhong.setToolTipText("Nhập mã phòng (VD: P01, ROOM1...)");
        formFields.add(txtMaPhong, gbc);
        
        row++;
        
        // Tên phòng
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Tên phòng: *"), gbc);
        gbc.gridx = 1;
        txtTenPhong = new JTextField(15);
        formFields.add(txtTenPhong, gbc);
        
        row++;
        
        // Loại phòng
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Loại phòng: *"), gbc);
        gbc.gridx = 1;
        cboLoaiPhong = new JComboBox<>();
        loadLoaiPhong(cboLoaiPhong);
        formFields.add(cboLoaiPhong, gbc);
        
        row++;
        
        // Số lượng ghế
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Số lượng ghế: *"), gbc);
        gbc.gridx = 1;
        txtSoLuongGhe = new JTextField(15);
        txtSoLuongGhe.setToolTipText("Số ghế trong phòng (VD: 100, 150...)");
        formFields.add(txtSoLuongGhe, gbc);
        
        row++;
        
        // Trạng thái
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Trạng thái:"), gbc);
        gbc.gridx = 1;
        chkTrangThai = new JCheckBox("Đang hoạt động");
        chkTrangThai.setSelected(true);
        chkTrangThai.setBackground(Color.WHITE);
        formFields.add(chkTrangThai, gbc);
        
        row++;
        
        // Note
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblNote = new JLabel("<html><i>💡 Lưu ý: Bỏ tick nếu phòng đang bảo trì</i></html>");
        lblNote.setFont(new Font("Arial", Font.PLAIN, 11));
        lblNote.setForeground(Color.GRAY);
        formFields.add(lblNote, gbc);
        
        // Buttons Panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        
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
        
        btnPanel.add(btnThem);
        btnPanel.add(btnSua);
        btnPanel.add(btnXoa);
        btnPanel.add(btnLamMoi);
        
        // Assemble form panel
        panel.add(lblFormTitle, BorderLayout.NORTH);
        panel.add(formFields, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        JPanel xuly = new JPanel();
        xuly.setLayout(new BoxLayout(xuly, BoxLayout.Y_AXIS));
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        
        searchPanel.add(lblSearch = new JLabel("Tìm kiếm theo tên phòng:"));
        txtTimKiem = new JTextField(20);
        searchPanel.add(txtTimKiem);
        
        btnTimKiem = new JButton("🔍 Tìm");
        btnTimKiem.addActionListener(e -> handleTimKiem());
        searchPanel.add(btnTimKiem);
        
        JButton btnShowAll = new JButton("Hiện tất cả");
        btnShowAll.addActionListener(e -> loadData());
        searchPanel.add(btnShowAll);
        
        JPanel loc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loc.setBackground(Color.WHITE);
        
        loc.add(lblLoc = new JLabel("Lọc: "));
        
        cboLocLoaiPhong = new JComboBox<LoaiPhong>();
        loadLoaiPhong(cboLocLoaiPhong);
        cboLocLoaiPhong.addActionListener(e -> locLoaiPhong());
        loc.add(cboLocLoaiPhong);
        
        cboLocTrangThai = new JComboBox<String>();
        loadTrangThai(cboLocTrangThai);
        cboLocTrangThai.addActionListener(e -> locTrangThai());
        loc.add(cboLocTrangThai);
        
        lblLoc.setPreferredSize(lblSearch.getPreferredSize());
        
        xuly.add(searchPanel);
        xuly.add(loc);
        
        // Table
        String[] columns = {"Mã phòng", "Tên phòng", "Loại phòng", "Số ghế", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePhong = new JTable(tableModel);
        tablePhong.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePhong.setRowHeight(25);
        tablePhong.getColumnModel().getColumn(0).setPreferredWidth(100);
        tablePhong.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablePhong.getColumnModel().getColumn(2).setPreferredWidth(150);
        tablePhong.getColumnModel().getColumn(3).setPreferredWidth(80);
        tablePhong.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        // Double click to edit
        tablePhong.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadFormFromTable();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablePhong);
        
        panel.add(xuly, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JLabel lblInfo = new JLabel("Double-click vào phòng để chỉnh sửa");
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void locTrangThai() {
		// TODO Auto-generated method stub
    	String tt = (String) cboLocTrangThai.getSelectedItem();
    	
    	phongDAO = new PhongDAO();
    	int ttInt = tt.equalsIgnoreCase("Hoạt động") ? 1 : 0;
    	List<Phong> list = phongDAO.getPhongByTrangThai(ttInt);
    	
    	tableModel.setRowCount(0);
    	for (Phong phong : list) {
    		String trangThai = phong.isTrangThai() ? "Hoạt động" : "Bảo trì";
            
            tableModel.addRow(new Object[]{
                phong.getMaPhong(),
                phong.getTenPhong(),
                phong.getLoaiPhong().getTenLoaiPhong(),
                phong.getSoLuongGhe(),
                trangThai
            });
		}
	}

	private void loadTrangThai(JComboBox<String> cboLocTrangThai2) {
		// TODO Auto-generated method stub
		phongDAO = new PhongDAO();
		Set<Boolean> list = phongDAO.getTrangThai();
		
		for (Boolean boolean1 : list) {
			cboLocTrangThai2.addItem(boolean1 == true ? "Hoạt động" : "Bảo trì");
		}
		
	}

	private void locLoaiPhong() {
		// TODO Auto-generated method stub
		phongDAO = new PhongDAO();
		LoaiPhong loaiPhong = (LoaiPhong) cboLocLoaiPhong.getSelectedItem();
		List<Phong> list = phongDAO.getPhongByLoaiPhong(loaiPhong);
		
		tableModel.setRowCount(0);
    	for (Phong phong : list) {
    		String trangThai = phong.isTrangThai() ? "Hoạt động" : "Bảo trì";
            
            tableModel.addRow(new Object[]{
                phong.getMaPhong(),
                phong.getTenPhong(),
                phong.getLoaiPhong().getTenLoaiPhong(),
                phong.getSoLuongGhe(),
                trangThai
            });
		}
		
	}

	private void loadLoaiPhong(JComboBox<LoaiPhong> cbo) {
        // Mock data - Tuần 3 thay bằng LookupService.getAllLoaiPhong()
    	loaiPhongDAO = new LoaiPhongDAO();
        List<LoaiPhong> list = loaiPhongDAO.getAllLoaiPhong();
        
        DefaultComboBoxModel<LoaiPhong> model = new DefaultComboBoxModel<>();
        for (LoaiPhong lp : list) {
            model.addElement(lp);
        }
        cbo.setModel(model);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Phong> list = phongDAO.getAll();
        
        for (Phong phong : list) {
            String trangThai = phong.isTrangThai() ? "Hoạt động" : "Bảo trì";
            
            tableModel.addRow(new Object[]{
                phong.getMaPhong(),
                phong.getTenPhong(),
                phong.getLoaiPhong().getTenLoaiPhong(),
                phong.getSoLuongGhe(),
                trangThai
            });
        }
    }
    
    private void loadFormFromTable() {
        int selectedRow = tablePhong.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần sửa!");
            return;
        }
        
        String maPhong = tableModel.getValueAt(selectedRow, 0).toString();
        Phong phong = phongDAO.findPhongByMa(maPhong);
        
        if (phong != null) {
            txtMaPhong.setText(phong.getMaPhong());
            txtMaPhong.setEnabled(false);
            txtMaPhong.setBackground(new Color(240, 240, 240));
            
            txtTenPhong.setText(phong.getTenPhong());
            txtSoLuongGhe.setText(String.valueOf(phong.getSoLuongGhe()));
            chkTrangThai.setSelected(phong.isTrangThai());
            
            // Set combo box
            for (int i = 0; i < cboLoaiPhong.getItemCount(); i++) {
                LoaiPhong lp = cboLoaiPhong.getItemAt(i);
                if (lp.getMaLoaiPhong().equals(phong.getLoaiPhong().getMaLoaiPhong())) {
                    cboLoaiPhong.setSelectedIndex(i);
                    break;
                }
            }
            
            currentMode = "EDIT";
        }
    }
    
    private void handleThem() {
        if (!validateForm()) return;
        
        // Kiểm tra mã phòng đã tồn tại chưa
        String maPhong = txtMaPhong.getText().trim();
        if (phongDAO.isMaPhongExists(maPhong)) {
            JOptionPane.showMessageDialog(this, 
                "❌ Mã phòng \"" + maPhong + "\" đã tồn tại!\nVui lòng nhập mã khác.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPhong.requestFocus();
            txtMaPhong.selectAll();
            return;
        }
        
        Phong phong = getPhongFromForm();
        
        if (phongDAO.insert(phong)) {
            JOptionPane.showMessageDialog(this, "✅ Thêm phòng thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSua() {
        if (txtMaPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần sửa từ bảng!");
            return;
        }
        
        if (!validateForm()) return;
        
        Phong phong = getPhongFromForm();
        
        int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn cập nhật phòng \"" + phong.getTenPhong() + "\"?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (phongDAO.update(phong)) {
                JOptionPane.showMessageDialog(this, "✅ Cập nhật phòng thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cập nhật phòng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleXoa() {
        int selectedRow = tablePhong.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng cần xóa!");
            return;
        }
        
        String maPhong = tableModel.getValueAt(selectedRow, 0).toString();
        String tenPhong = tableModel.getValueAt(selectedRow, 1).toString();
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa phòng \"" + tenPhong + "\"?\n" +
            "⚠️ Lưu ý: Không thể xóa nếu phòng đã có ghế hoặc lịch chiếu!",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (phongDAO.delete(maPhong)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa phòng thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Không thể xóa phòng!\n" +
                    "Phòng có thể đang có ghế hoặc lịch chiếu.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleTimKiem() {
        String keyword = txtTimKiem.getText().trim();
        
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }
        
        tableModel.setRowCount(0);
        List<Phong> list = phongDAO.search(keyword);
        
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phòng nào!");
        } else {
            for (Phong phong : list) {
                String trangThai = phong.isTrangThai() ? "✅ Hoạt động" : "🔧 Bảo trì";
                
                tableModel.addRow(new Object[]{
                    phong.getMaPhong(),
                    phong.getTenPhong(),
                    phong.getLoaiPhong().getTenLoaiPhong(),
                    phong.getSoLuongGhe(),
                    trangThai
                });
            }
        }
    }
    
    private boolean validateForm() {
        // Validate mã phòng
        if (txtMaPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phòng!");
            txtMaPhong.requestFocus();
            return false;
        }
        
        if (txtTenPhong.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phòng!");
            txtTenPhong.requestFocus();
            return false;
        }
        
        if (cboLoaiPhong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại phòng!");
            return false;
        }
        
        try {
            int soGhe = Integer.parseInt(txtSoLuongGhe.getText().trim());
            if (soGhe <= 0) {
                JOptionPane.showMessageDialog(this, "Số lượng ghế phải lớn hơn 0!");
                txtSoLuongGhe.requestFocus();
                return false;
            }
            if (soGhe > 500) {
                JOptionPane.showMessageDialog(this, "Số lượng ghế không được vượt quá 500!");
                txtSoLuongGhe.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Số lượng ghế phải là số nguyên!");
            txtSoLuongGhe.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private Phong getPhongFromForm() {
        Phong phong = new Phong();
        phong.setMaPhong(txtMaPhong.getText().trim());
        phong.setTenPhong(txtTenPhong.getText().trim());
        phong.setLoaiPhong((LoaiPhong) cboLoaiPhong.getSelectedItem());
        phong.setSoLuongGhe(Integer.parseInt(txtSoLuongGhe.getText().trim()));
        phong.setTrangThai(chkTrangThai.isSelected());
        return phong;
    }
    
    private void clearForm() {
        txtMaPhong.setText("");
        txtMaPhong.setEnabled(true);
        txtMaPhong.setBackground(Color.WHITE);
        
        txtTenPhong.setText("");
        txtSoLuongGhe.setText("");
        chkTrangThai.setSelected(true);
        cboLoaiPhong.setSelectedIndex(0);
        currentMode = "ADD";
        txtMaPhong.requestFocus();
    }
    
    // Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhongGUI gui = new PhongGUI();
            gui.setVisible(true);
        });
    }
}