package gui;

import com.toedter.calendar.JDateChooser;

import dao.VeDAO;
import entity.Ve;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

@SuppressWarnings("serial")
public class ThongKeGUI extends JPanel {

    private JLabel lblTitle;
    private JButton btnThongKe, btnLamMoi;
    private JTable tblVe;
    private DefaultTableModel modelVe;
    private JDateChooser dateChooser;
	private JLabel lblNgayTK;
	private JLabel lblTongVe;
	private JLabel lblTongDoanhThu;
	private VeDAO veDAO = new VeDAO();

    public ThongKeGUI() {
        setLayout(new BorderLayout(10, 10));

        JPanel pnlTitle = new JPanel();
        lblTitle = new JLabel("THỐNG KÊ VÉ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(new Color(0, 102, 204));
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));

        JPanel pnlLoc = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlLoc.setBorder(new TitledBorder("Lọc theo ngày"));
        
        lblNgayTK = new JLabel("Chọn ngày:");
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        dateChooser.setDateFormatString("dd/MM/yyyy");
        dateChooser.setPreferredSize(new Dimension(150, 25));
        btnThongKe = new JButton("Thống kê");

        pnlLoc.add(lblNgayTK);
        pnlLoc.add(dateChooser);
        pnlLoc.add(btnThongKe);
        
        JPanel pnlThongKe = new JPanel(new GridLayout(2, 2, 10, 5));
        pnlThongKe.setBorder(new TitledBorder("Kết quả tổng hợp"));
        
        pnlThongKe.add(new JLabel("Tổng vé bán:"));
        pnlThongKe.add(lblTongVe = new JLabel("0 vé"));
        pnlThongKe.add(new JLabel("Tổng doanh thu:"));
        pnlThongKe.add(lblTongDoanhThu = new JLabel("0 VNĐ"));

  
        JPanel pnlTable = new JPanel(new BorderLayout());
        pnlTable.setBorder(new TitledBorder("Danh sách vé đã bán"));
        String[] cols = {"Mã vé", "Tên phim", "Giờ chiếu", "Phòng", "Ghế", "Giá vé", "Ngày đặt"};
        modelVe = new DefaultTableModel(cols, 0);
        tblVe = new JTable(modelVe);
        JScrollPane scr = new JScrollPane(tblVe);
        pnlTable.add(scr, BorderLayout.CENTER);

       
        pnlCenter.add(pnlLoc, BorderLayout.NORTH);
        pnlCenter.add(pnlThongKe, BorderLayout.SOUTH);
        pnlCenter.add(pnlTable, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnLamMoi = new JButton("Làm mới");
        pnlButton.add(btnLamMoi);
        add(pnlButton, BorderLayout.SOUTH);

        
        btnThongKe.addActionListener(e -> actionThongKe());
        btnLamMoi.addActionListener(e -> lamMoi());
    }

    private void actionThongKe() {
        modelVe.setRowCount(0);
        
        double tong = 0;
        Date ngay = dateChooser.getDate();
        ArrayList<Ve> listVe = veDAO.findVeByNgay(ngay);
        for (Ve ve : listVe) {
        	tong += ve.getGia();
			String[] row = {ve.getMaVe(), ve.getLichChieu().getPhim().getTenPhim(), ve.getLichChieu().getGioBatDau().toString(),
					ve.getLichChieu().getPhong().getMaPhong(), ve.getGhe().getMaGhe(),ve.getGia()+"", ve.getThoiGianDat().toString()};
			modelVe.addRow(row);
		}
        

        lblTongVe.setText(modelVe.getRowCount() + " vé");
        lblTongDoanhThu.setText(tong+" VNĐ");
    }

    private void lamMoi() {
        dateChooser.setDate(new Date());
        modelVe.setRowCount(0);
        lblTongVe.setText("0 vé");
        lblTongDoanhThu.setText("0 VNĐ");
    }

}
