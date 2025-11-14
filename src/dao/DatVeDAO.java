package dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import connectDB.DatabaseConnection;
import entity.DatVe;
import entity.KhachHang;

public class DatVeDAO {

    public ArrayList<DatVe> getAllDatVe() {
        ArrayList<DatVe> list = new ArrayList<>();
        String sql = "SELECT * FROM DatVe dv JOIN KhachHang kh ON dv.maKhachHang=kh.maKhachHang";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) { 
                DatVe dv = new DatVe(rs.getString("maDatVe"), rs.getString("trangThai"), rs.getTimestamp("ngayDat").toLocalDateTime(),
                		new KhachHang(rs.getString("maKhachHang"), rs.getString("tenKhachHang"), rs.getString("soDienThoai")));
                list.add(dv);
            }
            System.out.println("K");
            con.close();
            DatabaseConnection.getInstance().disconnect();
           
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addDatVe(DatVe dv) {
        String sql = "INSERT INTO DatVe (maDatVe, maKhachHang,ngayDat, trangThai) VALUES (?, ?, ?,?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, dv.getMaDatVe());
            stmt.setString(2, dv.getKhachHang().getMaKH());
            stmt.setTimestamp(3, Timestamp.valueOf(dv.getNgayDat()));
            stmt.setString(4, dv.getTrangThai());
            

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    public boolean updateDatVe(DatVe dv) {
        String sql = "UPDATE DatVe SET trangThai = ? WHERE maDatVe = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, dv.getTrangThai());
            stmt.setString(2, dv.getMaDatVe());

            n = stmt.executeUpdate();
            
            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    public boolean removeDatVe(String maDatVe) {
        String sql = "DELETE FROM DatVe WHERE maDatVe = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maDatVe);
            n = stmt.executeUpdate();
            
            
            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }
}
