// ============================================
// SO DO GHE GUI - HÙNG PHỤ TRÁCH
// Cung cấp cho module Đặt vé của Đạt
// Đã sửa lỗi layout và logic nghiệp vụ
// ============================================
package gui;

import dao.GheDAO;
import dao.PhongDAO;
import dao.LichChieuDAO;
import dao.VeDAO; // <-- GIẢ SỬ ĐÂY LÀ DAO CỦA ĐẠT ĐỂ LẤY VÉ/GHẾ ĐÃ ĐẶT
import entity.Ghe;
import entity.Phong;
import entity.LichChieu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Giao diện sơ đồ ghế - Dùng để chọn ghế khi đặt vé
 * @author Hùng
 */
public class SoDoGheGUI extends JDialog {
    
    // Components
    private JPanel pnlSoDoGhe;
    private JLabel lblGheDaChon, lblTongTien;
    private JButton btnXacNhan, btnHuy;
    
    // Data
    private String maPhong;
    private String maLichChieu;
    private List<Ghe> dsGheTrongPhong;
    private List<Ghe> dsGheDaChon;
    private Map<String, JButton> mapGheButton;
    private List<Ghe> dsGheDaDatTheoChieu; // <-- Rất quan trọng: Ghế đã bán cho LỊCH CHIẾU NÀY
    
    // DAO
    private GheDAO gheDAO;
    private PhongDAO phongDAO;
    private LichChieuDAO lichChieuDAO;
    private VeDAO veDAO; // <-- DAO để check ghế đã bán
    
    // Colors
    private final Color COLOR_TRONG = new Color(189, 195, 199);      // Xám - Trống
    private final Color COLOR_DA_CHON = new Color(46, 204, 113);     // Xanh lá - Đã chọn
    private final Color COLOR_DA_DAT = new Color(231, 76, 60);       // Đỏ - Đã đặt
    private final Color COLOR_VIP = new Color(241, 196, 15);         // Vàng - VIP
    private final Color COLOR_DANG_SUA = new Color(149, 165, 166);   // Xám đậm - Đang sửa
    
    // Result - Đạt sẽ lấy 2 giá trị này
    private List<Ghe> resultGheDaChon = new ArrayList<>();
    private double resultTongPhuThu = 0; // <-- Thêm cho Đạt
    
    /**
     * Constructor cho Đạt gọi
     * @param maPhong Mã phòng chiếu
     * @param maLichChieu Mã lịch chiếu (để check ghế đã đặt)
     */
    public SoDoGheGUI(Frame parent, String maPhong, String maLichChieu) {
        super(parent, "Chọn ghế", true); // Modal dialog
        this.maPhong = maPhong;
        this.maLichChieu = maLichChieu;
        this.dsGheDaChon = new ArrayList<>();
        this.mapGheButton = new HashMap<>();
        
        // Khởi tạo DAO
        gheDAO = new GheDAO();
        phongDAO = new PhongDAO();
        lichChieuDAO = new LichChieuDAO();
        veDAO = new VeDAO(); // <-- Khởi tạo VeDAO
        
        // *** LOGIC NGHIỆP VỤ QUAN TRỌNG CHO ĐẠT ***
        // Lấy danh sách ghế đã bị đặt CỦA LỊCH CHIẾU NÀY
        // (Giả sử VeDAO có hàm này, nếu không, bạn cần tạo nó)
        //this.dsGheDaDatTheoChieu = veDAO.getGheDaDatByLichChieu(maLichChieu);
        
        initComponents();
        loadSoDoGhe();
        setLocationRelativeTo(parent);
    }
    
    /**
     * Constructor đơn giản cho test
     */
    public SoDoGheGUI(String maPhong, String maLichChieu) {
        this(null, maPhong, maLichChieu);
    }
    
