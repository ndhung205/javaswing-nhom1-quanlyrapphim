package gui;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.DatVeDAO;
import dao.PhimDAO;
import dao.PhongDAO;
import entity.*;


/***
 * 
 * @author Tuan Dat
 */
public class DatVeGUI extends JPanel implements ActionListener{
	private JTextField txtTenKH;
	private JTextField txtSDT;
	private JLabel lblTenKH;
	private JLabel lblSDT;
	private JComboBox<String> cboPhim;
	private JComboBox<String> cboPhong;
	private JComboBox<String> cboLichChieu;
	private JButton btnLamMoi;
	private JButton btnXacNhanDatVe;
	private JLabel lblPosterPhim;
	private JLabel lblSLVe;
	private JLabel lblListGhe;
	private JLabel lblListLoaiGhe;
	private JButton btnChonGhe;
	private JLabel lblTenPhim;
	private JLabel lblMoTa;
	private JLabel lblNgayChieu;
	private JLabel lblThoiLuong;
	private static final int WIDTH = 1200, HEIGHT = 700;
	private DatVeDAO datve;
	private PhongDAO phong;
	private PhimDAO phim;
	private JLabel lblTheLoai;

	
	public DatVeGUI(DatVeDAO datve, PhongDAO phong, PhimDAO phim){
		this.datve = datve;
	    this.phong = phong;
	    this.phim = phim;
	    setLayout(new BorderLayout());
		initDatVe();
	}
	
