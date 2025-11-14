// ============================================
// GHE DAO - HÙNG PHỤ TRÁCH (BẢN SỬA HOÀN CHỈNH)
// ============================================
package dao;

import entity.Ghe;
import entity.Phong;
import entity.LoaiGhe;
import entity.LoaiPhong;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import connectDB.DatabaseConnection;

/**
 * Data Access Object cho bảng Ghe
 * @author Hùng (sửa hoàn thiện)
 */
public class GheDAO {

    // Nếu không còn dùng phongDAO trong mapping thì có thể xóa biến này.
    private PhongDAO phongDAO = new PhongDAO();

    /**
     * Lấy tất cả ghế từ database
     * @return List<Ghe>
     */
    public ArrayList<Ghe> getAllGhe() {
        ArrayList<Ghe> list = new ArrayList<>();
        String sql =
            "SELECT " +
            "g.maGhe, g.maPhong, g.maLoaiGhe, g.trangThai AS trangThaiGhe, " +
            "p.tenPhong, p.soLuongGhe, p.trangThai AS trangThaiPhong, p.maLoaiPhong, " +
            "lp.tenLoaiPhong, lp.moTa AS moTaLoaiPhong, " +
            "lg.tenLoaiGhe, lg.phuThu, lg.moTa AS moTaLoaiGhe " +
            "FROM Ghe g " +
            "LEFT JOIN Phong p ON g.maPhong = p.maPhong " +
            "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
            "LEFT JOIN LoaiGhe lg ON g.maLoaiGhe = lg.maLoaiGhe " +
            "ORDER BY g.maPhong, g.maGhe";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Ghe ghe = createGheFromResultSet(rs);
                list.add(ghe);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy danh sách ghế: " + e.getMessage());
        }

