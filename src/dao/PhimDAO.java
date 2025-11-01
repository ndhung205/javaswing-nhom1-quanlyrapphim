package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import entity.LoaiPhim;
import entity.Phim;


public class PhimDAO {
	private ArrayList<Phim> listPhim;
	private Connection con;
	
	
	
	public PhimDAO() {
		listPhim = new ArrayList<Phim>();
	}
	
	public void connectDatabase() {
		try {
			con = null;
			String url = "jdbc:sqlserver://localhost:1433;databasename=QuanLyRapChieuPhim";
			String user = "sa";
			String pass ="sapassword";
			
			con =  DriverManager.getConnection(url, user, pass);
			
			Statement stm = con.createStatement();
			String sql = "SELECT * FROM Phim LEFT JOIN LoaiPhim ON LoaiPhim.maLoaiPhim = Phim.maLoaiPhim";
			
			ResultSet rs = stm.executeQuery(sql);
			
			while(rs.next()) {
				LoaiPhim lp = new LoaiPhim(rs.getString("maLoaiPhim"), rs.getString("tenLoaiPhim"), rs.getString("moTa"));
				Phim p = new Phim(rs.getString("maPhim"), rs.getString("tenPhim"), lp, rs.getString(4), rs.getInt("thoiLuongChieu"), rs.getInt("namPhatHanh"));
				listPhim.add(p);
			}
			System.out.println("Phim kết nối SQL thành công.");
			con.close();
			
			
		} catch (Exception e) {
			System.out.println("Phim kết nối SQL thất bại. Vui lòng kiểm tra kết nối.");
			e.printStackTrace();
		}
	}
	
	
	public boolean addPhim() {
		return true;
	}
	
	
	public boolean removePhim() {
		return true;
	}
	
	public boolean updatePhim() {
		return true;
	}
	
	public Phim findPhimByIndex(int index) {
		return listPhim.get(index);
	}
	public Phim findPhimByMa(String ma) {
		for (Phim phim : listPhim) {
			if(phim.getMaPhim().equalsIgnoreCase(ma))
				return phim;
		}
		return null;
	}
	
	public Phim findPhimByTen(String ten) {
		for (Phim phim : listPhim) {
			if(phim.getTenPhim().equalsIgnoreCase(ten))
				return phim;
		}
		return null;
	}
	public int getSize() {
		return listPhim.size();
	}
	
	public static void main(String[] args) {
		PhimDAO phim = new PhimDAO();
		phim.connectDatabase();
		Phim p = new Phim();
		p = phim.findPhimByTen("Avatar 2");
		System.out.println(p.getThoiLuongChieu());
	}
}
