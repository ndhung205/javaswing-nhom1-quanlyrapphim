package dao;

import java.sql.*;
import java.util.ArrayList;
import connectDB.DatabaseConnection;
import entity.KhachHang;

public class KhachHangDAO {

    // Lấy toàn bộ danh sách khách hàng
    public ArrayList<KhachHang> getAllKhachHang() {
        ArrayList<KhachHang> list = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                KhachHang kh = new KhachHang(
                        rs.getString("maKhachHang"),
                        rs.getString("ten"),
                        rs.getString("soDienThoai")
                );
                list.add(kh);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm khách hàng mới
    public boolean addKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (maKhachHang, ten, soDienThoai) VALUES (?, ?, ?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, kh.getMaKH());
            stmt.setString(2, kh.getTenKH());
            stmt.setString(3, kh.getSoDienThoai());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // Cập nhật thông tin khách hàng
    public boolean updateKhachHang(KhachHang kh) {
        String sql = "UPDATE KhachHang SET ten = ?, soDienThoai = ? WHERE maKhachHang = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, kh.getTenKH());
            stmt.setString(2, kh.getSoDienThoai());
            stmt.setString(3, kh.getMaKH());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // Xóa khách hàng theo mã
    public boolean removeKhachHang(String maKhachHang) {
        String sql = "DELETE FROM KhachHang WHERE maKhachHang = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maKhachHang);
            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }
    // tim kiem khach hang theo sdt
    public KhachHang findKhachHangBySDT(String sdt) {
        String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
        KhachHang kh = null;

        try {
        	Connection con = DatabaseConnection.getInstance().getConnection();
        	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, sdt);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                kh = new KhachHang(rs.getString("maKhachHang"),rs.getString("ten"),rs.getString("soDienThoai"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }

    // kiem tra su ton tai cua khach hang
    public boolean isKhachHangExists(KhachHang k) {
    	 String sql = "SELECT * FROM KhachHang WHERE soDienThoai = ?";
    	 KhachHang kh = null;
    	 try {
    		Connection con = DatabaseConnection.getInstance().getConnection();
         	PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, k.getSoDienThoai());
            ResultSet rs = stmt.executeQuery();
            if(rs.next()) {
            	kh = new KhachHang(rs.getString("maKhachHang"),rs.getString("ten"),rs.getString("soDienThoai"));
            }
            
		} catch (Exception e) {
			e.printStackTrace();
		}
    	 
    	return (kh !=null);
	}

}
