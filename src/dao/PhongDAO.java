package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public Phong findPhongByMa(String maPhong) {
        Phong phong = null;

        String sql = """
            SELECT p.maPhong, p.tenPhong, p.soLuongGhe, p.trangThai,
                   lp.maLoaiPhong, lp.tenLoaiPhong, lp.moTa
            FROM Phong p
            LEFT JOIN LoaiPhong lp ON lp.maLoaiPhong = p.maLoaiPhong
            WHERE p.maPhong = ?
        """;

        try (Connection con = DatabaseConnection.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, maPhong);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LoaiPhong loaiPhong = new LoaiPhong(
                        rs.getString("maLoaiPhong"),
                        rs.getString("tenLoaiPhong"),
                        rs.getString("moTa")
                    );

                    phong = new Phong(
                        rs.getString("maPhong"),
                        rs.getString("tenPhong"),
                        rs.getInt("soLuongGhe"),
                        loaiPhong,
                        rs.getBoolean("trangThai")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Lỗi lấy danh sách phim trong db");
        }

        return phong;
    }
	public static void main(String[] args) {
		PhongDAO phongDAO = new PhongDAO();
		List<Phong> list = phongDAO.getAll();
		for (Phong phong : list) {
			System.out.println(phong);
		}
		
		
	}
	
}
