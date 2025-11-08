package dao;

import java.nio.file.FileSystemNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LoaiPhim;

public class LoaiPhimDAO {
	public List<LoaiPhim> getAllLoaiPhim() {
		List<LoaiPhim> list = new ArrayList<LoaiPhim>();
		
		String sql = "SELECT * FROM LoaiPhim";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while (rs.next()) {
				LoaiPhim lp = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"));
				
				list.add(lp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	public boolean isMaPhimExists(String maLoaiPhim) {
        String sql = "SELECT COUNT(*) FROM LoaiPhim WHERE maLoaiPhim = ?";
        
        try (Connection conn = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, maLoaiPhim);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return false;
    }
	
	public boolean insert(LoaiPhim loaiPhim) {
		
		if(isMaPhimExists(loaiPhim.getMaLoaiPhim())) {
			System.err.println("Ma loai phim ton tai!");
			return false;
		}
		
		String sql = "INSERT INTO LoaiPhim(maLoaiPhim, tenLoaiPhim) VALUES(?,?)";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, loaiPhim.getMaLoaiPhim());
			stmt.setString(2, loaiPhim.getTenLoaiPhim());
			
			int n = stmt.executeUpdate();
			
			return n > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi them loaiphim trung ma: " + loaiPhim.getMaLoaiPhim());
			return false;
		}
	}
	
	public boolean update(LoaiPhim loaiPhim) {
		String sql = "UPDATE LoaiPhim SET tenLoaiPhim=? WHERE maLoaiPhim=?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			stmt.setString(1, loaiPhim.getTenLoaiPhim());
			stmt.setString(2, loaiPhim.getMaLoaiPhim());
			
			int n = stmt.executeUpdate();
			
			return n > 0;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi update");
			return false;
		}
	}
	
	public boolean delete(String maLoaiPhim) {
		String sql = "DELETE FROM LoaiPhim WHERE maLoaiPhim=?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, maLoaiPhim);
			
			int n = stmt.executeUpdate();
			
			return n > 0;
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("Loi khi xoa");
			return false;
		}
	}
	
	public List<LoaiPhim> search(String keyword) {
		List<LoaiPhim> list = new ArrayList<LoaiPhim>();
		
		String sql = "SELECT * FROM LoaiPhim WHERE tenloaiPhim LIKE ? ORDER BY maLoaiPhim";
		
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			
			String searchPattern = "%" + keyword + "%";
			stmt.setString(1, searchPattern);
			
			try (ResultSet rs = stmt.executeQuery()){
				while (rs.next()) {
					LoaiPhim lp = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"));
					
					list.add(lp);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}
	
	public LoaiPhim getById(String ID) {
		String sql = "SELECT * FROM LoaiPhim WHERE maLoaiPhim=?";
		
		LoaiPhim loaiPhim = new LoaiPhim();
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
	             PreparedStatement stmt = conn.prepareStatement(sql)) {
	            
	            stmt.setString(1, ID);
	            ResultSet rs = stmt.executeQuery();
	            
	            if (rs.next()) {
	                loaiPhim = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"));
	            }
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
		return loaiPhim;
	}
	
	public static void main(String[] args) {
		LoaiPhimDAO loaiPhimDAO = new LoaiPhimDAO();
		List<LoaiPhim> list = loaiPhimDAO.getAllLoaiPhim();
		for (LoaiPhim loaiPhim : list) {
			System.out.println(loaiPhim);
		}
	}
}
