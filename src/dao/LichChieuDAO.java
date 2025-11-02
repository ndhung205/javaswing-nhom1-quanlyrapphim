package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.LichChieu;
import entity.Phim;
import entity.Phong;

public class LichChieuDAO {

    // Danh sách lưu tạm dữ liệu lịch chiếu
    private ArrayList<LichChieu> listLichChieu = new ArrayList<>();

    /**
     * Lấy toàn bộ danh sách lịch chiếu từ database
     */
    public ArrayList<LichChieu> getAllLichChieu() {
        ArrayList<LichChieu> list = new ArrayList<>();

        String sql = """
            SELECT lc.maLichChieu, lc.ngayChieu, lc.gioBatDau, lc.gioKetThuc,
                   p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh,
                   ph.maPhong, ph.tenPhong, ph.soLuongGhe, ph.trangThai
            FROM LichChieu lc
            JOIN Phim p ON p.maPhim = lc.maPhim
            JOIN Phong ph ON ph.maPhong = lc.maPhong
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Phim phim = new Phim(
                    rs.getString("maPhim"),
                    rs.getString("tenPhim"),
                    null,
                    rs.getString("moTa"),
                    rs.getInt("thoiLuongChieu"),
                    rs.getInt("namPhatHanh")
                );

                Phong phong = new Phong(
                    rs.getString("maPhong"),
                    rs.getString("tenPhong"),
                    rs.getInt("soLuongGhe"),
                    null,
                    rs.getBoolean("trangThai")
                );

                LocalDate ngayChieu = rs.getDate("ngayChieu").toLocalDate();
                LocalDateTime gioBatDau = rs.getTimestamp("gioBatDau").toLocalDateTime();
                LocalDateTime gioKetThuc = rs.getTimestamp("gioKetThuc").toLocalDateTime();

                LichChieu lichChieu = new LichChieu(
                    rs.getString("maLichChieu"),
                    phim,
                    phong,
                    ngayChieu,
                    gioBatDau,
                    gioKetThuc
                );

                list.add(lichChieu);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        this.listLichChieu = list; // cập nhật danh sách hiện tại
        return list;
    }

    /**
     * Thêm lịch chiếu mới vào database
     */
    public boolean addLichChieu(LichChieu lc) {
        String sql = "INSERT INTO LichChieu (maLichChieu, maPhim, maPhong, ngayChieu, gioBatDau, gioKetThuc) VALUES (?, ?, ?, ?, ?, ?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, lc.getMaLichChieu());
            stmt.setString(2, lc.getPhim().getMaPhim());
            stmt.setString(3, lc.getPhong().getMaPhong());
            stmt.setDate(4, Date.valueOf(lc.getNgayChieu()));
            stmt.setTimestamp(5, Timestamp.valueOf(lc.getGioBatDau()));
            stmt.setTimestamp(6, Timestamp.valueOf(lc.getGioKetThuc()));

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    /**
     * Cập nhật thông tin lịch chiếu
     */
    public boolean updateLichChieu(LichChieu lc) {
        String sql = "UPDATE LichChieu SET maPhim = ?, maPhong = ?, ngayChieu = ?, gioBatDau = ?, gioKetThuc = ? WHERE maLichChieu = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, lc.getPhim().getMaPhim());
            stmt.setString(2, lc.getPhong().getMaPhong());
            stmt.setDate(3, Date.valueOf(lc.getNgayChieu()));
            stmt.setTimestamp(4, Timestamp.valueOf(lc.getGioBatDau()));
            stmt.setTimestamp(5, Timestamp.valueOf(lc.getGioKetThuc()));
            stmt.setString(6, lc.getMaLichChieu());

            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    /**
     * Xóa lịch chiếu theo mã
     */
    public boolean removeLichChieu(String maLichChieu) {
        String sql = "DELETE FROM LichChieu WHERE maLichChieu = ?";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maLichChieu);
            n = stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return n > 0;
    }

    /**
     * Tìm lịch chiếu theo chỉ số trong danh sách tạm
     */
    public LichChieu findLichChieuByIndex(int index) {
        if (index < 0 || index >= listLichChieu.size())
            return null;
        return listLichChieu.get(index);
    }

    /**
     * Lấy số lượng lịch chiếu hiện tại
     */
    public int getSize() {
        return listLichChieu.size();
    }
}