    private void initComponents() {
        setSize(900, 700);
        setLayout(new BorderLayout(10, 10));
        
        // Header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Center - Sơ đồ ghế
        JScrollPane scrollPane = createSoDoGhePanel();
        add(scrollPane, BorderLayout.CENTER);
        
        // Bottom - Thông tin & buttons
        JPanel bottomPanel = createBottomPanel();
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(52, 73, 94));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        // Title
        Phong phong = phongDAO.findPhongByMa(maPhong);
        LichChieu lichChieu = lichChieuDAO.getById(maLichChieu);
        
        String title = "🎬 SƠ ĐỒ GHẾ - " + (phong != null ? phong.getTenPhong() : maPhong);
        if (lichChieu != null) {
            title += " | " + lichChieu.getPhim().getTenPhim();
            title += " | " + lichChieu.getGioBatDau().format(java.time.format.DateTimeFormatter.ofPattern("HH:mm"));
        }
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        
        panel.add(lblTitle, BorderLayout.WEST);
        
        // Screen label
        JPanel screenPanel = new JPanel();
        screenPanel.setBackground(new Color(52, 73, 94));
        JLabel lblScreen = new JLabel("[ MÀN HÌNH CHIẾU ]");
        lblScreen.setFont(new Font("Arial", Font.BOLD, 16));
        lblScreen.setForeground(new Color(241, 196, 15));
        screenPanel.add(lblScreen);
        
