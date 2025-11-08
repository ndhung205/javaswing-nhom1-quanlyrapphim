package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				LoaiGhe lg = new LoaiGhe(
						rs.getString("maLoaiGhe"), 
						rs.getString("tenLoaiGhe"),
						rs.getDouble("phuThu"), 
						rs.getString("moTa"));
				list.add(lg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean isMaLoaiGheExists(String maLoaiGhe) {
		String sql = "SELECT COUNT(*) FROM LoaiGhe WHERE maLoaiGhe = ?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLoaiGhe);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insert(LoaiGhe loaiGhe) {
		if (isMaLoaiGheExists(loaiGhe.getMaLoaiGhe())) {
			System.err.println("Ma loai ghe ton tai!");
			return false;
		}
		String sql = "INSERT INTO LoaiGhe(maLoaiGhe, tenLoaiGhe, phuThu, moTa) VALUES(?,?,?,?)";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, loaiGhe.getMaLoaiGhe());
			stmt.setString(2, loaiGhe.getTenLoaiGhe());
			stmt.setDouble(3, loaiGhe.getPhuThu());
			stmt.setString(4, loaiGhe.getMoTa());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi them loai ghe: " + loaiGhe.getMaLoaiGhe());
			return false;
		}
	}

	public boolean update(LoaiGhe loaiGhe) {
		String sql = "UPDATE LoaiGhe SET tenLoaiGhe=?, phuThu=?, moTa=? WHERE maLoaiGhe=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, loaiGhe.getTenLoaiGhe());
			stmt.setDouble(2, loaiGhe.getPhuThu());
			stmt.setString(3, loaiGhe.getMoTa());
			stmt.setString(4, loaiGhe.getMaLoaiGhe());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi update loai ghe");
			return false;
		}
	}

	public boolean delete(String maLoaiGhe) {
		String sql = "DELETE FROM LoaiGhe WHERE maLoaiGhe=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maLoaiGhe);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi xoa loai ghe");
			return false;
		}
	}

	public List<LoaiGhe> search(String keyword) {
		List<LoaiGhe> list = new ArrayList<LoaiGhe>();
		String sql = "SELECT * FROM LoaiGhe WHERE tenLoaiGhe LIKE ? ORDER BY maLoaiGhe";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			String searchPattern = "%" + keyword + "%";
			stmt.setString(1, searchPattern);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					LoaiGhe lg = new LoaiGhe(
							rs.getString("maLoaiGhe"), 
							rs.getString("tenLoaiGhe"),
							rs.getDouble("phuThu"), 
							rs.getString("moTa"));
					list.add(lg);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public LoaiGhe getById(String ID) {
		String sql = "SELECT * FROM LoaiGhe WHERE maLoaiGhe=?";
		LoaiGhe loaiGhe = null;
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				loaiGhe = new LoaiGhe(
						rs.getString("maLoaiGhe"), 
						rs.getString("tenLoaiGhe"),
						rs.getDouble("phuThu"), 
						rs.getString("moTa"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return loaiGhe;
	}
}