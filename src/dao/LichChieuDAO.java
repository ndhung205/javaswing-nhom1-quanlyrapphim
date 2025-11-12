package dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LichChieu;
import entity.Phim;
import entity.Phong;

public class LichChieuDAO {
	
	private PhimDAO phimDAO = new PhimDAO();
    private PhongDAO phongDAO = new PhongDAO();

    // Danh sách lưu tạm dữ liệu lịch chiếu
    private ArrayList<LichChieu> listLichChieu = new ArrayList<>();

    /**
     * Lấy toàn bộ danh sách lịch chiếu từ database
     */
    public ArrayList<LichChieu> getAllLichChieu() {
        ArrayList<LichChieu> list = new ArrayList<>();

        String sql = """
            SELECT lc.maLichChieu, lc.ngayChieu, lc.gioBatDau, lc.gioKetThuc,
                   p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster, 
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
                    rs.getInt("namPhatHanh"),
                    rs.getString("poster")
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
     * Thêm lịch chiếu mới
     * @param lichChieu
     * @return true nếu thành công
     */
    public boolean insert(LichChieu lichChieu) {
        // Kiểm tra trùng lịch trước khi thêm
        if (checkTrungLich(lichChieu, null)) {
            System.err.println("❌ Lịch chiếu bị trùng với lịch khác!");
            return false;
        }
        
        String sql = "INSERT INTO LichChieu (maLichChieu, maPhim, maPhong, ngayChieu, gioBatDau, gioKetThuc) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lichChieu.getMaLichChieu());
            stmt.setString(2, lichChieu.getPhim().getMaPhim());
            stmt.setString(3, lichChieu.getPhong().getMaPhong());
            stmt.setDate(4, Date.valueOf(lichChieu.getNgayChieu()));
            stmt.setTimestamp(5, Timestamp.valueOf(lichChieu.getGioBatDau()));
            stmt.setTimestamp(6, Timestamp.valueOf(lichChieu.getGioKetThuc()));
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Thêm lịch chiếu thành công: " + lichChieu.getMaLichChieu());
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm lịch chiếu: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Kiểm tra trùng lịch chiếu trong cùng phòng
     * @param lichChieu Lịch chiếu cần kiểm tra
     * @param excludeMaLichChieu Mã lịch chiếu bỏ qua (dùng khi update)
     * @return true nếu bị trùng
     */
    public boolean checkTrungLich(LichChieu lichChieu, String excludeMaLichChieu) {
        String sql = "SELECT COUNT(*) FROM LichChieu " +
                     "WHERE maPhong = ? AND ngayChieu = ? " +
                     "AND maLichChieu != ? " +
                     "AND (" +
                     "  (gioBatDau < ? AND gioKetThuc > ?) OR " + // Lịch cũ bao lịch mới
                     "  (gioBatDau < ? AND gioKetThuc > ?) OR " + // Lịch mới bao lịch cũ
                     "  (gioBatDau >= ? AND gioBatDau < ?) OR " + // Bắt đầu trong khoảng
                     "  (gioKetThuc > ? AND gioKetThuc <= ?)" +   // Kết thúc trong khoảng
                     ")";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String excludeId = (excludeMaLichChieu != null) ? excludeMaLichChieu : "";
            Timestamp gioBatDau = Timestamp.valueOf(lichChieu.getGioBatDau());
            Timestamp gioKetThuc = Timestamp.valueOf(lichChieu.getGioKetThuc());
            
            stmt.setString(1, lichChieu.getPhong().getMaPhong());
            stmt.setDate(2, Date.valueOf(lichChieu.getNgayChieu()));
            stmt.setString(3, excludeId);
            stmt.setTimestamp(4, gioKetThuc);
            stmt.setTimestamp(5, gioBatDau);
            stmt.setTimestamp(6, gioKetThuc);
            stmt.setTimestamp(7, gioBatDau);
            stmt.setTimestamp(8, gioBatDau);
            stmt.setTimestamp(9, gioKetThuc);
            stmt.setTimestamp(10, gioBatDau);
            stmt.setTimestamp(11, gioKetThuc);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
    
    /**
     * Kiểm tra mã lịch chiếu đã tồn tại chưa
     * @param maLichChieu
     * @return true nếu đã tồn tại
     */
    public boolean isMaLichChieuExists(String maLichChieu) {
        String sql = "SELECT COUNT(*) FROM LichChieu WHERE maLichChieu = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }

    /**
     * Cập nhật lịch chiếu
     * @param lichChieu
     * @return true nếu thành công
     */
    public boolean update(LichChieu lichChieu) {
        // Kiểm tra trùng lịch (trừ chính nó)
        if (checkTrungLich(lichChieu, lichChieu.getMaLichChieu())) {
            System.err.println("❌ Lịch chiếu bị trùng với lịch khác!");
            return false;
        }
        
        String sql = "UPDATE LichChieu SET maPhim = ?, maPhong = ?, ngayChieu = ?, " +
                     "gioBatDau = ?, gioKetThuc = ? WHERE maLichChieu = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, lichChieu.getPhim().getMaPhim());
            stmt.setString(2, lichChieu.getPhong().getMaPhong());
            stmt.setDate(3, Date.valueOf(lichChieu.getNgayChieu()));
            stmt.setTimestamp(4, Timestamp.valueOf(lichChieu.getGioBatDau()));
            stmt.setTimestamp(5, Timestamp.valueOf(lichChieu.getGioKetThuc()));
            stmt.setString(6, lichChieu.getMaLichChieu());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Cập nhật lịch chiếu thành công: " + lichChieu.getMaLichChieu());
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật lịch chiếu: " + e.getMessage());
        }
        
        return false;
    }

    /**
     * Xóa lịch chiếu
     * @param maLichChieu
     * @return true nếu thành công
     */
    public boolean delete(String maLichChieu) {
        String sql = "DELETE FROM LichChieu WHERE maLichChieu = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maLichChieu);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Xóa lịch chiếu thành công: " + maLichChieu);
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa lịch chiếu: " + e.getMessage());
            
            if (e.getMessage().contains("REFERENCE constraint")) {
                System.err.println("⚠️ Không thể xóa lịch chiếu vì đã có vé!");
            }
        }
        
        return false;
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
    
    private LichChieu createLichChieuFromResultSet(ResultSet rs) throws SQLException {

        Phim phim = new Phim(
            rs.getString("maPhim"),
            rs.getString("tenPhim"),
            null,
            rs.getString("moTa"),
            rs.getInt("thoiLuongChieu"),
            rs.getInt("namPhatHanh"),
            rs.getString("poster")
        );

        Phong phong = new Phong(
            rs.getString("maPhong"),
            rs.getString("tenPhong"),
            rs.getInt("soLuongGhe"),
            null,
            rs.getBoolean("trangThai")
        );

        return new LichChieu(
            rs.getString("maLichChieu"),
            phim,
            phong,
            rs.getDate("ngayChieu").toLocalDate(),
            rs.getTimestamp("gioBatDau").toLocalDateTime(),
            rs.getTimestamp("gioKetThuc").toLocalDateTime()
        );
    }

    
    /**
     * Lấy lịch chiếu theo mã
     * @param maLichChieu
     * @return LichChieu hoặc null
     */
    public LichChieu getById(String maLichChieu) {
        String sql = """
        		SELECT lc.*, 
				       p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster,
				       ph.tenPhong, ph.soLuongGhe, ph.trangThai
				FROM LichChieu lc
				JOIN Phim p ON p.maPhim = lc.maPhim
				JOIN Phong ph ON ph.maPhong = lc.maPhong
				WHERE lc.maLichChieu = ?
        		""";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maLichChieu);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return createLichChieuFromResultSet(rs);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy lịch chiếu: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Lấy lịch chiếu theo ngày
     * @param ngay
     * @return List<LichChieu>
     */
    public List<LichChieu> getByNgay(LocalDate ngay) {
        List<LichChieu> list = new ArrayList<>();

        String sql = """
            SELECT lc.*, 
                   p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster,
                   ph.tenPhong, ph.soLuongGhe, ph.trangThai
            FROM LichChieu lc
            JOIN Phim p ON lc.maPhim = p.maPhim
            JOIN Phong ph ON lc.maPhong = ph.maPhong
            WHERE lc.ngayChieu = ?
            ORDER BY lc.gioBatDau
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setDate(1, Date.valueOf(ngay));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LichChieu lc = createLichChieuFromResultSet(rs);
                if (lc != null) list.add(lc);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy lịch chiếu theo ngày: " + e.getMessage());
        }

