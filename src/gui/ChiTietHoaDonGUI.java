package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import entity.HoaDon;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
	private JLabel lblThanhToan;

    public ChiTietHoaDonGUI(HoaDonGUI parent, HoaDon hoaDon) {
        super("Chi tiết hóa đơn - " + hoaDon.getMaHoaDon());
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

        String[] col = {"STT", "Tên phim", "Suất chiếu", "Ghế", "Giá", "Thành tiền"};
        modelChiTiet = new DefaultTableModel(col, 0);
        tblChiTiet = new JTable(modelChiTiet);
        JScrollPane scroll = new JScrollPane(tblChiTiet);
        scroll.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.GRAY), "Chi tiết vé"));

        add(scroll, BorderLayout.CENTER);

        JPanel pnlTongKet = new JPanel(new GridLayout(4, 2, 10, 5));
        pnlTongKet.setBorder(BorderFactory.createTitledBorder("Tổng kết"));
        lblTongTien = new JLabel("Tổng tiền: 150.000đ");
        lblGiamGia = new JLabel("Giảm giá: 0đ");
        lblVAT = new JLabel("VAT (10%): 15.000đ");
        lblThanhToan = new JLabel("Thanh toán: 165.000đ");

        pnlTongKet.add(lblTongTien);
        pnlTongKet.add(lblGiamGia);
        pnlTongKet.add(lblVAT);
        pnlTongKet.add(lblThanhToan);

        add(pnlTongKet, BorderLayout.SOUTH);

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
    }
    private void loadData() {
    	
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnIn) {
            JOptionPane.showMessageDialog(this, "Đang in hóa đơn...");
        } else if (o == btnXuatPDF) {
            JOptionPane.showMessageDialog(this, "Đang xuất hóa đơn ra file PDF...");
        } else if (o == btnDong) {
            dispose();
        }
    }
    public static void main(String[] args) {
		
	}
}
