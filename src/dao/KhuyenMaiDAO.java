package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import connectDB.DatabaseConnection;
import entity.KhuyenMai;

public class KhuyenMaiDAO {
	 public ArrayList<KhuyenMai> getAll() {
		ArrayList<KhuyenMai> list = new ArrayList<KhuyenMai>();
		String sql ="SELECT * FROM KhuyenMai";
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();
			
			while(rs.next()) {
				KhuyenMai km = new KhuyenMai(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"), rs.getDouble("phanTramGiam"), 
						rs.getDouble("soTienGiam"), rs.getDate("ngayBatDau"), rs.getDate("ngayKetThuc"), rs.getString("dieuKien"), rs.getBoolean("trangThai"));
				list.add(km);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	 public boolean addKhuyenMai(KhuyenMai km) {
		String sql = "INSERT INTO KhuyenMai VALUES(?,?,?,?,?,?,?,?)";
		int n = 0;
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);
			
			stmt.setString(1, km.getMaKM());
			stmt.setString(2, km.getTenKM());
			stmt.setDouble(3, km.getPhanTram());
			stmt.setDouble(4, km.getSoTienGiam());
			stmt.setDate(5, km.getNgayBD());
			stmt.setDate(6, km.getNgayKT());
			stmt.setString(7, km.getDieuKien());
			stmt.setBoolean(8, km.isTrangThai());
			
			
			n = stmt.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return n>0;
		
	}
	 public boolean removeKhuyenMai(String ma) {
			String sql = "DELETE FROM KhuyenMai WHERE maKhuyenMai = ?";
			int n = 0;
			try {
				Connection con = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = con.prepareStatement(sql);
				
				stmt.setString(1, ma);

				n = stmt.executeUpdate();
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			return n>0;
			
	}
	 public boolean updateKhuyenMai(KhuyenMai km) {
		String sql = "UPDATE KhuyenMai SET tenKhuyenMai=?, phanTramGiam=?,soTienGiam=?,ngayBatDau=?, ngayKetThuc=?,dieuKien=?, trangThai =? WHERE maKhuyenMai = ?";
		int n = 0;
		try {
			Connection con = DatabaseConnection.getInstance().getConnection();
			PreparedStatement stmt = con.prepareStatement(sql);			
			
			stmt.setString(1, km.getTenKM());
			stmt.setDouble(2, km.getPhanTram());
			stmt.setDouble(3, km.getSoTienGiam());
			stmt.setDate(4, km.getNgayBD());
			stmt.setDate(5, km.getNgayKT());
			stmt.setString(6, km.getDieuKien());
			stmt.setBoolean(7, km.isTrangThai());
			stmt.setString(8, km.getMaKM());
			
			n = stmt.executeUpdate();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return n>0;
	}
	 
	 public ArrayList<KhuyenMai> findKhuyenMaiById(String keyWord) {
		ArrayList<KhuyenMai> list = new ArrayList<KhuyenMai>();
		String sql = "SELECT * FROM KhuyenMai WHERE maKhuyenMai LIKE ? OR tenKhuyenMai LIKE ?";
		
		try {
			 Connection con = DatabaseConnection.getInstance().getConnection();
			 PreparedStatement stmt = con.prepareStatement(sql);
			 stmt.setString(1,"%"+keyWord+"%");
			 stmt.setString(2,"%"+keyWord+"%");
			 ResultSet rs = stmt.executeQuery();
			 
			 while(rs.next()) {
				 KhuyenMai km = new KhuyenMai(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"), rs.getDouble("phanTramGiam"), 
							rs.getDouble("soTienGiam"), rs.getDate("ngayBatDau"), rs.getDate("ngayKetThuc"), rs.getString("dieuKien"), rs.getBoolean("trangThai"));
				 list.add(km);
			 }
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return list;
	}
	 public KhuyenMai findKhuyenMaiByTen(String ten) {
			KhuyenMai km = null;
			String sql = "SELECT * FROM KhuyenMai WHERE tenKhuyenMai = ?";
			
			try {
				 Connection con = DatabaseConnection.getInstance().getConnection();
				 PreparedStatement stmt = con.prepareStatement(sql);
				 stmt.setString(1, ten);
				 ResultSet rs = stmt.executeQuery();
				 
				 if(rs.next()) {
					 km = new KhuyenMai(rs.getString("maKhuyenMai"), rs.getString("tenKhuyenMai"), rs.getDouble("phanTramGiam"), 
								rs.getDouble("soTienGiam"), rs.getDate("ngayBatDau"), rs.getDate("ngayKetThuc"), rs.getString("dieuKien"), rs.getBoolean("trangThai"));
				
				 }
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			return km;
		}
}
