package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import entity.DatVe;


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
	public boolean addDatVe() {
		return true;
	}
	
	
	public boolean removeDatVe() {
		return true;
	}
	
	public boolean updateDatVe() {
		return true;
	}
	
}
