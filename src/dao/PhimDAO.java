package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LoaiPhim;
import entity.Phim;
import main.RapPhimApp;

public class PhimDAO {

	public List<Phim> getAll() {
		List<Phim> list = new ArrayList<Phim>();
		String sql = "SELECT p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.maLoaiPhim, lp.tenLoaiPhim " +
					"FROM Phim p " +
					"JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim "+
					"ORDER BY p.tenPhim";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			
			while (rs.next()) {
				LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"), " ");
				
				Phim phim = new Phim(rs.getString("maPhim"), rs.getString("tenPhim"), loaiPhim, rs.getString("moTa"), rs.getInt("thoiLuongChieu"), rs.getInt("namPhatHanh"));
				
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
		String sql = "SELECT p.maPhim, p.tenPhim, p.moTa, p.thoiLuongChieu, p.namPhatHanh, p.maLoaiPhim, lp.tenLoaiPhim " +
				"FROM Phim p " +
				"JOIN LoaiPhim lp ON p.maLoaiPhim = lp.maLoaiPhim "+
				"WHERE maPhim = ?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, maPhim);
			
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				LoaiPhim loaiPhim = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"), " ");		
				
				Phim phim = new Phim(rs.getString("maPhim"), rs.getString("tenPhim"), loaiPhim, rs.getString("moTa"), rs.getInt("thoiLuongChieu"), rs.getInt("namPhatHanh"));
				
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
		
		String sqlInsert = "INSERT INTO Phim(maPhim, tenPhim, maLoaiPhim, moTa, thoiLuongChieu, namPhatHanh) " +
					"VALUES(?,?,?,?,?,?)";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = conn.prepareStatement(sqlInsert)){
			
			stmt.setString(1, phim.getMaPhim());
			stmt.setString(2, phim.getTenPhim());
			stmt.setString(3, phim.getLoaiPhim().getMaLoaiPhim());
			stmt.setString(4, phim.getMoTa());
			stmt.setInt(5, phim.getThoiLuongChieu());
			stmt.setInt(6, phim.getNamPhatHanh());
			
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
		String sql = "UPDATE Phim SET tenPhim=?, maLoaiPhim=?, moTa=?, thoiLuongChieu=?, namPhatHanh=? "+
					"WHERE maPhim=?";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)){
			stmt.setString(1, phim.getTenPhim());
			stmt.setString(2, phim.getLoaiPhim().getMaLoaiPhim());
			stmt.setString(3, phim.getMoTa());
			stmt.setInt(4, phim.getThoiLuongChieu());
			stmt.setInt(5, phim.getNamPhatHanh());
			stmt.setString(6, phim.getMaPhim());
			
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
	
	public static void main(String[] args) {
		PhimDAO phimDAO = new PhimDAO();
		List<Phim> phims =  phimDAO.getAll();
		for (Phim phim : phims) {
			System.out.println(phim);
		}
		
	}
}
