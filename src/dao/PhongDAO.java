package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
					"ORDER BY tenPhong";
		
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
	
	public static void main(String[] args) {
		PhongDAO phongDAO = new PhongDAO();
		List<Phong> list = phongDAO.getAll();
		for (Phong phong : list) {
			System.out.println(phong);
		}
		
		
	}
	
}
