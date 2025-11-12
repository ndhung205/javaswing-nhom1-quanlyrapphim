package dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.*;

public class HoaDonDAO {

    // them
    public boolean addHoaDon(HoaDon hd) {
        int n = 0;
        String sql = "INSERT INTO HoaDon (maHoaDon, maKhachHang, maNhanVien, maDatVe, ngayLapHoaDon, maThue, maKhuyenMai, maPhuongThuc, ngayThanhToan, tinhTrang) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, hd.getMaHoaDon());
            stmt.setString(2, hd.getKhachHang().getMaKH());
            stmt.setString(3, hd.getNhanVien().getMaNV());
            stmt.setString(4, hd.getDatVe().getMaDatVe());
            stmt.setTimestamp(5, Timestamp.valueOf(hd.getNgayLapHoaDon()));
            stmt.setString(6, hd.getThue().getMaThue());
            stmt.setString(7, hd.getKhuyenMai().getMaKM());
            stmt.setString(8, hd.getPtThanhToan().getMaPhuongThuc());
            stmt.setTimestamp(9, Timestamp.valueOf(hd.getNgayThanhToan()));
            stmt.setString(10, hd.getTinhTrang());

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // lay tat ca hoa don
    public ArrayList<HoaDon> getAllHoaDon() {
        ArrayList<HoaDon> list = new ArrayList<>();
        String sql = "SELECT hd.*, kh.maKhachHang, kh.ten as tenKH, nv.* FROM HoaDon hd JOIN KhachHang kh ON kh.maKhachHang=hd.maKhachHang JOIN NhanVien nv ON nv.maNhanVien=hd.maNhanVien";

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon(
                    rs.getString("maHoaDon"),
                    new KhachHang(rs.getString("maKhachHang"), rs.getString("tenKH")),
                    new NhanVien(rs.getString("maNhanVien"), rs.getString("ten")),
                    new DatVe(rs.getString("maDatVe")),
                    rs.getTimestamp("ngayLapHoaDon")!= null ? rs.getTimestamp("ngayLapHoaDon").toLocalDateTime() : LocalDateTime.now(),
                    new Thue(rs.getString("maThue")),
                    new KhuyenMai(rs.getString("maKhuyenMai")),
                    new PhuongThucThanhToan(rs.getString("maPhuongThuc")),
                    rs.getTimestamp("ngayThanhToan")!= null ? rs.getTimestamp("ngayThanhToan").toLocalDateTime() : LocalDateTime.now(),
                    rs.getString("tinhTrang")
                );
                list.add(hd);
            }

            rs.close();
            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // tim theo ma
    public HoaDon findHoaDonById(String maHoaDon) {
        HoaDon hd = null;
        String sql = """
        		SELECT hd.*, kh.maKhachHang, kh.ten as tenKH, nv.*, t.*,km.*
        		FROM HoaDon hd 
        		JOIN KhachHang kh ON kh.maKhachHang = hd.maKhachHang 
        		JOIN NhanVien nv ON nv.maNhanVien = hd.maNhanVien 
				LEFT JOIN Thue t ON t.maThue = hd.maThue
				LEFT JOIN KhuyenMai km ON km.maKhuyenMai=hd.maKhuyenMai
        		WHERE maHoaDon = ?
        		""";


        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
            	hd = new HoaDon(
                        rs.getString("maHoaDon"),
                        new KhachHang(rs.getString("maKhachHang"), rs.getString("tenKH")),
                        new NhanVien(rs.getString("maNhanVien"), rs.getString("ten")),
                        new DatVe(rs.getString("maDatVe")),
                        rs.getTimestamp("ngayLapHoaDon")!= null ? rs.getTimestamp("ngayLapHoaDon").toLocalDateTime() : LocalDateTime.now(),
                        new Thue(rs.getString("maThue"), rs.getString("tenThue"), rs.getFloat("phanTram"),rs.getString("moTa")),
                        new KhuyenMai(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"), rs.getDouble("phanTramGiam"), 
        						rs.getDouble("soTienGiam"), rs.getDate("ngayBatDau"), rs.getDate("ngayKetThuc"), rs.getString("dieuKien"), rs.getBoolean("trangThai")),
                        new PhuongThucThanhToan(rs.getString("maPhuongThuc")),
                        rs.getTimestamp("ngayThanhToan")!= null ? rs.getTimestamp("ngayThanhToan").toLocalDateTime() : LocalDateTime.now(),
                        rs.getString("tinhTrang")
                    );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hd;
    }

    // cap nhat
    public boolean updateHoaDon(HoaDon hd) {
        int n = 0;
        String sql = "UPDATE HoaDon SET maKhachHang=?, maNhanVien=?, maDatVe=?, ngayLapHoaDon=?, maThue=?, maKhuyenMai=?, maPhuongThucThanhToan=?, ngayThanhToan=?, tinhTrang=? "
                   + "WHERE maHoaDon=?";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, hd.getKhachHang().getMaKH());
            stmt.setString(2, hd.getNhanVien().getMaNV());
            stmt.setString(3, hd.getDatVe().getMaDatVe());
            stmt.setTimestamp(4, Timestamp.valueOf(hd.getNgayLapHoaDon()));
            stmt.setString(5, hd.getThue().getMaThue());
            stmt.setString(6, hd.getKhuyenMai().getMaKM());
            stmt.setString(7, hd.getPtThanhToan().getMaPhuongThuc());
            stmt.setTimestamp(8, Timestamp.valueOf(hd.getNgayThanhToan()));
            stmt.setString(9, hd.getTinhTrang());
            stmt.setString(10, hd.getMaHoaDon());

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    // xoa
    public boolean deleteHoaDon(String maHoaDon) {
        int n = 0;
        String sql = "DELETE FROM HoaDon WHERE maHoaDon = ?";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHoaDon);

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

}
