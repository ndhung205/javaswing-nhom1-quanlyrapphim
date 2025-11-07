package dao;

import java.sql.*;
import connectDB.DatabaseConnection;
import entity.TaiKhoan;

public class TaiKhoanDAO {

    // 🔹 Kiểm tra đăng nhập
    public TaiKhoan dangNhap(String tenTK, String matKhau) {
        String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? AND matKhau = ? AND trangThai = 1";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tenTK);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TaiKhoan(
                        rs.getString("maTaiKhoan"),
                        rs.getString("maNhanVien"),
                        rs.getString("tenTaiKhoan"),
                        rs.getString("matKhau"),
                        rs.getString("vaiTro"),
                        rs.getBoolean("trangThai")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 🔹 Đăng ký tài khoản
    public boolean dangKy(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (maTaiKhoan, maNhanVien, tenTaiKhoan, matKhau, vaiTro, trangThai) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tk.getMaTK());
            stmt.setString(2, tk.getNhanVien());
            stmt.setString(3, tk.getTenTK());
            stmt.setString(4, tk.getMatKhau());
            stmt.setString(5, tk.getVaiTro());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 🔹 Quên mật khẩu (lấy mật khẩu)
    public String layMatKhau(String tenTK) {
        String sql = "SELECT matKhau FROM TaiKhoan WHERE tenTaiKhoan = ?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tenTK);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("matKhau");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
