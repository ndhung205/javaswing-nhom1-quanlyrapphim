package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import dao.HoaDonDAO;
import dao.KhuyenMaiDAO;
import dao.NhanVienDAO;
import dao.PhuongThucThanhToanDAO;
import dao.ThueDAO;
import entity.DatVe;
import entity.HoaDon;
import entity.KhachHang;
import entity.KhuyenMai;
import entity.NhanVien;
import entity.PhuongThucThanhToan;

import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;


/***
 * @author Tuan Dat
 */
@SuppressWarnings("serial")
public class ThanhToanGUI extends JFrame implements ActionListener {

    private JTextField txtNhanVien;
    private JTextField txtTongTien;
    private JTextField txtGiamGia;
    private JTextField txtPhaiTra;
    private JComboBox<String> cboPhuongThuc;
    private JButton btnXacNhan;
    private JButton btnHuy;
    private double tongTien = 0, giamGia;
	private JComboBox<String> cboKM;
	private KhuyenMaiDAO kmDAO= new KhuyenMaiDAO();
	private HoaDonDAO hoaDon = new HoaDonDAO();
	private KhachHang khachHang;
	private DatVe datVe;
	private NhanVienDAO nvDAO = new NhanVienDAO();
	private ThueDAO thue = new ThueDAO() ;
	private PhuongThucThanhToanDAO ptttDAO= new PhuongThucThanhToanDAO();
	private DatVeGUI dvGUI;

    public ThanhToanGUI(DatVeGUI parent,KhachHang kh,DatVe dv, double tongTien) {
        super("Thanh Toán");
        this.tongTien = tongTien;
        this.datVe =dv;
        this.khachHang = kh;
        this.dvGUI = parent;

        initGUI();
        setLocationRelativeTo(parent);
    }

    private void initGUI() {
        setSize(450, 350);
        setLayout(new BorderLayout(10, 10));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JLabel lblTitle = new JLabel("THANH TOÁN HÓA ĐƠN", JLabel.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setForeground(Color.BLUE);
        add(lblTitle, BorderLayout.NORTH);

        JPanel pnlForm = new JPanel();
        pnlForm.setLayout(new BoxLayout(pnlForm, BoxLayout.Y_AXIS));
        pnlForm.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        pnlForm.add(createRow("Mã nhân viên:", txtNhanVien = new JTextField(20)));
        pnlForm.add(createRow("Khuyến mai: ", cboKM = new JComboBox<String>()));setKhuyenMaiOnComBox();
        pnlForm.add(createRow("Phương thức thanh toán:", cboPhuongThuc = new JComboBox<>(
                new String[]{"Tiền mặt", "Chuyển khoản", "Ví điện tử"})));
        pnlForm.add(createRow("Tổng tiền:", txtTongTien = new JTextField(formatMoney(tongTien))));
        pnlForm.add(createRow("Giảm giá:", txtGiamGia = new JTextField(formatMoney(giamGia))));
        pnlForm.add(createRow("Số tiền phải trả:", txtPhaiTra = new JTextField(formatMoney(tongTien - giamGia))));

        txtTongTien.setEditable(false);
        txtGiamGia.setEditable(false);
        txtPhaiTra.setEditable(false);
        txtPhaiTra.setFont(new Font("Arial", Font.BOLD, 14));
        txtPhaiTra.setForeground(Color.RED);

        add(pnlForm, BorderLayout.CENTER);

        JPanel pnlButton = new JPanel();
        btnXacNhan = new JButton("Xác nhận thanh toán");
        btnHuy = new JButton("Hủy");
        pnlButton.add(btnXacNhan);
        pnlButton.add(btnHuy);
        add(pnlButton, BorderLayout.SOUTH);

        // ====== Sự kiện ======
        btnXacNhan.addActionListener(this);
        btnHuy.addActionListener(this);
        cboKM.addActionListener(this);
    }

    private JPanel createRow(String labelText, JComponent input) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(160, 25));
        row.add(lbl, BorderLayout.WEST);
        row.add(input, BorderLayout.CENTER);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return row;
    }
    private void setKhuyenMaiOnComBox() {
    	ArrayList<KhuyenMai> km = kmDAO.getAll();
    	for (KhuyenMai khuyenMai : km) {
			cboKM.addItem(khuyenMai.getTenKM());
		}
    }


    private String formatMoney(double money) {
        DecimalFormat df = new DecimalFormat("#,### VNĐ");
        return df.format(money);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(btnHuy)) {
            dispose();
        } else if (src.equals(btnXacNhan)) {
            String tenNV = txtNhanVien.getText().trim();
            if (tenNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên nhân viên!");
                return;
            }

            String phuongThuc =cboPhuongThuc.getSelectedItem().toString();
            String tenKM = cboKM.getSelectedItem().toString();
            KhuyenMai km = kmDAO.findKhuyenMaiByTen(tenKM);
            if(km== null) {
            	System.out.println("Không tìm thấy Khuyến mãi "+tenKM);return;
            }
            
            this.giamGia = km.getPhanTram()!= 0? (km.getPhanTram()*tongTien)/100:km.getSoTienGiam();

            int confirm = JOptionPane.showConfirmDialog(this,"Xác nhận thanh toán bằng " + phuongThuc + "?\nSố tiền: " + txtPhaiTra.getText(),"Xác nhận",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                taoHoaDon();
                dispose();
            }
        }else if(src.equals(cboKM)) {
            String tenKM = cboKM.getSelectedItem().toString();
            KhuyenMai km = kmDAO.findKhuyenMaiByTen(tenKM);
            if(km== null) {
            	System.out.println("Không tìm thấy Khuyến mãi "+tenKM);return;
            }
            
            this.giamGia = km.getPhanTram()!= 0? (km.getPhanTram()*tongTien)/100:km.getSoTienGiam();
            txtGiamGia.setText(formatMoney(giamGia));
            txtPhaiTra.setText(formatMoney(tongTien - giamGia));
        }
    }
    private void taoHoaDon() {
    	
        String tenKM = cboKM.getSelectedItem().toString();
        KhuyenMai km = kmDAO.findKhuyenMaiByTen(tenKM);
        if(km== null) {
        	System.out.println("Không tìm thấy Khuyến mãi "+tenKM);return;
        }
        
        String maNV = txtNhanVien.getText().trim();
        if(maNV.isEmpty()) {
        	JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên.");return;
        }
        NhanVien nv = nvDAO.findNhanVienById(maNV);
        if(nv == null) {
        	JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên có mã "+ maNV+". Vui lòng kiểm tra lại.");
        	return;
        }
        String phuongThuc =cboPhuongThuc.getSelectedItem().toString();
        PhuongThucThanhToan pttt = ptttDAO.findPTTTByTen(phuongThuc);
    	String maHD = "HD" +System.currentTimeMillis()%100000;
    	HoaDon hd = new  HoaDon(maHD, khachHang, nv, datVe, LocalDateTime.now(), thue.getById("T01"), km, pttt, LocalDateTime.now(), "Đã thanh toán");
    	
    	if(hoaDon.addHoaDon(hd)) {
    		JOptionPane.showMessageDialog(this, "Thanh toán thành công.");
    		dvGUI.actionLamMoiForm();
    	}else {
    		System.out.println("Lỗi thanh toán.");
    	}
    }
    

      
}
