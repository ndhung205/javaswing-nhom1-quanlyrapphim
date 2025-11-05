package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.KhuyenMaiDAO;
import entity.KhuyenMai;

/***
 * @author Tuan Dat
 */
public class KhuyenMaiGUI extends JFrame implements ActionListener, MouseListener{
	
	private JTextField txtMa;
	private JTextField txtTen;
	private JTextField txtPhanTram;
	private JTextField txtNgayBD;
	private JTextField txtNgayKT;
	private JTable tableKM;
	private DefaultTableModel modelTableKM;
	private JButton btnThem;
	private JButton btnSua;
	private JButton btnXoa;
	private JButton btnLamMoi;
	private JTextField txtSTG;
	private JTextArea txtDK;
	private KhuyenMaiDAO kmDAO = new KhuyenMaiDAO();
	private JTextField txtTimKiem;
	private JButton btnTim;
	private JButton btnHienTatCa;

	public KhuyenMaiGUI() {
		initComponents();
		setLocationRelativeTo(null);
	}
	private void initComponents() {
	    setSize(1200, 700);
	    setDefaultCloseOperation(EXIT_ON_CLOSE);
	    setLayout(new BorderLayout());
	    
	    JPanel pnlTitle = new JPanel();
	    JLabel lblTitle = new JLabel("QUẢN LÝ KHUYẾN MÃI");	
	    lblTitle.setFont(new Font("arial", Font.BOLD, 25));
	    pnlTitle.add(lblTitle);
	    add(pnlTitle, BorderLayout.NORTH);

	    Box boxForm = Box.createVerticalBox();
	    Box row;
	    JLabel lblMa, lblTen, lblPhanTram, lblNgayBD, lblNgayKT, lblSTG, lblDK, lblKM;
	    
	    row = Box.createHorizontalBox();
	    row.add(lblKM = new JLabel("THÔNG TIN KHUYẾN MÃI", SwingConstants.LEFT));
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(20));
	    
