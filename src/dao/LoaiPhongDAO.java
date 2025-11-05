package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LoaiPhong;

public class LoaiPhongDAO {
	public List<LoaiPhong> getAllLoaiPhong() {
		List<LoaiPhong> list = new ArrayList<LoaiPhong>();
		
		String sql = "SELECT * FROM LoaiPhong";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while (rs.next()) {
				LoaiPhong lp = new LoaiPhong(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"), rs.getString("moTa"));
				
				list.add(lp);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}
}
