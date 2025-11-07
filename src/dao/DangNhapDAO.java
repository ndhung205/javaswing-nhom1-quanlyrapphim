package dao;

import java.sql.*;

import connectDB.DatabaseConnection;
import entity.TaiKhoan;

public class DangNhapDAO {

	// Phương thức đăng nhập - kiểm tra tên đăng nhập và mật khẩu
	public TaiKhoan dangNhap(String tenTK, String matKhau) {
		TaiKhoan tk = null;
		String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? AND matKhau = ? AND trangThai = 1";

		try (Connection con = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				) {
			stmt.setString(1, tenTK);
			stmt.setString(2, matKhau);

			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				String maTaiKhoan = rs.getString("maTaiKhoan");
				String maNhanVien = rs.getString("maNhanVien");
				String tenTaiKhoan = rs.getString("tenTaiKhoan");
				String matKhauTK = rs.getString("matKhau");
				String vaiTro = rs.getString("vaiTro");
				boolean trangThai = rs.getBoolean("trangThai");

				tk = new TaiKhoan(maTaiKhoan, maNhanVien, tenTaiKhoan, matKhauTK, vaiTro, trangThai);
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Lỗi đăng nhập SQL: " + e.getMessage());
		}

		return tk;
	}
}
