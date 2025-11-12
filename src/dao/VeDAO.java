package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import connectDB.DatabaseConnection;
import entity.Ghe;
import entity.LichChieu;
import entity.LoaiGhe;
import entity.Phim;
import entity.Phong;
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
        String sql = "INSERT INTO Ve (maVe, gia, maLichChieu,maGhe, maDatVe,trangThai, thoiGianDat) VALUES (?, ?, ?, ?, ?,?,?)";
        int n = 0;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ve.getMaVe());
            stmt.setDouble(2, ve.getGia());
            stmt.setString(3, ve.getLichChieu().getMaLichChieu());
            stmt.setString(4, ve.getGhe().getMaGhe());
            stmt.setString(5, ve.getDatVe().getMaDatVe());
            stmt.setString(6, "Đã đặt");
            stmt.setTimestamp(7, Timestamp.valueOf(ve.getThoiGianDat()));

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
    // tim danh sach Ve theo 1 ngay nao do
    public ArrayList<Ve> findVeByNgay(java.util.Date ngay) {
        ArrayList<Ve> list = new ArrayList<>();
        String sql = """
            SELECT v.*, Ghe.*, p.*, lc.*
            FROM Ve v
            JOIN Ghe ON Ghe.maGhe = v.maGhe
            JOIN LichChieu lc ON lc.maLichChieu = v.maLichChieu
            JOIN Phim p ON p.maPhim = lc.maPhim
            WHERE CAST(thoiGianDat AS DATE) = ?
        """;

        try {
            Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);

            // Chuyển đổi java.util.Date sang java.sql.Date
            java.sql.Date sqlDate = new java.sql.Date(ngay.getTime());
            stmt.setDate(1, sqlDate);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Phim phim = new Phim(
                            rs.getString("maPhim"),
                            rs.getString("tenPhim"),
                            null,
                            rs.getString("moTa"),
                            rs.getInt("thoiLuongChieu"),
                            rs.getInt("namPhatHanh"),
                            rs.getString("poster")
                    );
                    LocalDate ngayChieu = rs.getDate("ngayChieu").toLocalDate();
                    LocalDateTime gioBatDau = rs.getTimestamp("gioBatDau").toLocalDateTime();
                    LocalDateTime gioKetThuc = rs.getTimestamp("gioKetThuc").toLocalDateTime();

                    LichChieu lichChieu = new LichChieu(
                            rs.getString("maLichChieu"),
                            phim,
                            new Phong(rs.getString("maPhong")),
                            ngayChieu,
                            gioBatDau,
                            gioKetThuc
                    );
                    Ghe ghe = new Ghe(
                            rs.getString("maGhe"),
                            new Phong(rs.getString("maPhong")),
                            new LoaiGhe(rs.getString("maLoaiGhe")),
                            rs.getString("trangThai")
                    );

                    Ve ve = new Ve(
                            rs.getString("maVe"),
                            rs.getDouble("gia"),
                            lichChieu,
                            ghe,
                            rs.getTimestamp("thoiGianDat").toLocalDateTime()
                    );
                    list.add(ve);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public Ve findVeById(String maVe) {
        Ve ve = null;
        String sql = """
            SELECT v.*, g.*, lg.*, p.*, lc.*
            FROM Ve v
            JOIN Ghe g ON g.maGhe = v.maGhe
            JOIN LoaiGhe lg ON lg.maLoaiGhe = g.maLoaiGhe
            JOIN LichChieu lc ON lc.maLichChieu = v.maLichChieu
            JOIN Phim p ON p.maPhim = lc.maPhim
            WHERE v.maVe = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maVe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Phim phim = new Phim(
                            rs.getString("maPhim"),
                            rs.getString("tenPhim"),
                            null,
                            rs.getString("moTa"),
                            rs.getInt("thoiLuongChieu"),
                            rs.getInt("namPhatHanh"),
                            rs.getString("poster")
                    );

                    LocalDate ngayChieu = rs.getDate("ngayChieu").toLocalDate();
                    LocalDateTime gioBatDau = rs.getTimestamp("gioBatDau").toLocalDateTime();
                    LocalDateTime gioKetThuc = rs.getTimestamp("gioKetThuc").toLocalDateTime();
                    LichChieu lichChieu = new LichChieu(
                            rs.getString("maLichChieu"),
                            phim,
                            new Phong(rs.getString("maPhong")),
                            ngayChieu,
                            gioBatDau,
                            gioKetThuc
                    );

                    LoaiGhe loaiGhe = new LoaiGhe(
                            rs.getString("maLoaiGhe"),
                            rs.getString("tenLoaiGhe"),
                            rs.getDouble("phuThu"),
                            rs.getString("moTa")
                    );

                    Ghe ghe = new Ghe(
                            rs.getString("maGhe"),
                            new Phong(rs.getString("maPhong")),
                            loaiGhe,
                            rs.getString("trangThai")
                    );

                    ve = new Ve(
                            rs.getString("maVe"),
                            rs.getDouble("gia"),
                            lichChieu,
                            ghe,
                            rs.getTimestamp("thoiGianDat").toLocalDateTime()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ve;
    }


}
