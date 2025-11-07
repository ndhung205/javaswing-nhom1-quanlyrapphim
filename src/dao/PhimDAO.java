package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import connectDB.DatabaseConnection;
import entity.LoaiPhim;
import entity.Phim;

public class PhimDAO {

	public List<Phim> getAll() {
		List<Phim> list = new ArrayList<Phim>();
		
		String sql = "SELECT p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster, p.maLoaiPhim, lp.tenLoaiPhim " +
					"FROM Phim p " +
					"JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim "+
					"ORDER BY p.maPhim";

		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			
			while (rs.next()) {
				LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"));
				
				Phim phim = new Phim(rs.getString("maPhim"), rs.getString("tenPhim"), loaiPhim, rs.getString("moTa"), rs.getInt("thoiLuongChieu"), rs.getInt("namPhatHanh"), rs.getString("poster"));
				
				list.add(phim);
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Lỗi lấy danh sách phim trong db");
		}
		
		return list;
	}
	
	public Phim getById(String maPhim) {
		String sql = "SELECT p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.maLoaiPhim, p.poster, lp.tenLoaiPhim " +
				"FROM Phim p " +
				"JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim "+
				"WHERE maPhim = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, maPhim);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"));		
				
				Phim phim = new Phim(rs.getString("maPhim"), rs.getString("tenPhim"), loaiPhim, rs.getString("moTa"), rs.getInt("thoiLuongChieu"), rs.getInt("namPhatHanh"), rs.getString("poster"));
				
				return phim;
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Không tìm thấy phim có mã: " + maPhim);
			e.printStackTrace();
		}
		return null;
	}
	
	public int getSize() {
		return getAll().size();
	}
	
	public Phim findPhimByIndex(int index) {
		return getAll().get(index);
	}
	
	public Phim findPhimByTen(String tenPhim) {
		List<Phim> list = getAll();
		for (Phim phim2 : list) {
			if (phim2.getTenPhim().equalsIgnoreCase(tenPhim))
				return phim2;
		}
		return null;
	}
	
	public boolean isMaPhimExists(String maPhim) {
        String sql = "SELECT COUNT(*) FROM Phim WHERE maPhim = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maPhim);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
	
	public boolean createPhim(Phim phim) {
		
		if(isMaPhimExists(phim.getMaPhim())) {
			System.err.println("Ma phim ton tai!");
			return false;
		}
		
		String sqlInsert = "INSERT INTO Phim(maPhim, tenPhim, maLoaiPhim, moTa, thoiLuongChieu, namPhatHanh, poster) " +
					"VALUES(?,?,?,?,?,?,?)";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlInsert)){
			
			stmt.setString(1, phim.getMaPhim());
			stmt.setString(2, phim.getTenPhim());
			stmt.setString(3, phim.getLoaiPhim().getMaLoaiPhim());
			stmt.setString(4, phim.getMoTa());
			stmt.setInt(5, phim.getThoiLuongChieu());
			stmt.setInt(6, phim.getNamPhatHanh());
			stmt.setString(7, phim.getPoster());
			
			int n = stmt.executeUpdate();
            return n > 0;
		} catch (Exception e) {
			// TODO: handle exception
			System.err.println("Lỗi khi thêm phim có mã: " + phim.getMaPhim());
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean updatePhim(Phim phim) {
		String sql = "UPDATE Phim SET tenPhim=?, maLoaiPhim=?, moTa=?, thoiLuongChieu=?, namPhatHanh=?, poster=? "+
					"WHERE maPhim=?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, phim.getTenPhim());
			stmt.setString(2, phim.getLoaiPhim().getMaLoaiPhim());
			stmt.setString(3, phim.getMoTa());
			stmt.setInt(4, phim.getThoiLuongChieu());
			stmt.setInt(5, phim.getNamPhatHanh());
			stmt.setString(7, phim.getMaPhim());
			stmt.setString(6, phim.getPoster());
			
			int n = stmt.executeUpdate();
			return n > 0;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi update!");
			return false;
		}
	}
	
	public boolean deletePhim(String maPhim) {
		String sql = "DELETE FROM Phim WHERE maPhim=?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, maPhim);
			int n = stmt.executeUpdate();
			return n > 0;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi xoa phim!");
			return false;
		}
	}
	
	/**
     * Tìm kiếm phim theo tên
     * @param keyword
     * @return List<Phim>
     */
	public List<Phim> search(String keyword) {
	    List<Phim> list = new ArrayList<>();

	    String sql = "SELECT p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.poster, " +
	                 "p.maLoaiPhim, lp.tenLoaiPhim " +
	                 "FROM Phim p " +
	                 "LEFT JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim " +
	                 "WHERE p.tenPhim LIKE ? OR p.moTa LIKE ? " +
	                 "ORDER BY p.tenPhim";

	    try (Connection conn = DatabaseConnection.getInstance().getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {

	        String searchPattern = "%" + keyword + "%";
	        stmt.setString(1, searchPattern);
	        stmt.setString(2, searchPattern);

	        ResultSet rs = stmt.executeQuery();

	        while (rs.next()) {

	            LoaiPhim loaiPhim = new LoaiPhim(
	                rs.getString("maLoaiPhim"),
	                rs.getString("tenLoaiPhim")
	            );

	            Phim phim = new Phim(
	                rs.getString("maPhim"),
	                rs.getString("tenPhim"),
	                loaiPhim,
	                rs.getString("moTa"),
	                rs.getInt("thoiLuongChieu"),
	                rs.getInt("namPhatHanh"),
	                rs.getString("poster")
	            );

	            list.add(phim);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        System.err.println("❌ Lỗi khi tìm kiếm phim: " + e.getMessage());
	    }

	    return list;
	}

    
    public Set<Integer> getNamPhatHanh() {
		Set<Integer> list = new HashSet<Integer>();
		
		String sql = "SELECT namPhatHanh FROM Phim ORDER BY namPhatHanh";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while (rs.next()) {
				list.add(rs.getInt("namPhatHanh"));
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return list;
	}
    
    public List<Phim> getByLoaiPhim(LoaiPhim loaiPhim) {
        List<Phim> list = new ArrayList<>();

        String sql = """
            SELECT maPhim, tenPhim, moTa, thoiLuongChieu, namPhatHanh, poster
            FROM Phim
            WHERE maLoaiPhim = ?
            ORDER BY maPhim
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, loaiPhim.getMaLoaiPhim());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Phim p = new Phim(
                        rs.getString("maPhim"),
                        rs.getString("tenPhim"),
                        loaiPhim, // dùng luôn đối tượng đã truyền vào
                        rs.getString("moTa"),
                        rs.getInt("thoiLuongChieu"),
                        rs.getInt("namPhatHanh"),
                        rs.getString("poster")
                    );

                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Phim> getByNam(Integer nam) {
        List<Phim> list = new ArrayList<>();

        String sql = """
            SELECT p.maPhim, p.tenPhim, p.moTa AS moTaPhim, 
                   p.thoiLuongChieu, p.namPhatHanh, p.poster,
                   lp.maLoaiPhim, lp.tenLoaiPhim, lp.moTa AS moTaLoaiPhim
            FROM Phim p
            LEFT JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim
            WHERE p.namPhatHanh = ?
            ORDER BY p.maPhim
        """;

        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, nam);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    LoaiPhim lp = new LoaiPhim(
                        rs.getString("maLoaiPhim"),
                        rs.getString("tenLoaiPhim"));

                    Phim p = new Phim(
                        rs.getString("maPhim"),
                        rs.getString("tenPhim"),
                        lp,
                        rs.getString("moTaPhim"),
                        rs.getInt("thoiLuongChieu"),
                        rs.getInt("namPhatHanh"),
                        rs.getString("poster")
                    );
                    list.add(p);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
	
}
