// ============================================
// GHE GUI - HÙNG PHỤ TRÁCH
// ============================================
package gui;

import dao.GheDAO;
import dao.LoaiGheDAO;
import dao.PhongDAO;
import entity.Ghe;
import entity.Phong;
import entity.LoaiGhe;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Giao diện quản lý Ghế
 * @author Hùng
 */
public class GheGUI extends JPanel {
    
    // Components
    private JTable tableGhe;
    private DefaultTableModel tableModel;
    private JTextField txtMaGhe;
    private JComboBox<Phong> cboPhong;
    private JComboBox<LoaiGhe> cboLoaiGhe;
    private JComboBox<String> cboTrangThai;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JButton btnTaoGheTuDong;
    private JLabel lblTongGhe;
    
    // DAO
    private GheDAO gheDAO;
    private PhongDAO phongDAO;
    private LoaiGheDAO loaiGheDAO;
    
    // Mode: "ADD" hoặc "EDIT"
    private String currentMode = "ADD";
    
    public GheGUI() {
        gheDAO = new GheDAO();
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
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ GHẾ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.BLACK);
        
        panel.add(lblTitle, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblFormTitle = new JLabel("Thông tin ghế");
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Form fields
        JPanel formFields = new JPanel(new GridBagLayout());
        formFields.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Phòng (filter)
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Chọn phòng: *"), gbc);
        gbc.gridx = 1;
        cboPhong = new JComboBox<>();
        loadPhong();
        cboPhong.addActionListener(e -> loadDataByPhong());
        formFields.add(cboPhong, gbc);
        
        row++;
        
        // Mã ghế
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Mã ghế: *"), gbc);
        gbc.gridx = 1;
        txtMaGhe = new JTextField(15);
        txtMaGhe.setToolTipText("VD: P01A01, P01A02...");
        formFields.add(txtMaGhe, gbc);
        
        row++;
        
        // Loại ghế
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Loại ghế: *"), gbc);
        gbc.gridx = 1;
        cboLoaiGhe = new JComboBox<>();
        loadLoaiGhe();
        formFields.add(cboLoaiGhe, gbc);
        
        row++;
        
        // Trạng thái
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Trạng thái: *"), gbc);
        gbc.gridx = 1;
        cboTrangThai = new JComboBox<>(new String[]{"Trống", "Đã đặt", "Đang sửa"});
        formFields.add(cboTrangThai, gbc);
        
        row++;
        
        // Separator
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        formFields.add(new JSeparator(), gbc);
        
        row++;
        
        // Auto-generate section
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblAuto = new JLabel("🤖 Tạo ghế tự động");
        lblAuto.setFont(new Font("Arial", Font.BOLD, 13));
        formFields.add(lblAuto, gbc);
        
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        btnTaoGheTuDong = new JButton("Tạo ghế tự động cho phòng");
        btnTaoGheTuDong.setBackground(new Color(230, 126, 34));
        btnTaoGheTuDong.setForeground(Color.WHITE);
        btnTaoGheTuDong.setFocusPainted(false);
        btnTaoGheTuDong.addActionListener(e -> showAutoGenerateDialog());
        formFields.add(btnTaoGheTuDong, gbc);
        
        row++;
        
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblNote = new JLabel("<html><i>💡 Tạo ghế theo pattern: A01-A10, B01-B10...</i></html>");
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
        
        // Top panel - Info
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);
        lblTongGhe = new JLabel("Tổng số ghế: 0");
        lblTongGhe.setFont(new Font("Arial", Font.BOLD, 13));
        topPanel.add(lblTongGhe);
        
        // Table
        String[] columns = {"Mã ghế", "Phòng", "Loại ghế", "Phụ thu", "Trạng thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableGhe = new JTable(tableModel);
        tableGhe.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableGhe.setRowHeight(25);
        tableGhe.getColumnModel().getColumn(0).setPreferredWidth(120);
        tableGhe.getColumnModel().getColumn(1).setPreferredWidth(150);
        tableGhe.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableGhe.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableGhe.getColumnModel().getColumn(4).setPreferredWidth(120);
        
        // Double click to edit
        tableGhe.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadFormFromTable();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableGhe);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JLabel lblInfo = new JLabel("Double-click vào ghế để chỉnh sửa");
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadPhong() {
        List<Phong> list = phongDAO.getAll();
        
        DefaultComboBoxModel<Phong> model = new DefaultComboBoxModel<>();
        for (Phong p : list) {
            model.addElement(p);
        }
        cboPhong.setModel(model);
    }
    