	    row = Box.createHorizontalBox();
	    row.add(lblMa = new JLabel("Mã khuyến mãi:"));
	    row.add(txtMa = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));

	    row = Box.createHorizontalBox();
	    row.add(lblTen = new JLabel("Tên khuyến mãi:"));
	    row.add(txtTen = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));

	    row = Box.createHorizontalBox();
	    row.add(lblPhanTram = new JLabel("Giảm giá (%):"));
	    row.add(txtPhanTram = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));
	    
	    row = Box.createHorizontalBox();
	    row.add(lblSTG = new JLabel("Số tiền giảm:"));
	    row.add(txtSTG = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));

	    row = Box.createHorizontalBox();
	    row.add(lblNgayBD = new JLabel("Ngày bắt đầu:"));
	    row.add(txtNgayBD = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));
	    
	    row = Box.createHorizontalBox();
	    row.add(lblNgayKT = new JLabel("Ngày kết thúc:"));
	    row.add(txtNgayKT = new JTextField());
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(10));

	    row = Box.createHorizontalBox();
	    row.add(lblDK = new JLabel("Điều kiện:"));
	    row.add(txtDK = new JTextArea(10, 1));
	    boxForm.add(row);
	    boxForm.add(Box.createVerticalStrut(20));
	
	    lblKM.setFont(new Font("Tohoma", Font.BOLD, 20));

	    lblMa.setPreferredSize(new Dimension(150, 26));
	    lblTen.setPreferredSize(lblMa.getPreferredSize());
	    lblPhanTram.setPreferredSize(lblMa.getPreferredSize());
	    lblNgayBD.setPreferredSize(lblMa.getPreferredSize());
	    lblNgayKT.setPreferredSize(lblMa.getPreferredSize());
	    lblSTG.setPreferredSize(lblMa.getPreferredSize());
	    lblDK.setPreferredSize(lblMa.getPreferredSize());
	    
	    
	    txtMa.setPreferredSize(new Dimension(270, 26));
	    txtTen.setPreferredSize(txtMa.getPreferredSize());
	    txtNgayBD.setPreferredSize(txtMa.getPreferredSize());
	    txtNgayKT.setPreferredSize(txtMa.getPreferredSize());
	    txtPhanTram.setPreferredSize(txtMa.getPreferredSize());
	    txtSTG.setPreferredSize(txtMa.getPreferredSize());
	    
	   

	    JPanel pnlButton = new JPanel();
	    pnlButton.add(btnThem = new JButton("Thêm"));
	    pnlButton.add(btnSua = new JButton("Sửa"));
	    pnlButton.add(btnXoa = new JButton("Xóa"));
	    pnlButton.add(btnLamMoi = new JButton("Làm mới"));
	    boxForm.add(pnlButton);

	    btnLamMoi.addActionListener(this);
	    btnSua.addActionListener(this);
	    btnThem.addActionListener(this);
	    btnXoa.addActionListener(this);
	    
	    btnXoa.setForeground(Color.white);
	    btnSua.setForeground(Color.white);
	    btnLamMoi.setForeground(Color.white);
	    btnThem.setForeground(Color.white);
	    
	    btnThem.setBackground(new Color(39, 174, 96));    
	    btnSua.setBackground(new Color(52, 152, 219));     
	    btnXoa.setBackground(new Color(231, 76, 60));      
	    btnLamMoi.setBackground(new Color(149, 165, 166)); 
	    
	    
	    Object[] columNames = {"Mã khuyến mãi", "Tên khuyến mãi", "Giảm (%)", "Số tiền giảm",
	                           "Ngày bắt đầu", "Ngày kết thúc", "Điều kiện"};
	    modelTableKM = new DefaultTableModel(null, columNames);
	    tableKM = new JTable(modelTableKM);
	    tableKM.setRowHeight(20);
	    tableKM.addMouseListener(this);
	    JScrollPane scp = new JScrollPane(tableKM);
	    scp.setPreferredSize(new Dimension(700, 600));
	    
	    JPanel pnlTimKiem = new JPanel();
	    
	    pnlTimKiem.add(new JLabel("Tìm kiếm: "));
	    pnlTimKiem.add(txtTimKiem = new JTextField());
	    pnlTimKiem.add(btnTim = new JButton("🔍 Tìm kiếm"));
	    pnlTimKiem.add(btnHienTatCa = new JButton("Hiện tất cả"));
	    
	    btnTim.addActionListener(this);
	    btnTim.addActionListener(this);
	    
	    btnTim.setBackground(new Color(41, 128, 185));
	    btnHienTatCa.setBackground(new Color(155, 89, 182));
	    
	    btnTim.setForeground(Color.white);
	    btnHienTatCa.setForeground(Color.white);

	    txtTimKiem.setPreferredSize(new Dimension(250, 25));
	    
	    JPanel pnlLeft = new JPanel();
	    JPanel pnlRight = new JPanel();
	    pnlRight.setLayout(new BoxLayout(pnlRight, BoxLayout.Y_AXIS));
	    
	    pnlLeft.add(boxForm);
	    
	    pnlRight.add(pnlTimKiem);
	    pnlRight.add(Box.createVerticalStrut(10));
	    pnlRight.add(scp);
	    

	    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,pnlLeft, pnlRight);
	    splitPane.setDividerLocation(450); 
	    splitPane.setContinuousLayout(true);
	    splitPane.setOneTouchExpandable(true);

	    add(splitPane, BorderLayout.CENTER);

	    ArrayList<KhuyenMai> listKM = kmDAO.getAll();
	    loadDataOnTable(listKM);

	    setLocationRelativeTo(null);
	    setVisible(true);
	}

	
	public static void main(String[] args) {new KhuyenMaiGUI();}
	@Override
	public void actionPerformed(ActionEvent e) {
		Object source  = e.getSource();
		if(source.equals(btnLamMoi)) {
			actionLamMoi();
		}else if(source.equals(btnSua)) {
			actionSua();
			ArrayList<KhuyenMai> listKM = kmDAO.getAll();
		    loadDataOnTable(listKM);
		}else if(source.equals(btnThem)) {
			actionThem();
		}else if (source.equals(btnXoa)) {
			actionXoa();
		}else if(source.equals(btnTim)) {
			actionTim();
		}else if(source.equals(btnHienTatCa)) {
			ArrayList<KhuyenMai> listKM = kmDAO.getAll();
		    loadDataOnTable(listKM);
		}
		
	}
	private void actionThem() {
	    try {
	        String ma = txtMa.getText().trim();
	        String ten = txtTen.getText().trim();
	        double phanTram = Double.parseDouble(txtPhanTram.getText().trim());
	        double soTienGiam = Double.parseDouble(txtSTG.getText().trim());
	        String ngayBDStr = txtNgayBD.getText().trim();
	        String ngayKTStr = txtNgayKT.getText().trim();
	        String dieuKien = txtDK.getText().trim();

	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date utilNgayBD = sdf.parse(ngayBDStr);
	        java.util.Date utilNgayKT = sdf.parse(ngayKTStr);
	        java.sql.Date sqlNgayBD = new java.sql.Date(utilNgayBD.getTime());
	        java.sql.Date sqlNgayKT = new java.sql.Date(utilNgayKT.getTime());
	        
	        KhuyenMai km = new KhuyenMai(ma, ten, phanTram, soTienGiam, sqlNgayBD, sqlNgayKT, dieuKien, true);


	        if (kmDAO.addKhuyenMai(km)) {
	            JOptionPane.showMessageDialog(this, "Thêm thành công.");
	            String[] row = {km.getMaKM(),km.getTenKM(),String.valueOf(km.getPhanTram()),String.valueOf(km.getSoTienGiam()),sdf.format(km.getNgayBD()),
	                sdf.format(km.getNgayKT()),km.getDieuKien()};
	            modelTableKM.addRow(row);
	        } else {
	            JOptionPane.showMessageDialog(this, "Thêm thất bại. Vui lòng kiểm tra lại.");
	        }

	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Lỗi dữ liệu: " + e.getMessage() + "\nVui lòng nhập đúng định dạng ngày (dd/MM/yyyy)");
	        e.printStackTrace();
	    }
	}

	private void actionXoa() {
		int row = tableKM.getSelectedRow();
		if(row == -1) {
			JOptionPane.showConfirmDialog(this, "Vui lòng chọn dòng cần xóa!");
			return;
		}else {
			String ma = tableKM.getValueAt(row,0).toString().trim();
			int thongBao = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa dòng này không!");
			if(thongBao == JOptionPane.YES_OPTION) {
				if(kmDAO.removeKhuyenMai(ma)) {
					modelTableKM.removeRow(row);
					return;
				}
			}else if(thongBao == JOptionPane.NO_OPTION){
				return;
			}else {
				JOptionPane.showConfirmDialog(this, "Lỗi không thế xóa dòng này!");
			}
		}
	}
	private void actionSua() {
	    try {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        java.util.Date utilDateBD = sdf.parse(txtNgayBD.getText().trim());
	        java.util.Date utilDateKT = sdf.parse(txtNgayKT.getText().trim());
	        java.sql.Date sqlDateBD = new java.sql.Date(utilDateBD.getTime());
	        java.sql.Date sqlDateKT = new java.sql.Date(utilDateKT.getTime());
	        
	        KhuyenMai km = new KhuyenMai(txtMa.getText(), txtTen.getText(),Double.parseDouble(txtPhanTram.getText()), 
	                Double.parseDouble(txtSTG.getText()),sqlDateBD, sqlDateKT,txtDK.getText(), true);
	        
	        if (kmDAO.updateKhuyenMai(km)) {
	            JOptionPane.showMessageDialog(this, "Cập nhật thành công.");
	        } else {
	            JOptionPane.showMessageDialog(this, "Cập nhật thất bại. Vui lòng kiểm tra lại.");
	        }
	    } catch (Exception e) {
	        JOptionPane.showMessageDialog(this, "Lỗi dữ liệu hoặc sai định dạng ngày (dd/MM/yyyy).");
	        e.printStackTrace();
	    }
	}


	
	private void actionLamMoi() {
		txtDK.setText("");
		txtMa.setText("");
		txtNgayBD.setText("");
		txtNgayKT.setText("");
		txtPhanTram.setText("");
		txtSTG.setText("");
		txtTen.setText("");
		txtMa.requestFocus();
		
	}
	
 	private void loadDataOnTable(ArrayList<KhuyenMai> list) {
 		modelTableKM.setRowCount(0);
 		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		for(int i=0; i<list.size(); i++) {
			KhuyenMai km = list.get(i);
			String[] row = {km.getMaKM(), km.getTenKM(), String.valueOf(km.getPhanTram()),String.valueOf(km.getSoTienGiam()),
					sdf.format(km.getNgayBD()), sdf.format(km.getNgayKT()), km.getDieuKien()};
			modelTableKM.addRow(row);
		}
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		int row = tableKM.getSelectedRow();
		
		txtDK.setText(tableKM.getValueAt(row, 6).toString());
		txtMa.setText(tableKM.getValueAt(row,0).toString());
		txtNgayBD.setText(tableKM.getValueAt(row,4).toString());
		txtNgayKT.setText(tableKM.getValueAt(row,5).toString());
		txtPhanTram.setText(tableKM.getValueAt(row, 2).toString());
		txtSTG.setText(tableKM.getValueAt(row,3).toString());
		txtTen.setText(tableKM.getValueAt(row,1).toString());
		
	}
	@Override
	public void mousePressed(MouseEvent e) {}
	@Override
	public void mouseReleased(MouseEvent e) {}
	@Override
	public void mouseEntered(MouseEvent e) {}
	@Override
	public void mouseExited(MouseEvent e) {}
	private void actionTim() {
		String ma = txtTimKiem.getText().trim();
		if(ma.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Vui lòng nhập dữ liêu tìm kiếm");
			return;
		}
		ArrayList<KhuyenMai> km = kmDAO.findKhuyenMaiById(ma);
		if(km== null) {
			JOptionPane.showMessageDialog(this, "Không tìm thấy " +ma);return;
		}
		loadDataOnTable(km);
	}
}
