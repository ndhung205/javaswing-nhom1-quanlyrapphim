//package dao;
//
//import java.sql.*;
//import connectDB.DatabaseConnection;
//import entity.TaiKhoan;
//
//public class TaiKhoanDAO {
//	 
//	
//	public TaiKhoan dangNhap(String tenTK, String matKhau) {
//		String sql = "SELECT * FROM TaiKhoan WHERE tenTaiKhoan = ? AND matKhau = ? AND trangThai = 1";
//		try (Connection con = DatabaseConnection.getInstance().getConnection();
//				PreparedStatement stmt = con.prepareStatement(sql)) {
//
//			stmt.setString(1, tenTK);
//			stmt.setString(2, matKhau);
//
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				return new TaiKhoan(rs.getString("maTaiKhoan"), nv, rs.getString("tenTaiKhoan"),
//						rs.getString("matKhau"), rs.getString("vaiTro"), rs.getBoolean("trangThai"));
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	
//	public boolean dangKy(TaiKhoan tk) {
//		String sql = "INSERT INTO TaiKhoan (maTaiKhoan, maNhanVien, tenTaiKhoan, matKhau, vaiTro, trangThai) VALUES (?, ?, ?, ?, ?, 1)";
//		try (Connection con = DatabaseConnection.getInstance().getConnection();
//				PreparedStatement stmt = con.prepareStatement(sql)) {
//
//			stmt.setString(1, tk.getMaTK());
//			stmt.setString(2, tk.getNhanVien());
//			stmt.setString(3, tk.getTenTK());
//			stmt.setString(4, tk.getMatKhau());
//			stmt.setString(5, tk.getVaiTro());
//
//			return stmt.executeUpdate() > 0;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// 🔹 Cập nhật tài khoản
//	public boolean capNhatTaiKhoan(TaiKhoan tk) {
//		String sql = "UPDATE TaiKhoan SET maNhanVien = ?, tenTaiKhoan = ?, matKhau = ?, vaiTro = ?, trangThai = ? WHERE maTaiKhoan = ?";
//		try (Connection con = DatabaseConnection.getInstance().getConnection();
//				PreparedStatement stmt = con.prepareStatement(sql)) {
//
//			stmt.setString(1, tk.getNhanVien());
//			stmt.setString(2, tk.getTenTK());
//			stmt.setString(3, tk.getMatKhau());
//			stmt.setString(4, tk.getVaiTro());
//			stmt.setBoolean(5, tk.isTrangThai());
//			stmt.setString(6, tk.getMaTK());
//
//			return stmt.executeUpdate() > 0;
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	// 🔹 Quên mật khẩu (lấy mật khẩu)
//	public String layMatKhau(String tenTK) {
//		String sql = "SELECT matKhau FROM TaiKhoan WHERE tenTaiKhoan = ?";
//		try (Connection con = DatabaseConnection.getInstance().getConnection();
//				PreparedStatement stmt = con.prepareStatement(sql)) {
//
//			stmt.setString(1, tenTK);
//			ResultSet rs = stmt.executeQuery();
//			if (rs.next()) {
//				return rs.getString("matKhau");
//			}
//
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//}
package dao;

import java.sql.*;
import connectDB.DatabaseConnection;
import entity.TaiKhoan;
import entity.ChucVu;
import entity.NhanVien;

public class TaiKhoanDAO {
	
    private NhanVienDAO nhanVienDAO = new NhanVienDAO();

    
    public TaiKhoan dangNhap(String tenTK, String matKhau) {
        String sql =
        		"""
        		SELECT tk.maTaiKhoan, tk.tenTaiKhoan, tk.matKhau, tk.vaiTro, tk.trangThai,
				       nv.maNhanVien, nv.ten, nv.soDienThoai, nv.email, nv.ngayVaoLam,
				       cv.maChucVu, cv.tenChucVu, cv.moTa
				FROM TaiKhoan tk
				JOIN NhanVien nv ON nv.maNhanVien = tk.maNhanVien
				JOIN ChucVu cv ON cv.maChucVu = nv.maChucVu
        		WHERE tk.tenTaiKhoan = ? AND matKhau = ? AND trangThai = 1
        		""";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tenTK);
            stmt.setString(2, matKhau);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
            	
            	ChucVu chucVu = new ChucVu(rs.getString("maChucVu"), rs.getString("tenChucVu"), rs.getString("moTa"));
            	
                NhanVien nv = new NhanVien(rs.getString("maNhanVien"), rs.getString("ten"), rs.getString("soDienThoai"), rs.getString("email"), chucVu, rs.getDate("ngayVaoLam").toLocalDate());
                
                return new TaiKhoan(
                        rs.getString("maTaiKhoan"),
                        nv, 
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

    public boolean dangKy(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (maTaiKhoan, maNhanVien, tenTaiKhoan, matKhau, vaiTro, trangThai) VALUES (?, ?, ?, ?, ?, 1)";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tk.getMaTK());
            stmt.setString(2, tk.getNhanVien().getMaNV()); 
            stmt.setString(3, tk.getTenTK());
            stmt.setString(4, tk.getMatKhau());
            stmt.setString(5, tk.getVaiTro());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean capNhatTaiKhoan(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET maNhanVien = ?, tenTaiKhoan = ?, matKhau = ?, vaiTro = ?, trangThai = ? WHERE maTaiKhoan = ?";
        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, tk.getNhanVien().getMaNV());
            stmt.setString(2, tk.getTenTK());
            stmt.setString(3, tk.getMatKhau());
            stmt.setString(4, tk.getVaiTro());
            stmt.setBoolean(5, tk.isTrangThai());
            stmt.setString(6, tk.getMaTK());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

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
