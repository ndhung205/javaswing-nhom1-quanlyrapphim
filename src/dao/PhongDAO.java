package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entity.LoaiPhong;
import entity.Phong;

public class PhongDAO {
	private ArrayList<Phong> listPhong;
	private Connection con;
	
	
	
	public PhongDAO() {
		listPhong = new ArrayList<Phong>();
	}
	
	public void connectDatabase() {
		try {
			con = null;
			String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyRapChieuPhim";
			String user = "sa";
			String pass ="sapassword";
			
			con =  DriverManager.getConnection(url, user, pass);
			
			Statement stm = con.createStatement();
			String sql = "SELECT * FROM Phong LEFT JOIN LoaiPhong ON LoaiPhong.maLoaiPhong = Phong.maLoaiPhong";
			
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				String maPhong = rs.getString("maPhong");
				String tenPhong = rs.getString("tenPhong");
				int soluongGhe = rs.getInt("soLuongGhe");
				LoaiPhong lp = new LoaiPhong(rs.getString("maLoaiPhong"), rs.getString("tenLoaiPhong"), rs.getString("moTa"));
				Phong p = new Phong(maPhong, tenPhong, soluongGhe, lp, (rs.getBoolean("trangThai")));
				
				listPhong.add(p);
			}
			System.out.println("Phong kết nối SQL thành công.");
			con.close();
			
			
		} catch (Exception e) {
			System.out.println("Phong kết nối SQL thất bại. Vui lòng kiểm tra kết nối.");
			e.printStackTrace();
		}
	}
	
	
	public boolean addPhong() {
		return true;
	}
	
	
	public boolean removePhong() {
		return true;
	}
	
	public boolean updatePhong() {
		return true;
	}
	
	public Phong findPhongByIndex(int index) {
		return listPhong.get(index);
	}
	public int getSize() {
		return listPhong.size();
	}

	
}
