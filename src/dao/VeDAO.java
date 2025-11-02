package dao;

import java.sql.*;
import java.util.ArrayList;
import connectDB.DatabaseConnection;
import entity.Ve;

public class VeDAO {

    // Lấy tất cả vé trong CSDL
    public ArrayList<Ve> getAllVe() {
        ArrayList<Ve> list = new ArrayList<>();
        String sql = "SELECT * FROM Ve";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ve ve = new Ve(rs.getString("maVe"), rs.getDouble("gia"), null,null, rs.getTimestamp("thoiGianDat").toLocalDateTime());
                list.add(ve);
            }

            System.out.println("Lấy danh sách vé thành công (" + list.size() + " vé).");

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Thêm vé mới
    public boolean addVe(Ve ve) {
        String sql = "INSERT INTO Ve (maVe, gia, trangThai, thoiGianDat) VALUES (?, ?, ?, ?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ve.getMaVe());
            stmt.setDouble(2, ve.getGia());
            stmt.setString(3, null);
            stmt.setTimestamp(4, Timestamp.valueOf(ve.getThoiGianDat()));

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

   

    // Xóa vé theo mã
    public boolean removeVe(String maVe) {
        String sql = "DELETE FROM Ve WHERE maVe = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }
}
