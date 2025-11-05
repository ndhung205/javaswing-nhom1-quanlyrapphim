package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.LoaiGhe;

public class LoaiGheDAO {
	public List<LoaiGhe> getAllLoaiGhe() {
		List<LoaiGhe> list = new ArrayList<LoaiGhe>();
		
		String sql = "SELECT * FROM LoaiGhe";
		
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()){
			while (rs.next()) {
				LoaiGhe lp = new LoaiGhe(rs.getString("maLoaiGhe"), rs.getString("tenLoaiGhe"), rs.getDouble("phuThu"), rs.getString("moTa"));
				list.add(lp);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return list;
	}
}