    private void loadLoaiGhe() {
    	loaiGheDAO = new LoaiGheDAO();
        List<LoaiGhe> list = loaiGheDAO.getAllLoaiGhe();
        
        DefaultComboBoxModel<LoaiGhe> model = new DefaultComboBoxModel<>();
        for (LoaiGhe lg : list) {
            model.addElement(lg);
        }
        cboLoaiGhe.setModel(model);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        ArrayList<Ghe> list = gheDAO.getAllGhe();
        
        for (Ghe ghe : list) {
            tableModel.addRow(new Object[]{
                ghe.getMaGhe(),
                ghe.getPhong().getTenPhong(),
                ghe.getLoaiGhe().getTenLoaiGhe(),
                String.format("%,.0f VNĐ", ghe.getLoaiGhe().getPhuThu()),
                ghe.getTrangThai()
            });
        }
        
        lblTongGhe.setText("Tổng số ghế: " + list.size());
    }
    
    private void loadDataByPhong() {
        Phong phong = (Phong) cboPhong.getSelectedItem();
        if (phong == null) return;
        
        tableModel.setRowCount(0);
        List<Ghe> list = gheDAO.getByPhong(phong.getMaPhong());
        
        for (Ghe ghe : list) {
            tableModel.addRow(new Object[]{
                ghe.getMaGhe(),
                ghe.getPhong().getTenPhong(),
                ghe.getLoaiGhe().getTenLoaiGhe(),
                String.format("%,.0f VNĐ", ghe.getLoaiGhe().getPhuThu()),
                ghe.getTrangThai()
            });
        }
        
        lblTongGhe.setText("Tổng số ghế trong phòng: " + list.size());
    }
    
    private void loadFormFromTable() {
        int selectedRow = tableGhe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế cần sửa!");
            return;
        }
        
        String maGhe = tableModel.getValueAt(selectedRow, 0).toString();
        Ghe ghe = gheDAO.getById(maGhe);
        
        if (ghe != null) {
            txtMaGhe.setText(ghe.getMaGhe());
            txtMaGhe.setEnabled(false);
            txtMaGhe.setBackground(new Color(240, 240, 240));
            
            // Set combo boxes
            for (int i = 0; i < cboPhong.getItemCount(); i++) {
                if (cboPhong.getItemAt(i).getMaPhong().equals(ghe.getPhong().getMaPhong())) {
                    cboPhong.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < cboLoaiGhe.getItemCount(); i++) {
                if (cboLoaiGhe.getItemAt(i).getMaLoaiGhe().equals(ghe.getLoaiGhe().getMaLoaiGhe())) {
                    cboLoaiGhe.setSelectedIndex(i);
                    break;
                }
            }
            
            cboTrangThai.setSelectedItem(ghe.getTrangThai());
            
            currentMode = "EDIT";
        }
    }
    
