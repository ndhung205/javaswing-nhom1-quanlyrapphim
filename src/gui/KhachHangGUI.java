package gui;

import dao.KhachHangDAO;
import entity.KhachHang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class KhachHangGUI extends JPanel {
    private JTextField txtMaKH, txtTenKH, txtSDT, txtTimKiem;
    private JTable table;
    private DefaultTableModel model;
    private KhachHangDAO khDAO = new KhachHangDAO();

    public KhachHangGUI() {
        setSize(HEIGHT, WIDTH);
        
        setLayout(new BorderLayout(10, 10));

        // ======= PANEL NHẬP DỮ LIỆU =======
        JPanel pnlInput = new JPanel(new GridLayout(2, 4, 10, 10));
        pnlInput.setBorder(BorderFactory.createTitledBorder("Thông tin khách hàng"));

        pnlInput.add(new JLabel("Mã KH:"));
        txtMaKH = new JTextField();
        pnlInput.add(txtMaKH);

        pnlInput.add(new JLabel("Tên KH:"));
        txtTenKH = new JTextField();
        pnlInput.add(txtTenKH);

        pnlInput.add(new JLabel("SĐT:"));
        txtSDT = new JTextField();
        pnlInput.add(txtSDT);

        pnlInput.add(new JLabel("Tìm theo SĐT:"));
        txtTimKiem = new JTextField();
        pnlInput.add(txtTimKiem);

        JButton btnTim = new JButton("Tìm");
        pnlInput.add(btnTim);

        add(pnlInput, BorderLayout.NORTH);

        // ======= BẢNG DANH SÁCH =======
        String[] cols = {"Mã KH", "Tên KH", "Số điện thoại"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        JScrollPane scroll = new JScrollPane(table);
        add(scroll, BorderLayout.CENTER);

        // ======= PANEL NÚT =======
        JPanel pnlButtons = new JPanel(new FlowLayout());
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnLamMoi = new JButton("Làm mới");

        pnlButtons.add(btnThem);
        pnlButtons.add(btnSua);
        pnlButtons.add(btnXoa);
        pnlButtons.add(btnLamMoi);
        add(pnlButtons, BorderLayout.SOUTH);

        // ======= SỰ KIỆN =======
        btnThem.addActionListener(e -> themKhachHang());
        btnSua.addActionListener(e -> suaKhachHang());
        btnXoa.addActionListener(e -> xoaKhachHang());
        btnLamMoi.addActionListener(e -> loadData());
        btnTim.addActionListener(e -> timKhachHang());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (row >= 0) {
                    txtMaKH.setText(model.getValueAt(row, 0).toString());
                    txtTenKH.setText(model.getValueAt(row, 1).toString());
                    txtSDT.setText(model.getValueAt(row, 2).toString());
                }
            }
        });

        loadData();
    }

    private void loadData() {
        model.setRowCount(0); 
        ArrayList<KhachHang> ds = khDAO.getAllKhachHang();
        for (KhachHang kh : ds) {
            model.addRow(new Object[]{
                kh.getMaKH(),
                kh.getTenKH(),
                kh.getSoDienThoai()
            });
        }

        
        clearForm();
    }


    private void themKhachHang() {
        String ma = txtMaKH.getText().trim();
        String ten = txtTenKH.getText().trim();
        String sdt = txtSDT.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
            return;
        }

        KhachHang kh = new KhachHang(ma, ten, sdt);
        if (khDAO.addKhachHang(kh)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể thêm (trùng mã hoặc lỗi SQL).");
        }
    }

    private void suaKhachHang() {
        String ma = txtMaKH.getText().trim();
        String ten = txtTenKH.getText().trim();
        String sdt = txtSDT.getText().trim();

        if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đủ thông tin!");
            return;
        }

        KhachHang kh = new KhachHang(ma, ten, sdt);
        if (khDAO.updateKhachHang(kh)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
            loadData();
        } else {
            JOptionPane.showMessageDialog(this, "Không thể cập nhật (mã không tồn tại?).");
        }
    }

    private void xoaKhachHang() {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng cần xóa.");
            return;
        }

        String ma = model.getValueAt(row, 0).toString();
        if (JOptionPane.showConfirmDialog(this, "Xóa khách hàng " + ma + " ?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
            if (khDAO.removeKhachHang(ma)) {
                JOptionPane.showMessageDialog(this, "Đã xóa!");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Không thể xóa (lỗi SQL).");
            }
        }
    }

    private void timKhachHang() {
        String sdt = txtTimKiem.getText().trim();
        if (sdt.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập số điện thoại cần tìm!");
            return;
        }

        KhachHang kh = khDAO.findKhachHangBySDT(sdt);
        model.setRowCount(0);

        if (kh != null) {
            model.addRow(new Object[]{kh.getMaKH(), kh.getTenKH(), kh.getSoDienThoai()});
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng có SĐT: " + sdt);
        }
    }
    private void clearForm() {
        txtMaKH.setText("");
        txtTenKH.setText("");
        txtSDT.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
        txtMaKH.requestFocus();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new KhachHangGUI().setVisible(true));
    }
}
