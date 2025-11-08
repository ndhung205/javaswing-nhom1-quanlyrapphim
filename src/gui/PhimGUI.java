// ============================================
// PHIM GUI - HÙNG PHỤ TRÁCH
// ============================================
package gui;

import dao.LoaiPhimDAO;
import dao.PhimDAO;
import entity.Phim;
import entity.LoaiPhim;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Giao diện quản lý Phim
 * @author Hùng
 */
public class PhimGUI extends JPanel {
    
    // Components
    private JTable tablePhim;
    private DefaultTableModel tableModel;
    private JTextField txtMaPhim, txtTenPhim, txtTimKiem, txtThoiLuong, txtNamPhatHanh;
    private JTextArea txtMoTa;
    private JComboBox<LoaiPhim> cboLoaiPhim;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    // DAO
    private PhimDAO phimDAO;
    private LoaiPhimDAO loaiPhimDAO;
    
    // Mode: "ADD" hoặc "EDIT"
    private String currentMode = "ADD";
	private JLabel lblSearch;
	private JLabel lblLoc;
	private JComboBox<LoaiPhim> cboLocLoaiPhim;
	private JComboBox<Integer> cboLocNam;
	private JPanel formFields;
	private JTextField txtPoster;
    
    public PhimGUI() {
        phimDAO = new PhimDAO();
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
        splitPane.setDividerLocation(420);
        add(splitPane, BorderLayout.CENTER);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(41, 128, 185));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("QUẢN LÝ PHIM");
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
        JLabel lblFormTitle = new JLabel("Thông tin phim");
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Form fields
        formFields = new JPanel(new GridBagLayout());
        formFields.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Mã phim
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Mã phim: *"), gbc);
        gbc.gridx = 1;
        txtMaPhim = new JTextField(15);
        formFields.add(txtMaPhim, gbc);
        
        row++;
        
        // Tên phim
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Tên phim: *"), gbc);
        gbc.gridx = 1;
        txtTenPhim = new JTextField(15);
        formFields.add(txtTenPhim, gbc);
        
        row++;
        
        // Loại phim
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Loại phim: *"), gbc);
        gbc.gridx = 1;
        cboLoaiPhim = new JComboBox<>();
        loadLoaiPhim(cboLoaiPhim);
        formFields.add(cboLoaiPhim, gbc);
        
        row++;
        
        // Thời lượng chiếu
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Thời lượng (phút): *"), gbc);
        gbc.gridx = 1;
        txtThoiLuong = new JTextField(15);
        formFields.add(txtThoiLuong, gbc);
        
        row++;
        
        // Năm phát hành
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Năm phát hành: *"), gbc);
        gbc.gridx = 1;
        txtNamPhatHanh = new JTextField(15);
        formFields.add(txtNamPhatHanh, gbc);
        
        row++;
        
