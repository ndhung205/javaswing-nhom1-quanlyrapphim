package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public static void main(String[] args) {
		LoaiPhimDAO loaiPhimDAO = new LoaiPhimDAO();
		List<LoaiPhim> list = loaiPhimDAO.getAllLoaiPhim();
		for (LoaiPhim loaiPhim : list) {
			System.out.println(loaiPhim);
		}
	}
}