        return list;
    }

    /**
     * Lấy ghế theo phòng
     * @param maPhong
     * @return List<Ghe>
     */
    public List<Ghe> getByPhong(String maPhong) {
        List<Ghe> list = new ArrayList<>();
        String sql =
            "SELECT " +
            "g.maGhe, g.maPhong, g.maLoaiGhe, g.trangThai AS trangThaiGhe, " +
            "p.tenPhong, p.soLuongGhe, p.trangThai AS trangThaiPhong, p.maLoaiPhong, " +
            "lp.tenLoaiPhong, lp.moTa AS moTaLoaiPhong, " +
            "lg.tenLoaiGhe, lg.phuThu, lg.moTa AS moTaLoaiGhe " +
            "FROM Ghe g " +
            "LEFT JOIN Phong p ON g.maPhong = p.maPhong " +
            "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
            "LEFT JOIN LoaiGhe lg ON g.maLoaiGhe = lg.maLoaiGhe " +
            "WHERE g.maPhong = ? " +
            "ORDER BY g.maGhe";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ghe ghe = createGheFromResultSet(rs);
                    list.add(ghe);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy ghế theo phòng: " + e.getMessage());
        }

        return list;
    }

    /**
     * Lấy ghế theo mã
     * @param maGhe
     * @return Ghe hoặc null
     */
    public Ghe getById(String maGhe) {
        String sql =
            "SELECT " +
            "g.maGhe, g.maPhong, g.maLoaiGhe, g.trangThai AS trangThaiGhe, " +
            "p.tenPhong, p.soLuongGhe, p.trangThai AS trangThaiPhong, p.maLoaiPhong, " +
            "lp.tenLoaiPhong, lp.moTa AS moTaLoaiPhong, " +
            "lg.tenLoaiGhe, lg.phuThu, lg.moTa AS moTaLoaiGhe " +
            "FROM Ghe g " +
            "LEFT JOIN Phong p ON g.maPhong = p.maPhong " +
            "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
            "LEFT JOIN LoaiGhe lg ON g.maLoaiGhe = lg.maLoaiGhe " +
            "WHERE g.maGhe = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return createGheFromResultSet(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi lấy ghế: " + e.getMessage());
        }

        return null;
    }

    /**
     * Thêm ghế mới
     * @param ghe
     * @return true nếu thành công
     */
    public boolean insert(Ghe ghe) {
        String sql = "INSERT INTO Ghe (maGhe, maPhong, maLoaiGhe, trangThai) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getMaGhe());
            stmt.setString(2, ghe.getPhong().getMaPhong());
            stmt.setString(3, ghe.getLoaiGhe().getMaLoaiGhe());
            stmt.setString(4, ghe.getTrangThai());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Thêm ghế thành công: " + ghe.getMaGhe());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm ghế: " + e.getMessage());
        }

        return false;
    }

    /**
     * Cập nhật ghế
     * @param ghe
     * @return true nếu thành công
     */
    public boolean update(Ghe ghe) {
        String sql = "UPDATE Ghe SET maPhong = ?, maLoaiGhe = ?, trangThai = ? WHERE maGhe = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, ghe.getPhong().getMaPhong());
            stmt.setString(2, ghe.getLoaiGhe().getMaLoaiGhe());
            stmt.setString(3, ghe.getTrangThai());
            stmt.setString(4, ghe.getMaGhe());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Cập nhật ghế thành công: " + ghe.getMaGhe());
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật ghế: " + e.getMessage());
        }

        return false;
    }

    /**
     * Xóa ghế
     * @param maGhe
     * @return true nếu thành công
     */
    public boolean delete(String maGhe) {
        String sql = "DELETE FROM Ghe WHERE maGhe = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("✅ Xóa ghế thành công: " + maGhe);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa ghế: " + e.getMessage());

            if (e.getMessage().contains("REFERENCE constraint")) {
                System.err.println("⚠️ Không thể xóa ghế vì đã có vé!");
            }
        }

        return false;
    }

    /**
     * Kiểm tra mã ghế đã tồn tại chưa
     * @param maGhe
     * @return true nếu đã tồn tại
     */
    public boolean isMaGheExists(String maGhe) {
        String sql = "SELECT COUNT(*) FROM Ghe WHERE maGhe = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maGhe);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Tạo ghế tự động cho phòng theo pattern hàng-cột
     */
    public int generateGheForPhong(String maPhong, int soHang, int soGheMotHang, String maLoaiGhe) {
        int count = 0;
        String sqlInsert = "INSERT INTO Ghe (maGhe, maPhong, maLoaiGhe, trangThai) VALUES (?, ?, ?, ?)";
        String sqlSelect = "SELECT maGhe FROM Ghe WHERE maPhong = ?";
        String sqlPhong = "SELECT soLuongGhe FROM Phong WHERE maPhong = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmtInsert = conn.prepareStatement(sqlInsert);
             PreparedStatement stmtSelect = conn.prepareStatement(sqlSelect);
             PreparedStatement stmtPhong = conn.prepareStatement(sqlPhong)) {

            conn.setAutoCommit(false); // bắt đầu transaction

            // ✅ 1️⃣ Lấy số lượng ghế tối đa của phòng
            int soLuongGheToiDa = 0;
            stmtPhong.setString(1, maPhong);
            try (ResultSet rsPhong = stmtPhong.executeQuery()) {
                if (rsPhong.next()) {
                    soLuongGheToiDa = rsPhong.getInt("soLuongGhe");
                }
            }

            // ✅ 2️⃣ Lấy danh sách ghế đã có
            Set<String> existingGhe = new HashSet<>();
            stmtSelect.setString(1, maPhong);
            try (ResultSet rs = stmtSelect.executeQuery()) {
                while (rs.next()) {
                    existingGhe.add(rs.getString("maGhe"));
                }
            }

            int soGheHienCo = existingGhe.size();
            int soGheCanTao = soHang * soGheMotHang;

            // ✅ 3️⃣ Kiểm tra giới hạn
            if (soGheHienCo + soGheCanTao > soLuongGheToiDa) {
                JOptionPane.showMessageDialog(null,
                    "⚠️ Phòng này chỉ cho phép tối đa " + soLuongGheToiDa + " ghế.\n" +
                    "Hiện đã có " + soGheHienCo + " ghế, bạn đang cố tạo thêm " + soGheCanTao + " ghế.",
                    "Vượt quá giới hạn", JOptionPane.WARNING_MESSAGE);
                conn.rollback();
                return 0;
            }

            // ✅ 4️⃣ Sinh ghế mới
            for (int hang = 0; hang < soHang; hang++) {
                char kyTuHang = (char) ('A' + hang);
                for (int cot = 1; cot <= soGheMotHang; cot++) {
                    String maGhe = String.format("%s%s%02d", maPhong, kyTuHang, cot);
                    if (!existingGhe.contains(maGhe)) {
                        stmtInsert.setString(1, maGhe);
                        stmtInsert.setString(2, maPhong);
                        stmtInsert.setString(3, maLoaiGhe);
                        stmtInsert.setString(4, "Trống");
                        stmtInsert.addBatch();
                        count++;
                    }
                }
            }

            // ✅ 5️⃣ Thực thi và commit
            stmtInsert.executeBatch();
            conn.commit();
            conn.setAutoCommit(true);

            System.out.println("✅ Đã tạo " + count + " ghế cho phòng " + maPhong);

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tạo ghế tự động: " + e.getMessage());
        }

        return count;
    }


    /**
     * Xóa tất cả ghế trong phòng
     */
    public boolean deleteByPhong(String maPhong) {
        String sql = "DELETE FROM Ghe WHERE maPhong = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            int rowsAffected = stmt.executeUpdate();

            System.out.println("✅ Xóa " + rowsAffected + " ghế trong phòng " + maPhong);
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa ghế theo phòng: " + e.getMessage());
        }

        return false;
    }

    /**
     * Đếm số ghế trong phòng
     */
    public int countGheByPhong(String maPhong) {
        String sql = "SELECT COUNT(*) FROM Ghe WHERE maPhong = ?";

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * Helper method: Tạo object Ghe từ ResultSet
     * Lấy tất cả cột cần thiết từ ResultSet (đã alias rõ ràng ở SQL)
     */
    private Ghe createGheFromResultSet(ResultSet rs) throws SQLException {
        // --- Bảng LoaiPhong ---
        LoaiPhong loaiPhong = new LoaiPhong(
            rs.getString("maLoaiPhong"),
            rs.getString("tenLoaiPhong"),
            rs.getString("moTaLoaiPhong")
        );

        // --- Bảng Phong ---
        // Lưu ý: dùng getString cho trangThaiPhong để tránh mismatch kiểu DB (bit/varchar)
        Phong phong = new Phong(
            rs.getString("maPhong"),
            rs.getString("tenPhong"),
            rs.getInt("soLuongGhe"),
            loaiPhong,
            rs.getBoolean("trangThaiPhong")
        );

        // --- Bảng LoaiGhe ---
        LoaiGhe loaiGhe = new LoaiGhe(
            rs.getString("maLoaiGhe"),
            rs.getString("tenLoaiGhe"),
            rs.getDouble("phuThu"),
            rs.getString("moTaLoaiGhe")
        );

        // --- Bảng Ghe ---
        Ghe ghe = new Ghe(
            rs.getString("maGhe"),
            phong,
            loaiGhe,
            rs.getString("trangThaiGhe")
        );

        return ghe;
    }
    public ArrayList<Ghe> findGheByPhong(String maPhong) {
        ArrayList<Ghe> list = new ArrayList<>();
        String sql =
            "SELECT " +
            "g.maGhe, g.maPhong, g.maLoaiGhe, g.trangThai AS trangThaiGhe, " +
            "p.tenPhong, p.soLuongGhe, p.trangThai AS trangThaiPhong, p.maLoaiPhong, " +
            "lp.tenLoaiPhong, lp.moTa AS moTaLoaiPhong, " +
            "lg.tenLoaiGhe, lg.phuThu, lg.moTa AS moTaLoaiGhe " +
            "FROM Ghe g " +
            "LEFT JOIN Phong p ON g.maPhong = p.maPhong " +
            "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
            "LEFT JOIN LoaiGhe lg ON g.maLoaiGhe = lg.maLoaiGhe " +
            "WHERE g.maPhong = ? " +
            "ORDER BY g.maGhe";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Ghe ghe = createGheFromResultSet(rs);
                    list.add(ghe);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi khi lấy ghế theo phòng: " + e.getMessage());
        }

        return list;
    }

}