        panel.add(screenPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JScrollPane createSoDoGhePanel() {
        pnlSoDoGhe = new JPanel();
        pnlSoDoGhe.setBackground(Color.WHITE);
        pnlSoDoGhe.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JScrollPane scrollPane = new JScrollPane(pnlSoDoGhe);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        return scrollPane;
    }
    
    private void loadSoDoGhe() {
        pnlSoDoGhe.removeAll();

        // Lấy danh sách ghế trong phòng
        dsGheTrongPhong = gheDAO.getByPhong(maPhong);

        if (dsGheTrongPhong.isEmpty()) {
            JLabel lblEmpty = new JLabel("Phòng chưa có ghế. Vui lòng tạo ghế trước!");
            lblEmpty.setFont(new Font("Arial", Font.BOLD, 16));
            lblEmpty.setForeground(Color.RED);
            pnlSoDoGhe.add(lblEmpty);
            return;
        }

        // Phân tích cấu trúc ghế (A01, A02... B01, B02...)
        Map<Character, List<Ghe>> gheTheoHang = new HashMap<>();

        for (Ghe ghe : dsGheTrongPhong) {
            String maGhe = ghe.getMaGhe();
            // *** SỬA LỖI: Gọi hàm extractHang đã được sửa ***
            char hang = extractHang(maGhe); // Sẽ trả về 'A', 'B' (không phải 'P')

            gheTheoHang.putIfAbsent(hang, new ArrayList<>());
            gheTheoHang.get(hang).add(ghe);
        }

        // Sắp xếp hàng A-Z
        List<Character> dsHang = new ArrayList<>(gheTheoHang.keySet());
        dsHang.sort(Character::compareTo);

        // *** SỬA LỖI: Dùng BoxLayout để xếp các hàng theo chiều dọc (Y_AXIS) ***
        pnlSoDoGhe.setLayout(new BoxLayout(pnlSoDoGhe, BoxLayout.Y_AXIS));

        // Render từng hàng
        for (char hang : dsHang) {
            List<Ghe> gheHang = gheTheoHang.get(hang);
            gheHang.sort((g1, g2) -> g1.getMaGhe().compareTo(g2.getMaGhe()));

            // *** SỬA LỖI: Tạo một panel riêng cho mỗi hàng ***
            JPanel pnlHang = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
            pnlHang.setBackground(Color.WHITE);
            // Căn lề trái panel này (quan trọng khi dùng BoxLayout)
            pnlHang.setAlignmentX(Component.LEFT_ALIGNMENT);

            // Label hàng (A, B, C...)
            JLabel lblHang = new JLabel(String.valueOf(hang), SwingConstants.CENTER);
            lblHang.setFont(new Font("Arial", Font.BOLD, 18));
            lblHang.setOpaque(true);
            lblHang.setBackground(new Color(236, 240, 241));
            lblHang.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            
            // *** SỬA LỖI: Cho label hàng có kích thước cố định để căn chỉnh đẹp ***
            // (Kích thước này bằng với kích thước nút ghế trong createGheButton)
            lblHang.setPreferredSize(new Dimension(60, 60)); 
            
            pnlHang.add(lblHang); // Thêm label vào panel hàng

            // Các ghế trong hàng
            for (Ghe ghe : gheHang) {
                // Dùng hàm createGheButton gốc của bạn (nút có tên "01", "02"...)
                JButton btnGhe = createGheButton(ghe);
                pnlHang.add(btnGhe); // Thêm ghế vào panel hàng
                mapGheButton.put(ghe.getMaGhe(), btnGhe);
            }
            
            // *** SỬA LỖI: Thêm panel của cả hàng vào panel sơ đồ ghế chính ***
            pnlSoDoGhe.add(pnlHang);
        }

        pnlSoDoGhe.revalidate();
        pnlSoDoGhe.repaint();
    }
    
    private char extractHang(String maGhe) {
        // VD: P01A01 → A, P02B05 → B
        
        boolean foundDigit = false;
        // Duyệt qua chuỗi mã ghế
        for (int i = 0; i < maGhe.length(); i++) {
            char c = maGhe.charAt(i);
            
            // 1. Đánh dấu khi tìm thấy số đầu tiên
            if (Character.isDigit(c)) {
                foundDigit = true;
            }
            
            // 2. Nếu đã từng thấy số, tìm chữ cái ĐẦU TIÊN ngay sau đó
            if (foundDigit && Character.isLetter(c)) {
                return c; // Đây chính là hàng 'A', 'B', 'C'
            }
        }
        
        // Trường hợp mã ghế không theo quy tắc (ví dụ: "A01"), 
        // thì tìm chữ cái đầu tiên
        for (int i = 0; i < maGhe.length(); i++) {
             char c = maGhe.charAt(i);
             if (Character.isLetter(c)) {
                return c;
             }
        }

        return '?'; // Default nếu không tìm thấy
    }
    
    private JButton createGheButton(Ghe ghe) {
        // *** SỬA LỖI HIỂN THỊ ***
        // Extract số ghế (01, 02, 03...)
        // (Vì `loadSoDoGhe` đã có label hàng 'A', 'B' rồi)
        String soGhe = ghe.getMaGhe().substring(ghe.getMaGhe().length() - 2);
        
        JButton btn = new JButton(soGhe);
        
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setPreferredSize(new Dimension(60, 60));
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2));
        
        // Set màu dựa vào trạng thái
        updateGheButtonColor(btn, ghe);
        
        // Tooltip
        String tooltip = "<html><b>" + ghe.getMaGhe() + "</b><br/>" +
                         ghe.getLoaiGhe().getTenLoaiGhe() + "<br/>" +
                         "Phụ thu: " + String.format("%,.0f VNĐ", ghe.getLoaiGhe().getPhuThu()) +
                         "</html>";
        btn.setToolTipText(tooltip);
        
        // Click handler
        btn.addActionListener(e -> handleGheClick(ghe, btn));
        
        // Logic disable/enable sẽ được `updateGheButtonColor` xử lý
        