        // Mô tả
        gbc.gridx = 0; gbc.gridy = row;
        gbc.anchor = GridBagConstraints.NORTH;
        formFields.add(new JLabel("Mô tả:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1;
        gbc.weighty = 1;
        txtMoTa = new JTextArea(5, 15);
        txtMoTa.setLineWrap(true);
        txtMoTa.setWrapStyleWord(true);
        JScrollPane scrollMoTa = new JScrollPane(txtMoTa);
        formFields.add(scrollMoTa, gbc);
          
        // =======================
        // Poster (chỉ hiển thị đường dẫn)
        // =======================
        row++;
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.NONE;
        formFields.add(new JLabel("Poster:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0;

        JPanel panelPoster = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelPoster.setBackground(Color.WHITE);

        // Nút chọn ảnh
        JButton btnChonPoster = new JButton("Chọn ảnh");
        panelPoster.add(btnChonPoster);
        
        txtPoster = new JTextField(15);
        txtPoster.setEditable(false);
        panelPoster.add(txtPoster);
        formFields.add(panelPoster, gbc);
        
        // Sự kiện chọn ảnh
        btnChonPoster.addActionListener(e -> moAnh());
        
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
    
    private void moAnh() {
        // Lấy đường dẫn tuyệt đối tới thư mục project
        File baseDir = new File(System.getProperty("user.dir"), "img/posters");
        if (!baseDir.exists()) baseDir.mkdirs(); // Tạo nếu chưa có

        JFileChooser chooser = new JFileChooser(baseDir);
        chooser.setDialogTitle("Chọn ảnh poster");
        chooser.setFileFilter(new FileNameExtensionFilter("Hình ảnh", "jpg", "jpeg", "png", "gif"));

        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            File selectedFile = chooser.getSelectedFile();
            File destFile = new File(baseDir, selectedFile.getName());

            try {
                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                txtPoster.setText("img/posters/" + selectedFile.getName());
                JOptionPane.showMessageDialog(this, "✅ Đã chọn ảnh: " + selectedFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "❌ Lỗi khi sao chép ảnh: " + e.getMessage());
            }
        }
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
        
        searchPanel.add(lblSearch = new JLabel("Tìm kiếm theo tên phim hoặc mô tả:"));
        txtTimKiem = new JTextField(20);
        searchPanel.add(txtTimKiem);
        
        btnTimKiem = new JButton("Tìm");
        btnTimKiem.addActionListener(e -> handleTimKiem());
        searchPanel.add(btnTimKiem);
        
        JButton btnShowAll = new JButton("Hiện tất cả");
        btnShowAll.addActionListener(e -> loadData());
        searchPanel.add(btnShowAll);
        
        JPanel loc = new JPanel(new FlowLayout(FlowLayout.LEFT));
        loc.setBackground(Color.WHITE);
        
        loc.add(lblLoc = new JLabel("Lọc: "));
        
        cboLocLoaiPhim = new JComboBox<LoaiPhim>();
        loadLoaiPhim(cboLocLoaiPhim);
        cboLocLoaiPhim.addActionListener(e -> locLoaiPhim());
        loc.add(cboLocLoaiPhim);
        
        cboLocNam = new JComboBox<Integer>();
        loadNamPhatHanh(cboLocNam);
        cboLocNam.addActionListener(e -> locNamPhatHanh());
        loc.add(cboLocNam);
        
        lblLoc.setPreferredSize(lblSearch.getPreferredSize());
        
        xuly.add(searchPanel);
        xuly.add(loc);
        
        // Table
        String[] columns = {"Mã phim", "Tên phim", "Loại phim", "Thời lượng (phút)", "Năm", "Mô tả", "Poster"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablePhim = new JTable(tableModel);
        tablePhim.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablePhim.setRowHeight(25);
        tablePhim.getColumnModel().getColumn(0).setPreferredWidth(80);
        tablePhim.getColumnModel().getColumn(1).setPreferredWidth(200);
        tablePhim.getColumnModel().getColumn(2).setPreferredWidth(100);
        tablePhim.getColumnModel().getColumn(3).setPreferredWidth(100);
        tablePhim.getColumnModel().getColumn(4).setPreferredWidth(60);
        tablePhim.getColumnModel().getColumn(5).setPreferredWidth(250);
        
        // Double click to edit
        tablePhim.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                	txtMaPhim.setEditable(false);
                    loadFormFromTable();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tablePhim);
        
        panel.add(xuly, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Info label
        JLabel lblInfo = new JLabel("Double-click vào phim để chỉnh sửa");
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void locNamPhatHanh() {
		// TODO Auto-generated method stub
    	Integer nam = (Integer) cboLocNam.getSelectedItem();
    	phimDAO = new PhimDAO();
		List<Phim> list = phimDAO.getByNam(nam);
		tableModel.setRowCount(0);
		for (Phim phim : list) {
			tableModel.addRow(new Object[] {
					phim.getMaPhim(), phim.getTenPhim(), phim.getLoaiPhim().getTenLoaiPhim(), 
					phim.getThoiLuongChieu(), phim.getNamPhatHanh(), phim.getMoTa(), phim.getPoster()
			});
		}
	}

	private void locLoaiPhim() {
    	LoaiPhim lp = (LoaiPhim) cboLocLoaiPhim.getSelectedItem();
		phimDAO = new PhimDAO();
		List<Phim> list = phimDAO.getByLoaiPhim(lp);
		tableModel.setRowCount(0);
		for (Phim phim : list) {
			tableModel.addRow(new Object[] {
					phim.getMaPhim(), phim.getTenPhim(), phim.getLoaiPhim().getTenLoaiPhim(), phim.getThoiLuongChieu(), phim.getNamPhatHanh(), phim.getMoTa()
			});
		}		
	}
    
    private void loadNamPhatHanh(JComboBox<Integer> cbo) {
		// TODO Auto-generated method stub
    	phimDAO = new PhimDAO();
    	Set<Integer> list = new TreeSet<Integer>(phimDAO.getNamPhatHanh());
    	for (Integer year : list) {
    	    cbo.addItem(year);
    	}
	}

	private void loadLoaiPhim(JComboBox<LoaiPhim> cboLoai) {
        // Mock data - Tuần 3 thay bằng LookupService.getAllLoaiPhim()
    	loaiPhimDAO = new LoaiPhimDAO();
        List<LoaiPhim> list = loaiPhimDAO.getAllLoaiPhim();
        
        DefaultComboBoxModel<LoaiPhim> model = new DefaultComboBoxModel<>();
        for (LoaiPhim lp : list) {
            model.addElement(lp);
        }
        cboLoai.setModel(model);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<Phim> list = phimDAO.getAll();
        
        for (Phim phim : list) {
            tableModel.addRow(new Object[]{
                phim.getMaPhim(),
                phim.getTenPhim(),
                phim.getLoaiPhim().getTenLoaiPhim(),
                phim.getThoiLuongChieu(),
                phim.getNamPhatHanh(),
                phim.getMoTa(),
                phim.getPoster()
            });
        }
    }
    
    private void loadFormFromTable() {
        int selectedRow = tablePhim.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim cần sửa!");
            return;
        }
        
        String maPhim = tableModel.getValueAt(selectedRow, 0).toString();
        Phim phim = phimDAO.getById(maPhim);
        
        if (phim != null) {
            txtMaPhim.setText(phim.getMaPhim());
            txtTenPhim.setText(phim.getTenPhim());
            txtThoiLuong.setText(String.valueOf(phim.getThoiLuongChieu()));
            txtNamPhatHanh.setText(String.valueOf(phim.getNamPhatHanh()));
            txtMoTa.setText(phim.getMoTa());
            
            // Set combo box
            for (int i = 0; i < cboLoaiPhim.getItemCount(); i++) {
                LoaiPhim lp = cboLoaiPhim.getItemAt(i);
                if (lp.getMaLoaiPhim().equals(phim.getLoaiPhim().getMaLoaiPhim())) {
                    cboLoaiPhim.setSelectedIndex(i);
                    break;
                }
            }
            
         // 🧾 Gán đường dẫn poster (nếu có)
            if (phim.getPoster() != null) {
                txtPoster.setText(phim.getPoster());
            } else {
                txtPoster.setText("");
            }
            
            currentMode = "EDIT";
        }
    }
    
    private void handleThem() {
        if (!validateForm()) return;
        
        Phim phim = getPhimFromForm();
        
        if (phimDAO.createPhim(phim)) {
            JOptionPane.showMessageDialog(this, "Thêm phim thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Thêm phim thất bại do bị trùng mã phim: " + phim.getMaPhim() + "!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSua() {
        if (txtMaPhim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim cần sửa từ bảng!");
            return;
        }
        
        if (!validateForm()) return;
        
        Phim phim = getPhimFromForm();
        
        int choice = JOptionPane.showConfirmDialog(this, 
            "Bạn có chắc muốn cập nhật phim \"" + phim.getTenPhim() + "\"?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (phimDAO.updatePhim(phim)) {
                JOptionPane.showMessageDialog(this, "✅ Cập nhật phim thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Cập nhật phim thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        txtMaPhim.setEditable(true);
    }
    
    private void handleXoa() {
        int selectedRow = tablePhim.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim cần xóa!");
            return;
        }
        
        String maPhim = tableModel.getValueAt(selectedRow, 0).toString();
        String tenPhim = tableModel.getValueAt(selectedRow, 1).toString();
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa phim \"" + tenPhim + "\"?\n" +
            "⚠️ Lưu ý: Không thể xóa nếu phim đã có lịch chiếu!",
            "Xác nhận xóa", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (phimDAO.deletePhim(maPhim)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa phim thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Không thể xóa phim!\n" +
                    "Phim có thể đang có lịch chiếu hoặc đã bán vé.",
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
        List<Phim> list = phimDAO.search(keyword);
        
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phim nào!");
        } else {
            for (Phim phim : list) {
                tableModel.addRow(new Object[]{
                    phim.getMaPhim(),
                    phim.getTenPhim(),
                    phim.getLoaiPhim().getTenLoaiPhim(),
                    phim.getThoiLuongChieu(),
                    phim.getNamPhatHanh(),
                    phim.getMoTa()
                });
            }
        }
    }
    
    private boolean validateForm() {
        if (txtTenPhim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên phim!");
            txtTenPhim.requestFocus();
            return false;
        }
        
        if (cboLoaiPhim.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn loại phim!");
            return false;
        }
        
        try {
            int thoiLuong = Integer.parseInt(txtThoiLuong.getText().trim());
            if (thoiLuong <= 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0!");
                txtThoiLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên!");
            txtThoiLuong.requestFocus();
            return false;
        }
        
        try {
            int nam = Integer.parseInt(txtNamPhatHanh.getText().trim());
            if (nam < 1900 || nam > 2100) {
                JOptionPane.showMessageDialog(this, "Năm phát hành không hợp lệ!");
                txtNamPhatHanh.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Năm phát hành phải là số nguyên!");
            txtNamPhatHanh.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private Phim getPhimFromForm() {
        Phim phim = new Phim();
        phim.setMaPhim(txtMaPhim.getText().trim());
        phim.setTenPhim(txtTenPhim.getText().trim());
        phim.setLoaiPhim((LoaiPhim) cboLoaiPhim.getSelectedItem());
        phim.setMoTa(txtMoTa.getText().trim());
        phim.setThoiLuongChieu(Integer.parseInt(txtThoiLuong.getText().trim()));
        phim.setNamPhatHanh(Integer.parseInt(txtNamPhatHanh.getText().trim()));
        phim.setPoster(txtPoster.getText().trim());
        return phim;
    }
    
    private void clearForm() {
    	txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtThoiLuong.setText("");
        txtNamPhatHanh.setText("");
        txtMoTa.setText("");
        cboLoaiPhim.setSelectedIndex(0);
        currentMode = "ADD";
        txtTenPhim.requestFocus();
        txtMaPhim.setEditable(true);
        txtPoster.setText("");
    }
    
    
    // Test
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PhimGUI gui = new PhimGUI();
            gui.setVisible(true);
        });
    }
}
