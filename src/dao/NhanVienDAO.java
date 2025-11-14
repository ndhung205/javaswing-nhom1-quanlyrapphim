package dao;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.ChucVu;
import entity.NhanVien;

public class NhanVienDAO {

    // =============================
    // Lấy toàn bộ danh sách nhân viên
    // =============================
    public ArrayList<NhanVien> getAllNhanVien() {
        ArrayList<NhanVien> list = new ArrayList<>();

        String sql = "SELECT nv.maNhanVien, nv.ten, nv.soDienThoai, nv.email, " +
                     "nv.ngayVaoLam, cv.maChucVu, cv.tenChucVu, cv.moTa " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu";

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("ngayVaoLam");
                LocalDate ngay = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                ChucVu chucVu = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"),
                        rs.getString("moTa")
                );

                NhanVien nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("ten"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        chucVu,
                        ngay
                );
                list.add(nv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // =============================
    // Thêm nhân viên mới
    // =============================
    public boolean addNhanVien(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNhanVien, ten, soDienThoai, email, maChucVu, ngayVaoLam) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nv.getMaNV());
            stmt.setString(2, nv.getHoTen());
            stmt.setString(3, nv.getsDT());
            stmt.setString(4, nv.getEmail());
            stmt.setString(5, nv.getChucVu() != null ? nv.getChucVu().getMaChucVu() : null);
            stmt.setDate(6, nv.getNgayVaoLam() != null ? Date.valueOf(nv.getNgayVaoLam()) : null);

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // =============================
    // Cập nhật nhân viên
    // =============================
    public boolean updateNhanVien(NhanVien nv) {
        String sql = "UPDATE NhanVien SET ten = ?, soDienThoai = ?, email = ?, maChucVu = ?, ngayVaoLam = ? " +
                     "WHERE maNhanVien = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, nv.getHoTen());
            stmt.setString(2, nv.getsDT());
            stmt.setString(3, nv.getEmail());
            stmt.setString(4, nv.getChucVu() != null ? nv.getChucVu().getMaChucVu() : null);
            stmt.setDate(5, nv.getNgayVaoLam() != null ? Date.valueOf(nv.getNgayVaoLam()) : null);
            stmt.setString(6, nv.getMaNV());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // =============================
    // Xóa nhân viên
    // =============================
    public boolean removeNhanVien(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE maNhanVien = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maNV);
            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // =============================
    // Tìm nhân viên theo số điện thoại (JOIN để có chức vụ)
    // =============================
    public NhanVien findNhanVienBySDT(String sDT) {
        String sql = "SELECT nv.maNhanVien, nv.ten, nv.soDienThoai, nv.email, nv.ngayVaoLam, " +
                     "cv.maChucVu, cv.tenChucVu, cv.moTa " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu " +
                     "WHERE nv.soDienThoai = ?";
        NhanVien nv = null;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, sDT);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date sqlDate = rs.getDate("ngayVaoLam");
                LocalDate ngay = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                ChucVu chucVu = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"),
                        rs.getString("moTa")
                );

                nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("ten"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        chucVu,
                        ngay
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nv;
    }
    // tim bang id
    public NhanVien findNhanVienById(String id) {
        String sql = "SELECT nv.maNhanVien, nv.ten, nv.soDienThoai, nv.email, nv.ngayVaoLam, " +
                     "cv.maChucVu, cv.tenChucVu, cv.moTa " +
                     "FROM NhanVien nv " +
                     "LEFT JOIN ChucVu cv ON nv.maChucVu = cv.maChucVu " +
                     "WHERE nv.maNhanVien = ?";
        NhanVien nv = null;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Date sqlDate = rs.getDate("ngayVaoLam");
                LocalDate ngay = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                ChucVu chucVu = new ChucVu(
                        rs.getString("maChucVu"),
                        rs.getString("tenChucVu"),
                        rs.getString("moTa")
                );

                nv = new NhanVien(
                        rs.getString("maNhanVien"),
                        rs.getString("ten"),
                        rs.getString("soDienThoai"),
                        rs.getString("email"),
                        chucVu,
                        ngay
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nv;
    }

    // =============================
    // Kiểm tra nhân viên có tồn tại không
    // =============================
    public boolean isNhanVienExists(String maNV) {
        String sql = "SELECT 1 FROM NhanVien WHERE maNhanVien = ?";
        boolean exists = false;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maNV);
            ResultSet rs = stmt.executeQuery();
            exists = rs.next();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exists;
    }
}