        return btn;
    }
    
    private void updateGheButtonColor(JButton btn, Ghe ghe) {
        String trangThaiChung = ghe.getTrangThai(); // Trạng thái chung (Trống, Đang sửa)
        String loaiGhe = ghe.getLoaiGhe().getTenLoaiGhe();
        
        // *** SỬA LOGIC NGHIỆP VỤ CHO ĐẠT ***
        // 1. Ưu tiên 1: Check xem ghế có trong ds ĐÃ ĐẶT CỦA LỊCH CHIẾU này không
        // (Cần override .equals() trong Entity Ghế để .contains() hoạt động)
        if (dsGheDaDatTheoChieu.contains(ghe)) { 
            btn.setBackground(COLOR_DA_DAT);
            btn.setForeground(Color.WHITE);
            btn.setEnabled(false); // Không cho phép chọn
        }
        // 2. Ưu tiên 2: Check xem ghế có đang được CHỌN (bởi người dùng)
        else if (dsGheDaChon.contains(ghe)) {
            btn.setBackground(COLOR_DA_CHON);
            btn.setForeground(Color.WHITE);
            btn.setEnabled(true);
        }
        // 3. Ưu tiên 3: Check trạng thái chung của ghế (ví dụ ghế hỏng)
        else if (trangThaiChung.equals("Đang sửa")) {
            btn.setBackground(COLOR_DANG_SUA);
            btn.setForeground(Color.WHITE);
            btn.setEnabled(false); // Không cho phép chọn
        }
        // 4. Ghế VIP trống
        else if (loaiGhe.contains("VIP") || loaiGhe.contains("Deluxe")) {
            btn.setBackground(COLOR_VIP);
            btn.setForeground(Color.BLACK);
            btn.setEnabled(true);
        }
        // 5. Ghế thường trống
        else {
            btn.setBackground(COLOR_TRONG);
            btn.setForeground(Color.BLACK);
            btn.setEnabled(true);
        }
    }
    
    private void handleGheClick(Ghe ghe, JButton btn) {
        // Toggle chọn/bỏ chọn
        if (dsGheDaChon.contains(ghe)) {
            // Bỏ chọn
            dsGheDaChon.remove(ghe);
        } else {
            // Chọn
            dsGheDaChon.add(ghe);
        }
        
        // Update màu button
        updateGheButtonColor(btn, ghe);
        
        // Update thông tin
        updateThongTin();
    }
    
    private void updateThongTin() {
        int soGhe = dsGheDaChon.size();
        double tongTien = 0;
        
        for (Ghe ghe : dsGheDaChon) {
            tongTien += ghe.getLoaiGhe().getPhuThu();
        }
        
        lblGheDaChon.setText("Số ghế đã chọn: " + soGhe);
        lblTongTien.setText("Phụ thu: " + String.format("%,.0f VNĐ", tongTien));
    }
    
    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        panel.setBackground(Color.WHITE);
        
        // Legend panel
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        legendPanel.setBackground(Color.WHITE);
        
        legendPanel.add(createLegendItem("Trống", COLOR_TRONG));
        legendPanel.add(createLegendItem("VIP", COLOR_VIP));
        legendPanel.add(createLegendItem("Đã chọn", COLOR_DA_CHON));
        legendPanel.add(createLegendItem("Đã đặt", COLOR_DA_DAT));
        legendPanel.add(createLegendItem("Đang sửa", COLOR_DANG_SUA)); // <-- Thêm chú thích này
        
        // Info panel
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 5));
        infoPanel.setBackground(Color.WHITE);
        
        lblGheDaChon = new JLabel("Số ghế đã chọn: 0");
        lblGheDaChon.setFont(new Font("Arial", Font.BOLD, 14));
        
        lblTongTien = new JLabel("Phụ thu: 0 VNĐ");
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 14));
        lblTongTien.setForeground(new Color(231, 76, 60));
        
        infoPanel.add(lblGheDaChon);
        infoPanel.add(lblTongTien);
        
        // Button panel
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnPanel.setBackground(Color.WHITE);
        
        btnXacNhan = new JButton("✓ Xác nhận");
        btnXacNhan.setFont(new Font("Arial", Font.BOLD, 14));
        btnXacNhan.setBackground(new Color(46, 204, 113));
        btnXacNhan.setForeground(Color.WHITE);
        btnXacNhan.setFocusPainted(false);
        btnXacNhan.setPreferredSize(new Dimension(130, 40));
        btnXacNhan.addActionListener(e -> handleXacNhan());
        
        btnHuy = new JButton("✗ Hủy");
        btnHuy.setFont(new Font("Arial", Font.BOLD, 14));
        btnHuy.setBackground(new Color(149, 165, 166));
        btnHuy.setForeground(Color.WHITE);
        btnHuy.setFocusPainted(false);
        btnHuy.setPreferredSize(new Dimension(130, 40));
        btnHuy.addActionListener(e -> handleHuy());
        
        btnPanel.add(btnXacNhan);
        btnPanel.add(btnHuy);
        
        // Combine
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.add(legendPanel, BorderLayout.NORTH);
        topPanel.add(infoPanel, BorderLayout.CENTER);
        
        panel.add(topPanel, BorderLayout.CENTER);
        panel.add(btnPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createLegendItem(String text, Color color) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panel.setBackground(Color.WHITE);
        
        JLabel lblColor = new JLabel("   ");
        lblColor.setOpaque(true);
        lblColor.setBackground(color);
        lblColor.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        
        JLabel lblText = new JLabel(text);
        lblText.setFont(new Font("Arial", Font.PLAIN, 12));
        
        panel.add(lblColor);
        panel.add(lblText);
        
        return panel;
    }
    
    private void handleXacNhan() {
        if (dsGheDaChon.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng chọn ít nhất 1 ghế!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        // Set result
        resultGheDaChon = new ArrayList<>(dsGheDaChon);
        
        // *** THÊM TÍNH NĂNG CHO ĐẠT ***
        // Tính tổng tiền phụ thu để trả về
        double tongPhuThu = 0;
        for (Ghe ghe : resultGheDaChon) {
            tongPhuThu += ghe.getLoaiGhe().getPhuThu();
        }
        this.resultTongPhuThu = tongPhuThu;
        
        // Close dialog
        dispose();
    }
    
    private void handleHuy() {
        resultGheDaChon.clear();
        resultTongPhuThu = 0; // <-- Đặt lại tiền
        dispose();
    }
    
    /**
     * ĐẠT SẼ GỌI METHOD NÀY để lấy ghế đã chọn
     * @return List<Ghe> ghế đã chọn (empty nếu hủy)
     */
    public List<Ghe> getGheDaChon() {
        return resultGheDaChon;
    }
    
    /**
     * ĐẠT SẼ GỌI METHOD NÀY để lấy tổng tiền phụ thu
     * @return double tổng phụ thu (0 nếu hủy)
     */
    public double getTongPhuThuDaChon() {
        return resultTongPhuThu;
    }
    
    // Test
    public static void main(String[] args) {
        // (Giả sử bạn đã setup Look and Feel ở đâu đó)
        SwingUtilities.invokeLater(() -> {
            // Test với phòng P01 (phải có dữ liệu trong DB)
            // và lịch chiếu LC001
            SoDoGheGUI gui = new SoDoGheGUI("P01", "LC001");
            gui.setVisible(true);
            
            // Sau khi đóng, kiểm tra kết quả
            List<Ghe> gheChon = gui.getGheDaChon();
            double phuThu = gui.getTongPhuThuDaChon();
            
            if (!gheChon.isEmpty()) {
                System.out.println("Đã chọn " + gheChon.size() + " ghế:");
                for (Ghe ghe : gheChon) {
                    System.out.println("  - " + ghe.getMaGhe() + " (" + ghe.getLoaiGhe().getTenLoaiGhe() + ")");
                }
                System.out.println("Tổng phụ thu: " + String.format("%,.0f VNĐ", phuThu));
            } else {
                System.out.println("Đã hủy chọn ghế.");
            }
        });
    }
}