package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import connectDB.DatabaseConnection;
import entity.DatVe;
import entity.Ve;

public class DatVeDAO {
	private ArrayList<DatVe> listDatVe;
	private Connection con;
	
	
	
	public DatVeDAO() {
		listDatVe = new ArrayList<DatVe>();
	}
	
	public void connectDatabase() {
		try {
			con = null;
			String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyRapChieuPhim";
			String user = "sa";
			String pass ="sapassword";
			
			con =  DriverManager.getConnection(url, user, pass);
			
			Statement stm = con.createStatement();
			String sql = "Select * from DatVe";
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				
			}
			
			con.close();
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public boolean addDatVe(DatVe dv) {
		con = DatabaseConnection.getInstance().getConnection();
		PreparedStatement stmt = null;
		int n =0;
		try {
			stmt = con.prepareStatement("INSERT INTO DatVe VALUES (?,?,?,?,?)");
			stmt.setString(1, dv.getMaDatVe());
			stmt.setString(2, null);
			stmt.setString(3, dv.getNgayDat().toLocaleString());
			stmt.setString(4, dv.getTrangThai());
			stmt.setString(5, null);
			
			n = stmt.executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return n>0;
	}
	
	
	public boolean removeDatVe() {
		return true;
	}
	
	public boolean updateDatVe() {
		return true;
	}
	
}
