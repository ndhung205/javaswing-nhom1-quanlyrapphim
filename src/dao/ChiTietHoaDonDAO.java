package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.ChiTietHoaDon;
import entity.Ghe;
import entity.HoaDon;
import entity.LichChieu;
import entity.Phim;
import entity.Phong;
import entity.Ve;

public class ChiTietHoaDonDAO {

    // them
    public boolean addChiTietHoaDon(ChiTietHoaDon cthd) {
        int n = 0;
        String sql = "INSERT INTO ChiTietHoaDon (maHoaDon, maVe, donGia, soLuong) VALUES (?, ?, ?, ?)";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, cthd.getHoaDon().getMaHoaDon());
            stmt.setString(2, cthd.getVe().getMaVe());
            stmt.setDouble(3, cthd.getDonGia());
            stmt.setInt(4, 1);

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    // lay tat ca chi tiet hoa don
    public ArrayList<ChiTietHoaDon> getAllChiTietHoaDon() {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon";

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString("maHoaDon"));
                Ve ve = new Ve(rs.getString("maVe"));
                double donGia = rs.getDouble("donGia");

                ChiTietHoaDon cthd = new ChiTietHoaDon(hd, ve, donGia);
                list.add(cthd);
            }

            rs.close();
            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // lay danh sach  theo ma
    public ArrayList<ChiTietHoaDon> getChiTietHoaDonByHoaDon(String maHoaDon) {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        String sql = "SELECT * FROM ChiTietHoaDon WHERE maHoaDon = ?";

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHoaDon);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                HoaDon hd = new HoaDon(rs.getString("maHoaDon"));
                Ve ve = new Ve(rs.getString("maVe"));
                double donGia = rs.getDouble("donGia");

                ChiTietHoaDon cthd = new ChiTietHoaDon(hd, ve, donGia);
                list.add(cthd);
            }

            rs.close();
            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    // Cap nhat
    public boolean updateChiTietHoaDon(ChiTietHoaDon cthd) {
        int n = 0;
        String sql = "UPDATE ChiTietHoaDon SET donGia = ? WHERE maHoaDon = ? AND maVe = ?";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setDouble(1, cthd.getDonGia());
            stmt.setString(2, cthd.getHoaDon().getMaHoaDon());
            stmt.setString(3, cthd.getVe().getMaVe());

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }

    //Xoa
    public boolean deleteChiTietHoaDon(String maHoaDon, String maVe) {
        int n = 0;
        String sql = "DELETE FROM ChiTietHoaDon WHERE maHoaDon = ? AND maVe = ?";
        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            stmt.setString(1, maHoaDon);
            stmt.setString(2, maVe);

            n = stmt.executeUpdate();

            con.close();
            DatabaseConnection.getInstance().disconnect();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return n > 0;
    }
 // Tim chi tiet hoa don theo maHoaDon 
    public ArrayList<ChiTietHoaDon> findChiTietHoaDonByMa(String maHoaDon) {
    	String sql = """
    		    SELECT ct.*, v.*, lc.*, p.*
    		    FROM ChiTietHoaDon ct
    		    JOIN Ve v ON v.maVe = ct.maVe
    		    JOIN LichChieu lc ON lc.maLichChieu = v.maLichChieu
    		    JOIN Phim p ON p.maPhim = lc.maPhim
    		    WHERE ct.maHoaDon = ?
    		""";

        ArrayList<ChiTietHoaDon> cthd = new ArrayList<ChiTietHoaDon>();
        HoaDonDAO hdDAO = new HoaDonDAO();

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, maHoaDon);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	HoaDon hd = hdDAO.findHoaDonById(maHoaDon);
                Phim phim = new Phim(rs.getString("maPhim"),rs.getString("tenPhim"),null,
                        rs.getString("moTa"),rs.getInt("thoiLuongChieu"),rs.getInt("namPhatHanh"),rs.getString("poster")
                );
                LocalDate ngayChieu = rs.getDate("ngayChieu").toLocalDate();
                LocalDateTime gioBatDau = rs.getTimestamp("gioBatDau").toLocalDateTime();
                LocalDateTime gioKetThuc = rs.getTimestamp("gioKetThuc").toLocalDateTime();

                LichChieu lichChieu = new LichChieu(rs.getString("maLichChieu"),phim,
                        new Phong(rs.getString("maPhong")),ngayChieu,gioBatDau,gioKetThuc);
                Ghe ghe = new Ghe(rs.getString("maGhe"));

                Ve ve = new Ve(
                        rs.getString("maVe"),
                        rs.getDouble("gia"),
                        lichChieu,
                        ghe,
                        rs.getTimestamp("thoiGianDat").toLocalDateTime()
                );
                double donGia = rs.getDouble("donGia");

                ChiTietHoaDon ct = new ChiTietHoaDon(hd, ve, donGia);
                cthd.add(ct);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cthd;
    }

}
