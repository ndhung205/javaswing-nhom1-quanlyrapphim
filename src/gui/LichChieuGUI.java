// ============================================
// LICH CHIEU GUI - HÙNG PHỤ TRÁCH
// ============================================
package gui;

import dao.LichChieuDAO;
import dao.PhimDAO;
import dao.PhongDAO;
import entity.LichChieu;
import entity.Phim;
import entity.Phong;

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

/**
 * Giao diện quản lý Lịch chiếu
 * @author Hùng
 */
public class LichChieuGUI extends JPanel {
    
    // Components
    private JTable tableLichChieu;
    private DefaultTableModel tableModel;
    private JTextField txtMaLichChieu;
    private JComboBox<Phim> cboPhim;
    private JComboBox<Phong> cboPhong;
    private JDateChooser dateNgayChieu;
    private JSpinner spinGioChieu;
    private JSpinner spinPhutChieu;
    private JLabel lblGioKetThuc, lblThoiLuong;
    private JButton btnThem, btnSua, btnXoa, btnLamMoi;
    private JComboBox<String> cboFilterNgay;
    
    // DAO
    private LichChieuDAO lichChieuDAO;
    private PhimDAO phimDAO;
    private PhongDAO phongDAO;
    
    // Mode
    private String currentMode = "ADD";
    
    // Formatter
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    
    public LichChieuGUI() {
        lichChieuDAO = new LichChieuDAO();
        phimDAO = new PhimDAO();
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
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel lblTitle = new JLabel("📅 QUẢN LÝ LỊCH CHIẾU");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);
        
        panel.add(lblTitle, BorderLayout.WEST);
        
