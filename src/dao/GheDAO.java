package dao;

import java.sql.*;
import java.util.ArrayList;
import connectDB.DatabaseConnection;
import entity.Ghe;
import entity.LoaiGhe;

public class GheDAO {

    public ArrayList<Ghe> getAllGhe() {
        ArrayList<Ghe> list = new ArrayList<>();
        String sql = "SELECT * FROM Ghe LEFT JOIN LoaiGhe ON Ghe.maLoaiGhe = LoaiGhe.maLoaiGhe";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LoaiGhe lg = new LoaiGhe(
                        rs.getString("maLoaiGhe"),
                        rs.getString("tenLoaiGhe"),
                        rs.getDouble("phuThu"),
                        rs.getString("moTa")
                );

                Ghe ghe = new Ghe(
                        rs.getString("maGhe"),
                        null, // Phòng set sau
                        lg,
                        rs.getString("trangThai")
                );

                list.add(ghe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public boolean addGhe(Ghe ghe) {
        String sql = "INSERT INTO Ghe (maGhe, maPhong, maLoaiGhe, trangThai) VALUES (?, ?, ?, ?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ghe.getMaGhe());
            stmt.setString(2, ghe.getPhong().getMaPhong());
            stmt.setString(3, ghe.getLoaiGhe().getMaLoaiGhe());
            stmt.setString(4, ghe.getTrangThai());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    public boolean updateGhe(Ghe ghe) {
        String sql = "UPDATE Ghe SET trangThai = ? WHERE maGhe = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ghe.getTrangThai());
            stmt.setString(2, ghe.getMaGhe());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    public boolean removeGhe(String maGhe) {
        String sql = "DELETE FROM Ghe WHERE maGhe = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }
}
