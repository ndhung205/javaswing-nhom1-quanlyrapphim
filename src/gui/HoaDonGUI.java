package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dao.HoaDonDAO;
import entity.HoaDon;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class HoaDonGUI extends JPanel implements ActionListener {

    private JTextField txtTimKiem;
    private JButton btnTim;
    private JTable table;
    private DefaultTableModel model;
	private JButton btnXem;
	private JButton btnXoa;
	private HoaDonDAO hoaDonDAO = new HoaDonDAO();

    public HoaDonGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        JPanel pnlTop = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pnlTop.add(new JLabel("Tìm kiếm hóa đơn:"));
        txtTimKiem = new JTextField(20);
        pnlTop.add(txtTimKiem);
        btnTim = new JButton("Tìm");
        pnlTop.add(btnTim);

        add(pnlTop, BorderLayout.NORTH);

        String[] columns = {"Mã HĐ", "Mã Đặt Vé", "Khách hàng", "Ngày lập", "Trạng thái", "Nhân viên"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnXem = new JButton("Xem");
        btnXoa = new JButton("Xóa");
        
        pnlBottom.add(btnXem);
        pnlBottom.add(btnXoa);
        add(pnlBottom, BorderLayout.SOUTH);

        btnTim.addActionListener(this);
        btnXem.addActionListener(this);
        btnXoa.addActionListener(this);

        ArrayList<HoaDon> listHD = hoaDonDAO.getAllHoaDon();
        loadDataOnTable(listHD);
    }

    private void loadDataOnTable(ArrayList<HoaDon> listHD) {	
    	for(int i=0;i<listHD.size(); i++) {
    		HoaDon hd = listHD.get(i);
    		String[] row = {hd.getMaHoaDon(),hd.getDatVe().getMaDatVe(), hd.getKhachHang().getTenKH(), 
    				hd.getNgayLapHoaDon().toString(),hd.getTinhTrang(), hd.getNhanVien().getHoTen()};
    		model.addRow(row);
    		
    	}
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        int row = table.getSelectedRow();

        if (o == btnTim) {
            ArrayList<HoaDon> listHD = new ArrayList<HoaDon>();
            String tenKH = txtTimKiem.getText().trim();
            if(tenKH.isEmpty()) {
            	JOptionPane.showMessageDialog(this, "Vui lòng nhập tên khách hàng vào ô tìm kiếm");
            	txtTimKiem.requestFocus();
            	return;
            }
            HoaDon hd =	hoaDonDAO.findHoaDonByTenKhachHang(tenKH);
            if(hd== null) {
            	JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có tên " + tenKH);
            	return;
            }
            listHD.add(hd);
            model.setRowCount(0);
            loadDataOnTable(listHD);
            
        } else if (o == btnXem) {
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn cần xem!");
                return;
            }
            String maHD = model.getValueAt(row, 0).toString();         
            HoaDon hd = hoaDonDAO.findHoaDonById(maHD);
            if (hd == null) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy hóa đơn có mã: " + maHD);
                return;
            }

            ChiTietHoaDonGUI ctGUI = new ChiTietHoaDonGUI(this, hd);
            ctGUI.setVisible(true);
            
        }else if (o == btnXoa) {
            if (row == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xóa!");
                return;
            }
            int thongBao = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa hóa đơn này?", "Xác nhận",JOptionPane.YES_NO_OPTION);
            if (thongBao == JOptionPane.YES_OPTION) {
            	if(hoaDonDAO.deleteHoaDon(table.getValueAt(row, 0).toString())) {
            		model.removeRow(row);
            		JOptionPane.showMessageDialog(this, "Xóa thành công.");
            		return;
            	}else {
            		JOptionPane.showMessageDialog(this, "Xóa thất bại. Vui lòng kiểm tra lại.");
            		return;
            	}
                
            }
        }
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Quản lý Hóa đơn");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.add(new HoaDonGUI());
        f.setSize(900, 500);
        f.setLocationRelativeTo(null);
        f.setVisible(true);
    }
}
