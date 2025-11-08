

package dao;

import java.sql.*;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.ChucVu;

public class ChucVuDAO {

    // Lấy toàn bộ danh sách chức vụ
    public ArrayList<ChucVu> getAllChucVu() {
        ArrayList<ChucVu> list = new ArrayList<>();
        String sql = "SELECT * FROM ChucVu";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                ChucVu cv = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"),
                        rs.getString("moTa")
                );
                list.add(cv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Tìm chức vụ theo mã
    public ChucVu findChucVuByMa(String maChucVu) {
        String sql = "SELECT * FROM ChucVu WHERE maChucVu = ?";
        ChucVu cv = null;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maChucVu);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                cv = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"),
                        rs.getString("moTa")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cv;
    }

    // Kiểm tra chức vụ có tồn tại không
    public boolean isChucVuExists(String maChucVu) {
        String sql = "SELECT 1 FROM ChucVu WHERE maChucVu = ?";
        boolean exists = false;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maChucVu);
            ResultSet rs = stmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
}

