package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.ChiTietHoaDonDAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class ChiTietHoaDonGUI extends JFrame implements ActionListener {

    private JLabel lblMaHD;
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    private JButton btnIn, btnXuatPDF, btnDong;
	private JLabel lblMaDatVe;
	private JLabel lblKhachHang;
	private JLabel lblNgayLap;
	private JLabel lblNhanVien;
	private JLabel lblTongTien;
	private JLabel lblGiamGia;
	private JLabel lblVAT;
	private HoaDon hoaDon;
	private HoaDonGUI parent;
	private ChiTietHoaDonDAO ctDAO = new ChiTietHoaDonDAO();

    public ChiTietHoaDonGUI(HoaDonGUI parent, HoaDon hd) {
    	this.hoaDon = hd;
        this.parent = parent;
        
        initGUI();
    }
    private void initGUI() {
    	setTitle("Chi tiết hóa đơn - " + hoaDon.getMaHoaDon());
        setLayout(new BorderLayout(10, 10));
        setSize(700, 500);
        setLocationRelativeTo(parent);

        JPanel pnlThongTin = new JPanel(new GridLayout(3, 2, 10, 5));
        pnlThongTin.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY),
                "Thông tin hóa đơn", TitledBorder.LEFT, TitledBorder.TOP));

        lblMaHD = new JLabel("Mã HĐ: " + hoaDon.getMaHoaDon());
        lblMaDatVe = new JLabel("Mã đặt vé: " + hoaDon.getDatVe().getMaDatVe());
        lblKhachHang = new JLabel("Khách hàng: " + hoaDon.getKhachHang().getTenKH());
        lblNgayLap = new JLabel("Ngày lập: " +hoaDon.getNgayLapHoaDon());
        lblNhanVien = new JLabel("Nhân viên: " + hoaDon.getNhanVien().getHoTen());
        pnlThongTin.add(lblMaHD);
        pnlThongTin.add(lblMaDatVe);
        pnlThongTin.add(lblKhachHang);
        pnlThongTin.add(lblNgayLap);
        pnlThongTin.add(lblNhanVien);
        pnlThongTin.add(new JLabel(""));

        add(pnlThongTin, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        
        String[] col = {"STT", "Tên phim", "Suất chiếu", "Ghế", "Giá", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(col, 0);
        tblChiTiet = new JTable(modelChiTiet);
        JScrollPane scroll = new JScrollPane(tblChiTiet);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Chi tiết vé"));

        pnlCenter.add(scroll);

        JPanel pnlTongKet = new JPanel(new GridLayout(4, 2, 10, 5));
        pnlTongKet.setBorder(BorderFactory.createTitledBorder("Tổng kết"));
        lblTongTien = new JLabel();
        lblGiamGia = new JLabel();
        lblVAT = new JLabel();

        pnlTongKet.add(lblTongTien);
        pnlTongKet.add(lblGiamGia);
        pnlTongKet.add(lblVAT);

        pnlCenter.add(pnlTongKet);
        
        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnIn = new JButton("In hóa đơn");
        btnXuatPDF = new JButton("Xuất PDF");
        btnDong = new JButton("Đóng");

        pnlButton.add(btnIn);
        pnlButton.add(btnXuatPDF);
        pnlButton.add(btnDong);
        add(pnlButton, BorderLayout.PAGE_END);

        btnIn.addActionListener(this);
        btnXuatPDF.addActionListener(this);
        btnDong.addActionListener(this);
        
        loadData();
    }
    private void loadData() {
    	ArrayList<ChiTietHoaDon> listCT = ctDAO.findChiTietHoaDonByMa(hoaDon.getMaHoaDon());
    	int stt = 1;
    	double tongTien = 0;
    	float thue = 0;
    	double giamGia = 0;
    	for (ChiTietHoaDon chiTietHoaDon : listCT) {
			String[] row = {stt++ +"",chiTietHoaDon.getVe().getLichChieu().getPhim().getTenPhim(),
					chiTietHoaDon.getVe().getLichChieu().getGioBatDau().toString(), chiTietHoaDon.getVe().getGhe().getMaGhe(),
					chiTietHoaDon.getDonGia()+"", chiTietHoaDon.getDonGia()+" VNĐ"};
			
//			tongTien += chiTietHoaDon.getDonGia();
//			thue = chiTietHoaDon.getHoaDon().getThue().getPhanTram();
//			giamGia = chiTietHoaDon.getHoaDon().getKhuyenMai().getPhanTram() !=0 ? chiTietHoaDon.getHoaDon().getKhuyenMai().getPhanTram(): chiTietHoaDon.getHoaDon().getKhuyenMai().getSoTienGiam();
//			
			modelChiTiet.addRow(row);
		}
    	
    	lblGiamGia.setText("Giảm giá: "+giamGia);
    	lblVAT.setText("Thuế: " + thue);
    	lblTongTien.setText("Tổng tiền: "+tongTien);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnIn) {
            JOptionPane.showMessageDialog(this, "Đang phát triển");
        } else if (o == btnXuatPDF) {
            JOptionPane.showMessageDialog(this, "Đang xuất hóa đơn ra file PDF...");
        } else if (o == btnDong) {
            dispose();
        }
    }
}
