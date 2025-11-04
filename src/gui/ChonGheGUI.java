package gui;


import java.awt.BorderLayout;
import java.awt.Color;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
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
	private JLabel lblThoiLuong;
	private static final int WIDTH = 1200, HEIGHT = 700;
	private DatVeDAO datve;
	private PhongDAO phong;
	private PhimDAO phim;
	private JLabel lblTheLoai;
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
		lblTitle.setFont(new Font("SansSerif", Font.BOLD, 35));
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
		
		cboLichChieu.setEditable(true);
		cboPhim.setEditable(true);
		cboPhong.setEditable(true);
		
		
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

		
		JPanel pnlEast = createPanelThongTinPhim();
		
		btnChonGhe.addActionListener(this);
		btnLamMoi.addActionListener(this);
		btnXacNhanDatVe.addActionListener(this);
		cboPhim.addActionListener(this);
		

		
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
			actionLamMoiForm();
			
		}else if(source.equals(btnChonGhe)) {
		    ChonGheGUI chonGheGUI =	new ChonGheGUI("P01",this);
		    chonGheGUI.setVisible(true);
		    
		}else if(source.equals(btnXacNhanDatVe)) {
			actionDatVe();
		}else if(source.equals(cboPhim)) {
			showThongTinPhim();
		}
		
	}
	
	private JPanel createPanelThongTinPhim() {
	    JPanel pnlEast = new JPanel(new BorderLayout());
	    pnlEast.setBorder(BorderFactory.createTitledBorder("THÔNG TIN PHIM"));
	    pnlEast.setPreferredSize(new Dimension(450, HEIGHT));

	    lblPosterPhim = new JLabel("", SwingConstants.CENTER);
	    lblPosterPhim.setPreferredSize(new Dimension(400, 300));
	    pnlEast.add(lblPosterPhim, BorderLayout.NORTH);

	    JPanel pnlThongTinPhim = new JPanel(new GridBagLayout());
	    GridBagConstraints gbc = new GridBagConstraints();
	    gbc.insets = new java.awt.Insets(5, 5, 5, 5);
	    gbc.fill = GridBagConstraints.HORIZONTAL;

	    // label helper
	    Font fontLabel = new Font("SansSerif", Font.BOLD, 13);

	    addRow(pnlThongTinPhim, gbc, 0, "Tên phim:", lblTenPhim = new JLabel(), fontLabel);
	    addRow(pnlThongTinPhim, gbc, 1, "Thể loại:", lblTheLoai = new JLabel(), fontLabel);
	    addRow(pnlThongTinPhim, gbc, 2, "Mô tả:", lblMoTa = new JLabel(), fontLabel);
	    addRow(pnlThongTinPhim, gbc, 3, "Năm xuất bản:", lblNamXB = new JLabel(), fontLabel);
	    addRow(pnlThongTinPhim, gbc, 4, "Thời lượng:", lblThoiLuong = new JLabel(), fontLabel);

	    pnlEast.add(pnlThongTinPhim, BorderLayout.CENTER);

	    return pnlEast;
	}

	private void addRow(JPanel panel, GridBagConstraints gbc, int y, String labelText, JLabel valueLabel, Font font) {
	    JLabel lbl = new JLabel(labelText);
	    lbl.setFont(font);
	    lbl.setEnabled(false);

	    gbc.gridx = 0;
	    gbc.gridy = y;
	    gbc.weightx = 0.2; 
	    panel.add(lbl, gbc);

	    gbc.gridx = 1;
	    gbc.weightx = 0.8; 
	    panel.add(valueLabel, gbc);
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
			ImageIcon icon = new ImageIcon(new ImageIcon(p.getPath()).getImage().getScaledInstance(300, 300, java.awt.Image.SCALE_SMOOTH));
			lblPosterPhim.setIcon(icon);
			lblTenPhim.setText(p.getTenPhim());
			lblTheLoai.setText(p.getLoaiPhim().getTenLoaiPhim());
			lblMoTa.setText("<html><div style='width:250px;'>" + p.getMoTa() + "</div></html>");
			lblNamXB.setText(String.valueOf(p.getNamPhatHanh()));
			lblThoiLuong.setText(String.valueOf(p.getThoiLuongChieu() + " phút"));
		}else {
			System.out.println("Không tìm thấy Phim");
		}
		
	}

	private void actionDatVe() {
	    try {
	        String tenKH = txtTenKH.getText().trim();
	        String sdt = txtSDT.getText().trim();

	        if (tenKH.isEmpty() || sdt.isEmpty()) {
	            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin khách hàng!");
	            return;
	        }

	        String tenPhim = (String) cboPhim.getSelectedItem();
	        String tenPhong = (String) cboPhong.getSelectedItem();
	        String gioChieu = (String) cboLichChieu.getSelectedItem();

	        if (tenPhim == null || tenPhim.contains("Phim")) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn phim hợp lệ!");
	            return;
	        }
	        if (tenPhong == null) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn phòng hợp lệ!");
	            return;
	        }
	        if (gioChieu == null) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn lịch chiếu hợp lệ!");
	            return;
	        }

	        if (lblListGhe.getText().equals("Chưa chọn ghế")) {
	            JOptionPane.showMessageDialog(this, "Vui lòng chọn ghế trước khi đặt vé!");
	            return;
	        }

	        KhachHangDAO khDAO = new KhachHangDAO();
	        String maKH = "KH" + System.currentTimeMillis() % 100000;
	        KhachHang kh = new KhachHang(maKH, tenKH, sdt);
	        khDAO.addKhachHang(kh);

	        String maDatVe = datve.generateNewId();
	        DatVe datVe = new DatVe(maDatVe, "Đã xác nhận", LocalDateTime.now());
	        boolean ok = datve.addDatVe(datVe);

	        if (!ok) {
	            JOptionPane.showMessageDialog(this, "Lỗi khi thêm đặt vé vào database!");
	            return;
	        }

	        JOptionPane.showMessageDialog(this,
	                "✅ Đặt vé thành công!\n"
	                + "Khách hàng: " + tenKH
	                + "\nSĐT: " + sdt
	                + "\nPhim: " + tenPhim
	                + "\nPhòng: " + tenPhong
	                + "\nSuất chiếu: " + gioChieu
	                + "\nGhế: " + lblListGhe.getText()
	        );
	        actionLamMoiForm();

	    } catch (Exception ex) {
	        ex.printStackTrace();
	        JOptionPane.showMessageDialog(this, "Lỗi khi đặt vé: " + ex.getMessage());
	    }
	}
	private void actionLamMoiForm() {
		txtTenKH.setText("");
		txtSDT.setText("");
		lblListGhe.setText("Chưa chọn");
		lblListLoaiGhe.setText("Chưa chọn");
		lblSLVe.setText("Chưa chọn");
		txtTenKH.requestFocus();
		cboPhim.setSelectedIndex(0);
		cboLichChieu.setSelectedIndex(0);
		cboPhong.setSelectedIndex(0);
	}
	
}