    private void handleThem() {
        if (!validateForm()) return;
        
        String maGhe = txtMaGhe.getText().trim();
        if (gheDAO.isMaGheExists(maGhe)) {
            JOptionPane.showMessageDialog(this, 
                "❌ Mã ghế \"" + maGhe + "\" đã tồn tại!\nVui lòng nhập mã khác.",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaGhe.requestFocus();
            txtMaGhe.selectAll();
            return;
        }
        
        Ghe ghe = getGheFromForm();
        
        if (gheDAO.insert(ghe)) {
            JOptionPane.showMessageDialog(this, "✅ Thêm ghế thành công!");
            loadDataByPhong();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm ghế thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSua() {
        if (txtMaGhe.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế cần sửa từ bảng!");
            return;
        }
        
        if (!validateForm()) return;
        
        Ghe ghe = getGheFromForm();
        
        int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn cập nhật ghế \"" + ghe.getMaGhe() + "\"?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (gheDAO.update(ghe)) {
                JOptionPane.showMessageDialog(this, "✅ Cập nhật ghế thành công!");
                loadDataByPhong();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cập nhật ghế thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void handleXoa() {
        int selectedRow = tableGhe.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế cần xóa!");
            return;
        }
        
        String maGhe = tableModel.getValueAt(selectedRow, 0).toString();
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa ghế \"" + maGhe + "\"?\n" +
            "⚠️ Lưu ý: Không thể xóa nếu ghế đã có vé!",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (gheDAO.delete(maGhe)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa ghế thành công!");
                loadDataByPhong();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Không thể xóa ghế!\nGhế có thể đã có vé.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAutoGenerateDialog() {
        Phong phong = (Phong) cboPhong.getSelectedItem();
        if (phong == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng!");
            return;
        }
        
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(new JLabel("Phòng:"));
        panel.add(new JLabel(phong.getTenPhong() + " " + phong.getSoLuongGhe() + " ghế"));
        
        panel.add(new JLabel("Số hàng (A-Z): *"));
        JSpinner spinHang = new JSpinner(new SpinnerNumberModel(10, 1, 26, 1));
        panel.add(spinHang);
        
        panel.add(new JLabel("Số ghế/hàng: *"));
        panel.add(new JLabel("10"));
        
        panel.add(new JLabel("Loại ghế mặc định:"));
        JComboBox<LoaiGhe> cboLoaiGheDefault = new JComboBox<>();
        for (int i = 0; i < cboLoaiGhe.getItemCount(); i++) {
            cboLoaiGheDefault.addItem(cboLoaiGhe.getItemAt(i));
        }
        panel.add(cboLoaiGheDefault);
        
        int result = JOptionPane.showConfirmDialog(this, panel, 
            "Tạo ghế tự động - " + phong.getTenPhong(), 
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            int soHang = (int) spinHang.getValue();
            LoaiGhe loaiGhe = (LoaiGhe) cboLoaiGheDefault.getSelectedItem();
            
            int totalGhe = soHang * 10;
            
            if (totalGhe > phong.getSoLuongGhe()) {
            	JOptionPane.showMessageDialog(this, "Số lượng ghế muốn tạo vượt quá số lượng cho phép là: " + phong.getSoLuongGhe());
            	return;
            }
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "Sẽ tạo " + totalGhe + " ghế cho phòng " + phong.getTenPhong() + "\n" +
                "Pattern: " + phong.getMaPhong() + "A01, " + phong.getMaPhong() + "A02...\n\n" +
                "Tiếp tục?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                int created = gheDAO.generateGheForPhong(
                    phong.getMaPhong(), 
                    soHang, 
                    10, 
                    loaiGhe.getMaLoaiGhe()
                );
                
                JOptionPane.showMessageDialog(this, 
                    "✅ Đã tạo " + created + " ghế thành công!");
                loadDataByPhong();
            }
        }
    }
    
    private boolean validateForm() {
        if (txtMaGhe.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã ghế!");
            txtMaGhe.requestFocus();
            return false;
        }
        
        if (cboPhong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng!");
            return false;
        }
        
        if (cboLoaiGhe.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại ghế!");
            return false;
        }
        
        return true;
    }
    
    private Ghe getGheFromForm() {
        Ghe ghe = new Ghe();
        ghe.setMaGhe(txtMaGhe.getText().trim());
        ghe.setPhong((Phong) cboPhong.getSelectedItem());
        ghe.setLoaiGhe((LoaiGhe) cboLoaiGhe.getSelectedItem());
        ghe.setTrangThai((String) cboTrangThai.getSelectedItem());
        return ghe;
    }
    
    private void clearForm() {
        txtMaGhe.setText("");
        txtMaGhe.setEnabled(true);
        txtMaGhe.setBackground(Color.WHITE);
        
        cboLoaiGhe.setSelectedIndex(0);
        cboTrangThai.setSelectedIndex(0);
        currentMode = "ADD";
        txtMaGhe.requestFocus();
    }
    
    // Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GheGUI gui = new GheGUI();
            gui.setVisible(true);
        });
    }
}