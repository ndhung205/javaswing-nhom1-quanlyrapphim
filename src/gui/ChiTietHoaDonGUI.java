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
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import com.itextpdf.text.*;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.*;
@SuppressWarnings("serial")
public class ChiTietHoaDonGUI extends JFrame implements ActionListener {

    private JLabel lblMaHD;
    private JTable tblChiTiet;
    private DefaultTableModel modelChiTiet;
    private JButton btnIn, btnDong;
	private JLabel lblMaDatVe;
	private JLabel lblKhachHang;
	private JLabel lblNgayLap;
	private JLabel lblNhanVien;
	private JLabel lblTongTien;
	private JLabel lblGiamGia;
	private JLabel lblVAT;
	private HoaDon hoaDon = new HoaDon();
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
        btnDong = new JButton("Đóng");

        pnlButton.add(btnIn);
        pnlButton.add(btnDong);
        add(pnlButton, BorderLayout.PAGE_END);

        btnIn.addActionListener(this);
        btnDong.addActionListener(this);
        
        loadData();
    }
    private void loadData() {
    	ArrayList<ChiTietHoaDon> listCT = ctDAO.findChiTietHoaDonByMa(hoaDon.getMaHoaDon());
    	int stt = 1;
    	double tongTien = 0;
    	double thue = 0;
    	double giamGia = 0;
    	
    	for (ChiTietHoaDon chiTietHoaDon : listCT) {
			String[] row = {stt++ +"",chiTietHoaDon.getVe().getLichChieu().getPhim().getTenPhim(),
					chiTietHoaDon.getVe().getLichChieu().getGioBatDau().toString(), chiTietHoaDon.getVe().getGhe().getMaGhe(),
					chiTietHoaDon.getDonGia()+"", chiTietHoaDon.tinhThanhTien() +" VNĐ"};
			
			
			tongTien += chiTietHoaDon.tinhThanhTien();
			modelChiTiet.addRow(row);
		}
    	thue = hoaDon.getThue().getPhanTram();
    	giamGia = hoaDon.tinhTienKhuyenMai(tongTien);
    	
    	DecimalFormat dft = new DecimalFormat("#,###.0 VNĐ");
    	
    	lblGiamGia.setText("Giảm giá: "+dft.format(giamGia));
    	lblVAT.setText("Thuế: " + thue +"%");
    	lblTongTien.setText("Tổng tiền: "+dft.format(tongTien));
    }
    
    private void inHoaDonPDF() {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        try {
            String fileName = "HoaDon_" + hoaDon.getMaHoaDon() + ".pdf";
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            BaseFont baseFont = BaseFont.createFont("C:\\Windows\\Fonts\\arial.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            Font titleFont = new Font(baseFont, 18, Font.BOLD);
            Font infoFont = new Font(baseFont, 12);
            Font headerFont = new Font(baseFont, 12, Font.BOLD);
            Font italicFont = new Font(baseFont, 12, Font.ITALIC);

            Paragraph title = new Paragraph("HÓA ĐƠN BÁN VÉ XEM PHIM", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph("\n"));


            document.add(new Paragraph("Mã HĐ: " + hoaDon.getMaHoaDon(), infoFont));
            document.add(new Paragraph("Mã đặt vé: " + hoaDon.getDatVe().getMaDatVe(), infoFont));
            document.add(new Paragraph("Khách hàng: " + hoaDon.getKhachHang().getTenKH(), infoFont));
            document.add(new Paragraph("Ngày lập: " + hoaDon.getNgayLapHoaDon(), infoFont));
            document.add(new Paragraph("Nhân viên: " + hoaDon.getNhanVien().getHoTen(), infoFont));
            document.add(new Paragraph("\n"));

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100);
            table.setWidths(new float[]{1, 4, 3, 2, 2, 3});

            String[] headers = {"STT", "Tên phim", "Suất chiếu", "Ghế", "Giá", "Thành tiền"};
            for (String h : headers) {
                PdfPCell cell = new PdfPCell(new Phrase(h, headerFont));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
                table.addCell(cell);
            }

            ArrayList<ChiTietHoaDon> listCT = ctDAO.findChiTietHoaDonByMa(hoaDon.getMaHoaDon());
            DecimalFormat dft = new DecimalFormat("#,###.0 VNĐ");
            int stt = 1;
            double tongTien = 0;

            for (ChiTietHoaDon ct : listCT) {
                table.addCell(new Phrase(String.valueOf(stt++), infoFont));
                table.addCell(new Phrase(ct.getVe().getLichChieu().getPhim().getTenPhim(), infoFont));
                table.addCell(new Phrase(ct.getVe().getLichChieu().getGioBatDau().toString(), infoFont));
                table.addCell(new Phrase(ct.getVe().getGhe().getMaGhe(), infoFont));
                table.addCell(new Phrase(String.valueOf(ct.getDonGia()), infoFont));
                table.addCell(new Phrase(dft.format(ct.tinhThanhTien()), infoFont));

                tongTien += ct.tinhThanhTien();
            }

            document.add(table);
            document.add(new Paragraph("\n"));

            // Tính toán tổng
            double giamGia = hoaDon.tinhTienKhuyenMai(tongTien);
            double thue = hoaDon.getThue().getPhanTram();
            double thanhTien = tongTien - giamGia + tongTien * thue / 100;

            document.add(new Paragraph("Tổng tiền: " + dft.format(tongTien), infoFont));
            document.add(new Paragraph("Giảm giá: " + dft.format(giamGia), infoFont));
            document.add(new Paragraph("Thuế: " + thue + "%", infoFont));
            document.add(new Paragraph("Thành tiền cuối cùng: " + dft.format(thanhTien), headerFont));

            document.add(new Paragraph("\nCảm ơn quý khách đã mua vé!", italicFont));

            document.close();
            JOptionPane.showMessageDialog(this, "Xuất hóa đơn thành công: " + fileName);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi in hóa đơn!");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnIn) {
            inHoaDonPDF();
        } else if (o == btnDong) {
            dispose();
        }
    }
}