	private void initDatVe() {	
		JPanel pnlTitle = new JPanel();
		JLabel lblTitle;
		pnlTitle.add(lblTitle = new JLabel("ĐẶT VÉ", SwingConstants.CENTER));
		lblTitle.setFont(new Font("arial", Font.BOLD, 40));
		lblTitle.setForeground(Color.blue);
		
		add(lblTitle, BorderLayout.NORTH);
		
		JPanel pnlCenter = new JPanel();
		pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
		
		
		JPanel pnlThongTinKH = new JPanel();
		pnlThongTinKH.setLayout(new BoxLayout(pnlThongTinKH, BoxLayout.Y_AXIS));
		pnlThongTinKH.setBorder(BorderFactory.createTitledBorder("THÔNG TIN KHÁCH HÀNG"));
		pnlThongTinKH.setPreferredSize(new Dimension(700, 90));
		
		Box row = Box.createHorizontalBox();
		row.add(lblTenKH= new JLabel("Họ và tên khách hàng:"));
		row.add(txtTenKH= new JTextField());
		pnlThongTinKH.add(row);
		
		row = Box.createHorizontalBox();
		row.add(lblSDT= new JLabel("Số điện thoại:"));
		row.add(txtSDT= new JTextField());
		pnlThongTinKH.add(Box.createVerticalStrut(10));
		pnlThongTinKH.add(row);
		
		
		lblTenKH.setPreferredSize(new Dimension(150, 30));
		lblSDT.setPreferredSize(lblTenKH.getPreferredSize());
		
		JPanel pnlChonPhim = new JPanel();
		pnlChonPhim.setBorder(BorderFactory.createTitledBorder("CHỌN PHIM VÀ SUẤT CHIẾU"));
		pnlChonPhim.setLayout(new GridLayout(4, 2, 5, 5));
		pnlChonPhim.add(new JLabel("Phim: "));pnlChonPhim.add(cboPhim= new JComboBox<String>(getDanhSachTenPhim()));
		pnlChonPhim.add(new JLabel("Phòng:"));pnlChonPhim.add(cboPhong= new JComboBox<String>(getDanhSachTenPhong()));
		pnlChonPhim.add(new JLabel("Lịch chiếu:"));pnlChonPhim.add(cboLichChieu= new JComboBox<String>());
		
		
		
		JPanel pnlChonGhe = new JPanel();
		pnlChonGhe.setBorder(BorderFactory.createTitledBorder("CHỌN LOẠI VÉ VÀ GHẾ"));
		pnlChonGhe.setLayout(new GridLayout(5, 2, 10, 10));
		
		pnlChonGhe.add(new JLabel("Loại ghế: "));
		pnlChonGhe.add(lblListLoaiGhe = new JLabel("Chưa chọn"));
		pnlChonGhe.add(new JLabel("Ghế đã chọn:"));
		pnlChonGhe.add(lblListGhe =  new JLabel("Chưa chọn ghế"));
		pnlChonGhe.add(new JLabel("Số lượng ghế:"));
		pnlChonGhe.add(lblSLVe =  new JLabel("Chưa chọn"));
		pnlChonGhe.add(btnChonGhe = new JButton("Chọn ghế"));
		
		
		lblListLoaiGhe.setEnabled(false);
		lblListGhe.setEnabled(false);
		lblSLVe.setEnabled(false);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		pnlButton.add(btnLamMoi= new JButton("Làm mới"));
		pnlButton.add(btnXacNhanDatVe= new JButton("Xác nhận đặt vé"));
		
		
		pnlCenter.add(pnlThongTinKH);
		pnlCenter.add(Box.createVerticalStrut(10));
		pnlCenter.add(pnlChonPhim);
		pnlCenter.add(Box.createVerticalStrut(10));
		pnlCenter.add(pnlChonGhe);
		pnlCenter.add(Box.createVerticalStrut(10));
		pnlCenter.add(pnlButton);
		
		JPanel pnlLeftContainer = new JPanel();
		pnlLeftContainer.setPreferredSize(new Dimension(700, pnlLeftContainer.getHeight()));
		pnlLeftContainer.add(pnlCenter);

		
		JPanel pnlEast = new JPanel();
		pnlEast.setBorder(BorderFactory.createTitledBorder("THÔNG TIN PHIM"));
		pnlEast.setPreferredSize(new Dimension(450, pnlEast.getHeight()));
	
		ImageIcon icon = new ImageIcon(new ImageIcon("img/doraemon.jpg").getImage().getScaledInstance(300, 200, java.awt.Image.SCALE_SMOOTH));
		lblPosterPhim = new JLabel(icon);
		lblPosterPhim.setPreferredSize(new Dimension(400, 300));
		
		JPanel pnlThongTinPhim = new JPanel();
		pnlThongTinPhim.setLayout(new GridLayout(5, 2, 8, 8));
		
		pnlThongTinPhim.add(new JLabel("Tên phim: ")).setEnabled(false);
		pnlThongTinPhim.add(lblTenPhim = new JLabel());
		pnlThongTinPhim.add(new JLabel("Thể loại phim:")).setEnabled(false);
		pnlThongTinPhim.add(lblTheLoai=new JLabel());
		pnlThongTinPhim.add(new JLabel("Mô tả:")).setEnabled(false);
		pnlThongTinPhim.add(lblMoTa=new JLabel());
		pnlThongTinPhim.add(new JLabel("Ngày chiếu:")).setEnabled(false);
		pnlThongTinPhim.add(lblNgayChieu=new JLabel());
		pnlThongTinPhim.add(new JLabel("Thời lượng:")).setEnabled(false);
		pnlThongTinPhim.add(lblThoiLuong = new JLabel());
		
		lblTenPhim.setPreferredSize(new Dimension(200, 20));
		
		btnChonGhe.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnXacNhanDatVe.addActionListener(this);
		cboPhim.addActionListener(this);
		
		pnlEast.add(lblPosterPhim);
		pnlEast.add(pnlThongTinPhim);
		
		add(pnlLeftContainer, BorderLayout.CENTER);
		add(pnlEast, BorderLayout.EAST);
		
		setSize(WIDTH, HEIGHT);
	 	setVisible(true);
	 	
	}
	public static void main(String[] args) {
		DatVeDAO datve = new DatVeDAO();
	    PhongDAO phong = new PhongDAO();
	    PhimDAO phim = new PhimDAO();
	    datve.connectDatabase();
	    new DatVeGUI(datve, phong, phim);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source.equals(btnLamMoi)) {
			txtTenKH.setText("");
			txtSDT.setText("");
			lblListGhe.setText("");
			lblListLoaiGhe.setText("");
			lblSLVe.setText("");
			lblTheLoai.setText("");
			txtTenKH.requestFocus();
			cboPhim.setSelectedIndex(0);
			cboLichChieu.setSelectedIndex(0);
			cboPhong.setSelectedIndex(0);
			
		}else if(source.equals(btnChonGhe)) {
			
		}else if(source.equals(btnXacNhanDatVe)) {
			if(datve.addDatVe()) {
				
			}
		}else if(source.equals(cboPhim)) {
			showThongTinPhim();
		}
		
	}
	
	private String[] getDanhSachTenPhong() {
		String[] phongItems = new String[phong.getSize()+1];
		phongItems[0] = "----------------------------- Phòng ------------------------------------";
		for(int i=0; i<phong.getSize(); i++) {
			phongItems[i+1] = phong.findPhongByIndex(i).getTenPhong();
		}
		return phongItems;
	}
	private String[] getDanhSachTenPhim() {
		String[] phimItems = new String[phim.getSize()+1];
		phimItems[0] = "----------------------------- Phim ------------------------------------";
		for(int i=0; i<phim.getSize(); i++) {
			phimItems[i+1] = phim.findPhimByIndex(i).getTenPhim();
		}
		return phimItems;
	}
	
	private void showThongTinPhim() {
		String tenPhim =  (String) cboPhim.getSelectedItem();
		Phim p = new Phim();
		
		for (int i=0;i<phim.getSize(); i++) {
			p = phim.findPhimByTen(tenPhim);
		}
		if(p != null) {
			lblTenPhim.setText(p.getTenPhim());
			lblTheLoai.setText(p.getLoaiPhim().getTenLoaiPhim());
			lblMoTa.setText(p.getMoTa());
			lblNgayChieu.setText(String.valueOf(p.getNamPhatHanh()));
			lblThoiLuong.setText(String.valueOf(p.getThoiLuongChieu()));
		}else {
			System.out.println("Không tìm thấy Phim");
		}
		
	}

	
}
