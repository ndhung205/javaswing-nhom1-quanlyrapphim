package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LoaiPhong;
import entity.Phong;

public class PhongDAO {
	
	public List<Phong> getAll() {
		List<Phong> list = new ArrayList<Phong>();
		String sql = "SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.maLoaiPhong, lp.tenLoaiPhong, p.trangThai " +
					"FROM Phong p " +
					"JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
					"ORDER BY p.maPhong";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LoaiPhong loaiPhong = new LoaiPhong(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"), " ");
				
				Phong phong = new Phong(rs.getString("maPhong"), rs.getString("tenPhong"), rs.getInt("soLuongGhe"), loaiPhong, rs.getBoolean("trangThai"));
				
				list.add(phong);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi doc data db");
		}
		
		return list;
	}
	
	public int getSize() {
		return getAll().size();
	}
	
	public Phong findPhongByIndex(int index) {
		return getAll().get(index);
	}
	public Phong findPhongByMa(String maPhong) {
        Phong phong = null;

        String sql = """
            SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.trangThai,
                   lp.maLoaiPhong, lp.tenLoaiPhong, lp.moTa
            FROM Phong p
            LEFT JOIN LoaiPhong lp ON lp.maLoaiPhong = p.maLoaiPhong
            WHERE p.maPhong = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoaiPhong loaiPhong = new LoaiPhong(
                        rs.getString("maLoaiPhong"),
                        rs.getString("tenLoaiPhong"),
                        rs.getString("moTa")
                    );

                    phong = new Phong(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe"),
                        loaiPhong,
                        rs.getBoolean("trangThai")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi lấy danh sách phim trong db");
        }

        return phong;
    }
	
	/**
     * Thêm phòng mới
     * @param phong
     * @return true nếu thành công
     */
    public boolean insert(Phong phong) {
        String sql = "INSERT INTO Phong (maPhong, tenPhong, soLuongGhe, maLoaiPhong, trangThai) " +
                     "VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, phong.getMaPhong());
            stmt.setString(2, phong.getTenPhong());
            stmt.setInt(3, phong.getSoLuongGhe());
            stmt.setString(4, phong.getLoaiPhong().getMaLoaiPhong());
            stmt.setBoolean(5, phong.isTrangThai());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Thêm phòng thành công: " + phong.getTenPhong());
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi thêm phòng: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Cập nhật phòng
     * @param phong
     * @return true nếu thành công
     */
    public boolean update(Phong phong) {
        String sql = "UPDATE Phong SET tenPhong = ?, soLuongGhe = ?, " +
                     "maLoaiPhong = ?, trangThai = ? " +
                     "WHERE maPhong = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, phong.getTenPhong());
            stmt.setInt(2, phong.getSoLuongGhe());
            stmt.setString(3, phong.getLoaiPhong().getMaLoaiPhong());
            stmt.setBoolean(4, phong.isTrangThai());
            stmt.setString(5, phong.getMaPhong());
            
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Cập nhật phòng thành công: " + phong.getTenPhong());
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi cập nhật phòng: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Xóa phòng
     * @param maPhong
     * @return true nếu thành công
     */
    public boolean delete(String maPhong) {
        String sql = "DELETE FROM Phong WHERE maPhong = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhong);
            int rowsAffected = stmt.executeUpdate();
            
            if (rowsAffected > 0) {
                System.out.println("✅ Xóa phòng thành công: " + maPhong);
                return true;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi xóa phòng: " + e.getMessage());
            
            // Kiểm tra lỗi Foreign Key
            if (e.getMessage().contains("REFERENCE constraint")) {
                System.err.println("⚠️ Không thể xóa phòng vì đang có ghế hoặc lịch chiếu!");
            }
        }
        
        return false;
    }
    
    /**
     * Tìm kiếm phòng theo tên
     * @param keyword
     * @return List<Phong>
     */
    public List<Phong> search(String keyword) {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.trangThai, " +
                     "p.maLoaiPhong, lp.tenLoaiPhong, lp.moTa " +
                     "FROM Phong p " +
                     "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                     "WHERE p.tenPhong LIKE ? OR p.maPhong LIKE ? " +
                     "ORDER BY p.tenPhong";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String searchPattern = "%" + keyword + "%";
            stmt.setString(1, searchPattern);
            stmt.setString(2, searchPattern);
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                LoaiPhong loaiPhong = new LoaiPhong(
                    rs.getString("maLoaiPhong"),
                    rs.getString("tenLoaiPhong"),
                    rs.getString("moTa")
                );
                
                Phong phong = new Phong(
                    rs.getString("maPhong"),
                    rs.getString("tenPhong"),
                    rs.getInt("soLuongGhe"),
                    loaiPhong,
                    rs.getBoolean("trangThai")
                );
                
                list.add(phong);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("❌ Lỗi khi tìm kiếm phòng: " + e.getMessage());
        }
        
        return list;
    }
    
    /**
     * Kiểm tra mã phòng đã tồn tại chưa
     * @param maPhong
     * @return true nếu đã tồn tại
     */
    public boolean isMaPhongExists(String maPhong) {
        String sql = "SELECT COUNT(*) FROM Phong WHERE maPhong = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhong);
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
     * Lấy danh sách phòng đang hoạt động
     * @return List<Phong>
     */
    public List<Phong> getPhongHoatDong() {
        List<Phong> list = new ArrayList<>();
        String sql = "SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.trangThai, " +
                     "p.maLoaiPhong, lp.tenLoaiPhong, lp.moTa " +
                     "FROM Phong p " +
                     "LEFT JOIN LoaiPhong lp ON p.maLoaiPhong = lp.maLoaiPhong " +
                     "WHERE p.trangThai = 1 " +
                     "ORDER BY p.tenPhong";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                LoaiPhong loaiPhong = new LoaiPhong(
                    rs.getString("maLoaiPhong"),
                    rs.getString("tenLoaiPhong"),
                    rs.getString("moTa")
                );
                
                Phong phong = new Phong(
                    rs.getString("maPhong"),
                    rs.getString("tenPhong"),
                    rs.getInt("soLuongGhe"),
                    loaiPhong,
                    rs.getBoolean("trangThai")
                );
                
                list.add(phong);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return list;
    }
	
	public static void main(String[] args) {
		PhongDAO phongDAO = new PhongDAO();
		List<Phong> list = phongDAO.getAll();
		for (Phong phong : list) {
			System.out.println(phong);
		}
		
		
	}
	public Phong findPhongByTen(String ten) {
        Phong phong = null;

        String sql = """
            SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.trangThai,
                   lp.maLoaiPhong, lp.tenLoaiPhong, lp.moTa
            FROM Phong p
            LEFT JOIN LoaiPhong lp ON lp.maLoaiPhong = p.maLoaiPhong
            WHERE p.tenPhong = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, ten);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoaiPhong loaiPhong = new LoaiPhong(
                        rs.getString("maLoaiPhong"),
                        rs.getString("tenLoaiPhong"),
                        rs.getString("moTa")
                    );

                    phong = new Phong(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe"),
                        loaiPhong,
                        rs.getBoolean("trangThai")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi lấy danh sách phim trong db");
        }

        return phong;
    }
	
}