        return panel;
    }
    
    private JPanel createFormPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Title
        JLabel lblFormTitle = new JLabel("Thông tin lịch chiếu");
        lblFormTitle.setFont(new Font("Arial", Font.BOLD, 16));
        lblFormTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        
        // Form fields
        JPanel formFields = new JPanel(new GridBagLayout());
        formFields.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 5, 8, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        // Mã lịch chiếu
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Mã lịch chiếu: *"), gbc);
        gbc.gridx = 1;
        txtMaLichChieu = new JTextField(15);
        txtMaLichChieu.setToolTipText("VD: LC001, LC002...");
        formFields.add(txtMaLichChieu, gbc);
        
        row++;
        
        // Phim
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Phim: *"), gbc);
        gbc.gridx = 1;
        cboPhim = new JComboBox<>();
        loadPhim();
        cboPhim.addActionListener(e -> updateGioKetThuc());
        formFields.add(cboPhim, gbc);
        
        row++;
        
        // Thời lượng (auto)
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Thời lượng:"), gbc);
        gbc.gridx = 1;
        lblThoiLuong = new JLabel("-- phút");
        lblThoiLuong.setFont(new Font("Arial", Font.BOLD, 12));
        lblThoiLuong.setForeground(new Color(52, 152, 219));
        formFields.add(lblThoiLuong, gbc);
        
        row++;
        
        // Phòng
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Phòng: *"), gbc);
        gbc.gridx = 1;
        cboPhong = new JComboBox<>();
        loadPhong();
        formFields.add(cboPhong, gbc);
        
        row++;
        
        // Ngày chiếu (JDateChooser)
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Ngày chiếu: *"), gbc);
        gbc.gridx = 1;
        dateNgayChieu = new JDateChooser();
        dateNgayChieu.setDateFormatString("dd/MM/yyyy");
        dateNgayChieu.setPreferredSize(new Dimension(200, 25));
        dateNgayChieu.setMinSelectableDate(new Date()); // Không chọn ngày quá khứ
        dateNgayChieu.addPropertyChangeListener("date", e -> updateGioKetThuc());
        formFields.add(dateNgayChieu, gbc);
        
        row++;
        
        // Giờ bắt đầu (Spinner với format HH:mm)
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Giờ bắt đầu: *"), gbc);
        gbc.gridx = 1;
        
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        timePanel.setBackground(Color.WHITE);
        
        // Spinner giờ (0-23)
        SpinnerNumberModel hourModel = new SpinnerNumberModel(14, 0, 23, 1);
        spinGioChieu = new JSpinner(hourModel);
        spinGioChieu.setPreferredSize(new Dimension(60, 25));
        spinGioChieu.addChangeListener(e -> updateGioKetThuc());
        
        // Spinner phút (0-59)
        SpinnerNumberModel minuteModel = new SpinnerNumberModel(0, 0, 59, 5);
        spinPhutChieu = new JSpinner(minuteModel);
        spinPhutChieu.setPreferredSize(new Dimension(60, 25));
        spinPhutChieu.addChangeListener(e -> updateGioKetThuc());
        
        timePanel.add(spinGioChieu);
        timePanel.add(new JLabel(":"));
        timePanel.add(spinPhutChieu);
        
        formFields.add(timePanel, gbc);
        
        row++;
        
        // Giờ kết thúc (auto)
        gbc.gridx = 0; gbc.gridy = row;
        formFields.add(new JLabel("Giờ kết thúc:"), gbc);
        gbc.gridx = 1;
        lblGioKetThuc = new JLabel("--:--");
        lblGioKetThuc.setFont(new Font("Arial", Font.BOLD, 12));
        lblGioKetThuc.setForeground(new Color(231, 76, 60));
        formFields.add(lblGioKetThuc, gbc);
        
        row++;
        
        // Warning
        gbc.gridx = 0; gbc.gridy = row;
        gbc.gridwidth = 2;
        JLabel lblWarning = new JLabel("<html><i>⚠️ Hệ thống sẽ kiểm tra trùng lịch tự động</i></html>");
        lblWarning.setFont(new Font("Arial", Font.PLAIN, 11));
        lblWarning.setForeground(Color.ORANGE);
        formFields.add(lblWarning, gbc);
        
        // Buttons
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
        
        panel.add(lblFormTitle, BorderLayout.NORTH);
        panel.add(formFields, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.setBackground(Color.WHITE);
        
        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(Color.WHITE);
        
        filterPanel.add(new JLabel("Xem lịch chiếu:"));
        cboFilterNgay = new JComboBox<>(new String[]{
            "Hôm nay", "Ngày mai", "7 ngày tới", "Tất cả"
        });
        cboFilterNgay.addActionListener(e -> filterByNgay());
        filterPanel.add(cboFilterNgay);
        
        // Table
        String[] columns = {"Mã LC", "Phim", "Phòng", "Ngày chiếu", "Giờ bắt đầu", "Giờ kết thúc"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tableLichChieu = new JTable(tableModel);
        tableLichChieu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableLichChieu.setRowHeight(25);
        tableLichChieu.getColumnModel().getColumn(0).setPreferredWidth(80);
        tableLichChieu.getColumnModel().getColumn(1).setPreferredWidth(200);
        tableLichChieu.getColumnModel().getColumn(2).setPreferredWidth(120);
        tableLichChieu.getColumnModel().getColumn(3).setPreferredWidth(100);
        tableLichChieu.getColumnModel().getColumn(4).setPreferredWidth(80);
        tableLichChieu.getColumnModel().getColumn(5).setPreferredWidth(80);
        
        tableLichChieu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    loadFormFromTable();
                }
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(tableLichChieu);
        
        panel.add(filterPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        JLabel lblInfo = new JLabel("Double-click để chỉnh sửa");
        lblInfo.setForeground(Color.GRAY);
        lblInfo.setFont(new Font("Arial", Font.ITALIC, 11));
        panel.add(lblInfo, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadPhim() {
        List<Phim> list = phimDAO.getAll();
        DefaultComboBoxModel<Phim> model = new DefaultComboBoxModel<>();
        for (Phim p : list) {
            model.addElement(p);
        }
        cboPhim.setModel(model);
    }
    
    private void loadPhong() {
        List<Phong> list = phongDAO.getPhongByTrangThai(1);
        DefaultComboBoxModel<Phong> model = new DefaultComboBoxModel<>();
        for (Phong p : list) {
            model.addElement(p);
        }
        cboPhong.setModel(model);
    }
    
    private void loadData() {
        tableModel.setRowCount(0);
        List<LichChieu> list = lichChieuDAO.getAllLichChieu();
        
        for (LichChieu lc : list) {
            tableModel.addRow(new Object[]{
                lc.getMaLichChieu(),
                lc.getPhim().getTenPhim(),
                lc.getPhong().getTenPhong(),
                lc.getNgayChieu().format(dateFormatter),
                lc.getGioBatDau().format(timeFormatter),
                lc.getGioKetThuc().format(timeFormatter)
            });
        }
    }
    
    private void filterByNgay() {
        String filter = (String) cboFilterNgay.getSelectedItem();
        List<LichChieu> list;
        
        switch (filter) {
            case "Hôm nay":
                list = lichChieuDAO.getByNgay(LocalDate.now());
                break;
            case "Ngày mai":
                list = lichChieuDAO.getByNgay(LocalDate.now().plusDays(1));
                break;
            case "7 ngày tới":
                list = lichChieuDAO.getAllLichChieu();
                LocalDate now = LocalDate.now();
                list.removeIf(lc -> lc.getNgayChieu().isBefore(now) || 
                                    lc.getNgayChieu().isAfter(now.plusDays(7)));
                break;
            default:
                list = lichChieuDAO.getAllLichChieu();
        }
        
        tableModel.setRowCount(0);
        for (LichChieu lc : list) {
            tableModel.addRow(new Object[]{
                lc.getMaLichChieu(),
                lc.getPhim().getTenPhim(),
                lc.getPhong().getTenPhong(),
                lc.getNgayChieu().format(dateFormatter),
                lc.getGioBatDau().format(timeFormatter),
                lc.getGioKetThuc().format(timeFormatter)
            });
        }
    }
    
    private void updateGioKetThuc() {
        Phim phim = (Phim) cboPhim.getSelectedItem();
        if (phim == null) return;
        
        lblThoiLuong.setText(phim.getThoiLuongChieu() + " phút");
        
        try {
            int gio = (int) spinGioChieu.getValue();
            int phut = (int) spinPhutChieu.getValue();
            
            LocalTime gioBatDau = LocalTime.of(gio, phut);
            LocalTime gioKetThuc = gioBatDau.plusMinutes(phim.getThoiLuongChieu());
            
            lblGioKetThuc.setText(gioKetThuc.format(timeFormatter));
        } catch (Exception e) {
            lblGioKetThuc.setText("--:--");
        }
    }
    
    private void loadFormFromTable() {
        int row = tableLichChieu.getSelectedRow();
        if (row == -1) return;
        
        String maLichChieu = tableModel.getValueAt(row, 0).toString();
        LichChieu lc = lichChieuDAO.getById(maLichChieu);
        
        if (lc != null) {
            txtMaLichChieu.setText(lc.getMaLichChieu());
            txtMaLichChieu.setEnabled(false);
            txtMaLichChieu.setBackground(new Color(240, 240, 240));
            
            for (int i = 0; i < cboPhim.getItemCount(); i++) {
                if (cboPhim.getItemAt(i).getMaPhim().equals(lc.getPhim().getMaPhim())) {
                    cboPhim.setSelectedIndex(i);
                    break;
                }
            }
            
            for (int i = 0; i < cboPhong.getItemCount(); i++) {
                if (cboPhong.getItemAt(i).getMaPhong().equals(lc.getPhong().getMaPhong())) {
                    cboPhong.setSelectedIndex(i);
                    break;
                }
            }
            
            // Set ngày chiếu (JDateChooser)
            Date ngayChieu = Date.from(lc.getNgayChieu().atStartOfDay(ZoneId.systemDefault()).toInstant());
            dateNgayChieu.setDate(ngayChieu);
            
            // Set giờ bắt đầu
            spinGioChieu.setValue(lc.getGioBatDau().getHour());
            spinPhutChieu.setValue(lc.getGioBatDau().getMinute());
            
            updateGioKetThuc();
            currentMode = "EDIT";
        }
    }
    
    private void handleThem() {
        if (!validateForm()) return;
        
        String maLichChieu = txtMaLichChieu.getText().trim();
        if (lichChieuDAO.isMaLichChieuExists(maLichChieu)) {
            JOptionPane.showMessageDialog(this,
                "❌ Mã lịch chiếu đã tồn tại!",
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        LichChieu lc = getLichChieuFromForm();
        
        if (lichChieuDAO.checkTrungLich(lc, null)) {
            JOptionPane.showMessageDialog(this,
                "❌ Lịch chiếu bị trùng với lịch khác trong cùng phòng!\n" +
                "Vui lòng chọn giờ hoặc phòng khác.",
                "Trùng lịch", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (lichChieuDAO.insert(lc)) {
            JOptionPane.showMessageDialog(this, "✅ Thêm lịch chiếu thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Thêm lịch chiếu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleSua() {
        if (txtMaLichChieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần sửa!");
            return;
        }
        
        if (!validateForm()) return;
        
        LichChieu lc = getLichChieuFromForm();
        
        if (lichChieuDAO.checkTrungLich(lc, lc.getMaLichChieu())) {
            JOptionPane.showMessageDialog(this,
                "❌ Lịch chiếu bị trùng với lịch khác!",
                "Trùng lịch", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (lichChieuDAO.update(lc)) {
            JOptionPane.showMessageDialog(this, "✅ Cập nhật lịch chiếu thành công!");
            loadData();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "❌ Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void handleXoa() {
        int row = tableLichChieu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu cần xóa!");
            return;
        }
        
        String maLichChieu = tableModel.getValueAt(row, 0).toString();
        
        int choice = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa lịch chiếu này?\n⚠️ Không thể xóa nếu đã có vé!",
            "Xác nhận", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (choice == JOptionPane.YES_OPTION) {
            if (lichChieuDAO.delete(maLichChieu)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa thành công!");
                loadData();
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this,
                    "❌ Không thể xóa!\nLịch chiếu có thể đã có vé.",
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private boolean validateForm() {
        if (txtMaLichChieu.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã lịch chiếu!");
            return false;
        }
        
        if (cboPhim.getSelectedItem() == null || cboPhong.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim và phòng!");
            return false;
        }
        
        if (dateNgayChieu.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn ngày chiếu!");
            return false;
        }
        
        return true;
    }
    
    private LichChieu getLichChieuFromForm() {
        Phim phim = (Phim) cboPhim.getSelectedItem();
        Phong phong = (Phong) cboPhong.getSelectedItem();
        
        // Lấy ngày từ JDateChooser
        Date selectedDate = dateNgayChieu.getDate();
        LocalDate ngayChieu = selectedDate.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        
        // Lấy giờ phút từ spinner
        int gio = (int) spinGioChieu.getValue();
        int phut = (int) spinPhutChieu.getValue();
        LocalTime gioBatDau = LocalTime.of(gio, phut);
        
        LocalDateTime gioBatDauFull = LocalDateTime.of(ngayChieu, gioBatDau);
        LocalDateTime gioKetThucFull = gioBatDauFull.plusMinutes(phim.getThoiLuongChieu());
        
        return new LichChieu(
            txtMaLichChieu.getText().trim(),
            phim,
            phong,
            ngayChieu,
            gioBatDauFull,
            gioKetThucFull
        );
    }
    
    private void clearForm() {
        txtMaLichChieu.setText("");
        txtMaLichChieu.setEnabled(true);
        txtMaLichChieu.setBackground(Color.WHITE);
        
        cboPhim.setSelectedIndex(0);
        cboPhong.setSelectedIndex(0);
        dateNgayChieu.setDate(new Date()); // Hôm nay
        spinGioChieu.setValue(14); // 14:00
        spinPhutChieu.setValue(0);
        
        lblGioKetThuc.setText("--:--");
        lblThoiLuong.setText("-- phút");
        
        currentMode = "ADD";
        txtMaLichChieu.requestFocus();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LichChieuGUI gui = new LichChieuGUI();
            gui.setVisible(true);
        });
    }
}