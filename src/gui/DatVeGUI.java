package gui;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.DatVeDAO;
import dao.GheDAO;
import dao.KhachHangDAO;
import dao.LichChieuDAO;
import dao.PhimDAO;
import dao.PhongDAO;
import entity.*;


/**
 * Giao diện đặt vé của hệ thống quản lý rạp chiếu phim.
 * 
 * Lớp này cho phép người dùng:
 * 
 * Nhập thông tin khách hàng (họ tên, số điện thoại).
 * Chọn phim, phòng chiếu và suất chiếu.
 * Chọn loại ghế, ghế cụ thể và số lượng vé cần đặt.
 * Hiển thị thông tin phim (poster, chi tiết phim,...)
 * Xác nhận hoặc làm mới thông tin đặt vé.
 * 
 * Giao diện được xây dựng bằng các thành phần Swing như JPanel,  JLabel, JComboBox, JTextField, và  JButton.
 * 
 * @author Phạm Tuấn Đạt
 * @version 1.0
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
//	private JLabel lblNgayChieu;
	private JLabel lblThoiLuong;
	private static final int WIDTH = 1200, HEIGHT = 700;
	private DatVeDAO datve;
	private PhongDAO phong;
	private PhimDAO phim;
	private JLabel lblTheLoai;
	private DatVe dv;
	private LichChieuDAO lichChieu;

	private JLabel lblNamXB;

	
 	public DatVeGUI(){
		this.datve = new DatVeDAO();
		this.phong = new PhongDAO();
		this.phim = new PhimDAO();
		this.lichChieu = new LichChieuDAO();
	    setLayout(new BorderLayout());
		initDatVe();
	}
	
	private void initDatVe() {	
		JPanel pnlTitle = new JPanel();
		JLabel lblTitle;
		pnlTitle.add(Box.createVerticalStrut(10));
		pnlTitle.add(lblTitle = new JLabel("ĐẶT VÉ", SwingConstants.CENTER));
		pnlTitle.add(Box.createVerticalStrut(10));
		lblTitle.setFont(new Font("arial", Font.BOLD, 35));
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
		pnlChonPhim.add(new JLabel("Lịch chiếu:"));pnlChonPhim.add(cboLichChieu= new JComboBox<String>(getDanhSachTenLichChieu()));
		
		
		
		JPanel pnlChonGhe = new JPanel();
		pnlChonGhe.setBorder(BorderFactory.createTitledBorder("CHỌN LOẠI VÉ VÀ GHẾ"));
		pnlChonGhe.setLayout(new GridLayout(5, 2, 10, 10));
		
		pnlChonGhe.add(new JLabel("Loại ghế chọn: ", SwingConstants.CENTER));
		pnlChonGhe.add(lblListLoaiGhe = new JLabel("Chưa chọn"));
		pnlChonGhe.add(new JLabel("Ghế đã chọn:", SwingConstants.CENTER));
		pnlChonGhe.add(lblListGhe =  new JLabel("Chưa chọn ghế"));
		pnlChonGhe.add(new JLabel("Số lượng ghế:", SwingConstants.CENTER));
		pnlChonGhe.add(lblSLVe =  new JLabel("Chưa chọn"));
		pnlChonGhe.add(btnChonGhe = new JButton("Chọn ghế"));
		
		btnChonGhe.setBackground(Color.red);
		btnChonGhe.setForeground(Color.white);
		
		
		lblListLoaiGhe.setEnabled(false);
		lblListGhe.setEnabled(false);
		lblSLVe.setEnabled(false);
		
		JPanel pnlButton = new JPanel();
		pnlButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		pnlButton.add(btnLamMoi= new JButton("Làm mới"));
		pnlButton.add(btnXacNhanDatVe= new JButton("Xác nhận đặt vé"));
		
		btnLamMoi.setBackground(Color.green);
		btnXacNhanDatVe.setBackground(Color.blue);
		btnLamMoi.setForeground(Color.white);
		btnXacNhanDatVe.setForeground(Color.white);
		
		
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
	
		ImageIcon icon = new ImageIcon(new ImageIcon("img/doraemon.jpg").getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH));
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
		pnlThongTinPhim.add(new JLabel("Năm xuất bản:")).setEnabled(false);
		pnlThongTinPhim.add(lblNamXB=new JLabel());
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
	    new DatVeGUI();
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if(source.equals(btnLamMoi)) {
			txtTenKH.setText("");
			txtSDT.setText("");
			lblListGhe.setText("Chưa chọn");
			lblListLoaiGhe.setText("Chưa chọn");
			lblSLVe.setText("Chưa chọn");
			txtTenKH.requestFocus();
			cboPhim.setSelectedIndex(0);
			cboLichChieu.setSelectedIndex(0);
			cboPhong.setSelectedIndex(0);
			
		}else if(source.equals(btnChonGhe)) {
			GheDAO ghe = new GheDAO();
		    ChonGheGUI chonGheGUI =	new ChonGheGUI("P01", phong, ghe, this);
		    chonGheGUI.setVisible(true);
		    
		}else if(source.equals(btnXacNhanDatVe)) {
			dv = new DatVe(TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, null);
			if(datve.addDatVe(dv)) {
				if(saveThongThinKhachHang()) {
					JOptionPane.showConfirmDialog(this, "Đặt vé thành công.");
					return;
				}
				else {
					JOptionPane.showConfirmDialog(this, "Đặt vé thất bại. Vui lòng kiểm tra lại.");
					return;
				}
			}
		}else if(source.equals(cboPhim)) {
			showThongTinPhim();
		}
		
	}
	
	private boolean saveThongThinKhachHang() {
		KhachHangDAO khachHang = new KhachHangDAO();
		int i = 11;
		return khachHang.addKhachHang(new KhachHang("KH"+(char)(i++), txtTenKH.getText(), txtSDT.getText()));
	}

	
	public void showGhe(ArrayList<String> dsGheChon) {
		
		String s = new String();
		for (String string : dsGheChon) {
			s += string +", ";
		}
		
		lblListGhe.setEnabled(true);
		lblListGhe.setText(s);
		lblListGhe.setEnabled(false);
		
		lblSLVe.setEnabled(true);
		lblSLVe.setText(String.valueOf(dsGheChon.size()));
		lblSLVe.setEnabled(false);
	}
	
	private String[] getDanhSachTenPhong() {
		ArrayList<Phong> listP = phong.getAllPhong();
		String[] phongItems = new String[listP.size()+1];
		phongItems[0] = "----------------------------- Phòng ------------------------------------";
		for(int i=0; i<listP.size(); i++) {
			phongItems[i+1] = listP.get(i).getTenPhong();
		}
		return phongItems;
	}
	private String[] getDanhSachTenPhim() {
		List<Phim> lp = phim.getAll();
		String[] phimItems = new String[lp.size()+1];
		phimItems[0] = "------------------------------- Phim ------------------------------------";
		for(int i=0; i<lp.size(); i++) {
			phimItems[i+1] = lp.get(i).getTenPhim();
		}
		return phimItems;
	}
	private String[] getDanhSachTenLichChieu() {
		ArrayList<LichChieu> lc = lichChieu.getAllLichChieu();
		String[] lichItems = new String[lc.size()+1];
		lichItems[0] = "--------------------------- Lịch Chiếu ---------------------------------";
		for(int i=0; i<lc.size(); i++) {
			lichItems[i+1] = lc.get(i).getGioBatDau().toString();
		}
		return lichItems;
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
			lblNamXB.setText(String.valueOf(p.getNamPhatHanh()));
			lblThoiLuong.setText(String.valueOf(p.getThoiLuongChieu() + " phút"));
		}else {
			System.out.println("Không tìm thấy Phim");
		}
		
	}

	
}
