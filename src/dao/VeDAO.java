package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import connectDB.DatabaseConnection;
import entity.Ve;

public class VeDAO {
	private ArrayList<Ve> listVe;
	private Connection con;
	
	
	public VeDAO() {
		this.listVe = new ArrayList<Ve>();
	}
	public ArrayList<Ve> connectDatabase() {
		try {
			con = null;
			String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyRapChieuPhim";
			String user = "sa";
			String pass ="sapassword";
			
			con =  DriverManager.getConnection(url, user, pass);
			
			Statement stm = con.createStatement();
			String sql = "Select * from Ve";
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				String maVe = rs.getString("maVe");
				double gia = rs.getDouble("gia");
				Date thoiGianDat = rs.getDate("thoiGianDat");
				String trangthai = rs.getString("trangThai");
				
				Ve ve = new Ve(maVe, gia, trangthai, thoiGianDat);
				listVe.add(ve);
			}
			
			con.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return listVe;
	}


	public boolean addVe() {
		return true;
	}
	
	
	public boolean removeVe() {
		return true;
	}
	
	public boolean updateVe() {
		return true;
	}
}