        return list;
    }

    
    /**
     * Lấy lịch chiếu theo phim
     * @param maPhim
     * @return List<LichChieu>
     */
    public List<LichChieu> getByPhim(String maPhim) {
        List<LichChieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LichChieu WHERE maPhim = ? ORDER BY ngayChieu DESC, gioBatDau DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                LichChieu lc = createLichChieuFromResultSet(rs);
                if (lc != null) list.add(lc);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Lấy lịch chiếu theo phòng
     * @param maPhong
     * @return List<LichChieu>
     */
    public List<LichChieu> getByPhong(String maPhong) {
        List<LichChieu> list = new ArrayList<>();
        String sql = "SELECT * FROM LichChieu WHERE maPhong = ? ORDER BY ngayChieu DESC, gioBatDau DESC";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhong);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                LichChieu lc = createLichChieuFromResultSet(rs);
                if (lc != null) list.add(lc);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
    
    /**
     * Tìm lịch chiếu theo phòng và giờ bắt đầu (String giờBatDau từ ComboBox)
     * @param maPhong
     * @param gioBatDauStr - ví dụ: "2025-11-07T19:30" hoặc "19:30"
     * @return LichChieu hoặc null nếu không tìm thấy
     */
    public LichChieu findByPhongAndTime(String maPhong, String gioBatDauStr) {
        String sql = """
            SELECT lc.*, 
                   p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster,
                   ph.tenPhong, ph.soLuongGhe, ph.trangThai
            FROM LichChieu lc
            JOIN Phim p ON lc.maPhim = p.maPhim
            JOIN Phong ph ON lc.maPhong = ph.maPhong
            WHERE lc.maPhong = ?
              AND FORMAT(lc.gioBatDau, 'yyyy-MM-dd HH:mm') = ?
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // ===== XỬ LÝ CHUỖI GIOBATDAU =====
            // Case 1: chuỗi chỉ có HH:mm → tự ghép ngày chiếu
            if (gioBatDauStr.length() == 5) {  // "19:30"
                // Lấy ngày gần nhất của lịch chiếu theo phòng
                LichChieu lcSample = getSampleByPhong(maPhong);
                if (lcSample == null) return null;

                String ngay = lcSample.getNgayChieu().toString();  // yyyy-MM-dd
                gioBatDauStr = ngay + " " + gioBatDauStr;          // yyyy-MM-dd HH:mm
            }

            // Case 2: nếu người dùng nhập kiểu 2025-11-07T19:30 → đổi thành đúng format
            gioBatDauStr = gioBatDauStr.replace("T", " ");

            stmt.setString(1, maPhong);
            stmt.setString(2, gioBatDauStr);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createLichChieuFromResultSet(rs);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tìm lịch chiếu theo phòng và giờ: " + e.getMessage());
        }

        return null;
    }

    /** 
     * Lấy 1 lịch chiếu bất kỳ trong phòng, dùng để biết ngàyChiếu thực tế.
     */
    private LichChieu getSampleByPhong(String maPhong) {
        List<LichChieu> list = getByPhong(maPhong);
        return list.isEmpty() ? null : list.get(0);
    }
    
    public static void main(String[] args) {
		LichChieuDAO lichChieuDAO = new LichChieuDAO();
		
		System.out.println(lichChieuDAO.getByNgay(LocalDate.now()));
	}
    public List<LichChieu> getLichChieuTheoPhong(String maPhong) {
        List<LichChieu> list = new ArrayList<>();
        String sql = """
            SELECT *
            FROM LichChieu lc
            JOIN Phim p ON lc.maPhim = p.maPhim
            JOIN Phong ph ON lc.maPhong = ph.maPhong
            WHERE lc.maPhong = ?
            ORDER BY lc.ngayChieu, lc.gioBatDau
        """;

        try {
        	Connection con = DatabaseConnection.getInstance().getConnection();
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                LichChieu lc = createLichChieuFromResultSet(rs);
                list.add(lc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

}
