package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.Thue;

public class ThueDAO {

	public List<Thue> getAllThue() {
		List<Thue> list = new ArrayList<Thue>();
		String sql = "SELECT * FROM Thue";

		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				Thue thue = new Thue(
						rs.getString("maThue"), 
						rs.getString("tenThue"), 
						rs.getFloat("phanTram"),
						rs.getString("moTa"));
				list.add(thue);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean isMaThueExists(String maThue) {
		String sql = "SELECT COUNT(*) FROM Thue WHERE maThue = ?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maThue);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insert(Thue thue) {
		if (isMaThueExists(thue.getMaThue())) {
			System.err.println("Ma thue ton tai!");
			return false;
		}
		String sql = "INSERT INTO Thue(maThue, tenThue, phanTram, moTa) VALUES(?,?,?,?)";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, thue.getMaThue());
			stmt.setString(2, thue.getTenThue());
			stmt.setFloat(3, thue.getPhanTram());
			stmt.setString(4, thue.getMoTa());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi them thue: " + thue.getMaThue());
			return false;
		}
	}

	public boolean update(Thue thue) {
		String sql = "UPDATE Thue SET tenThue=?, phanTram=?, moTa=? WHERE maThue=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, thue.getTenThue());
			stmt.setFloat(2, thue.getPhanTram());
			stmt.setString(3, thue.getMoTa());
			stmt.setString(4, thue.getMaThue());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi update thue");
			return false;
		}
	}

	public boolean delete(String maThue) {
		String sql = "DELETE FROM Thue WHERE maThue=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maThue);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi xoa thue");
			return false;
		}
	}

	public List<Thue> search(String keyword) {
		List<Thue> list = new ArrayList<Thue>();
		String sql = "SELECT * FROM Thue WHERE tenThue LIKE ? ORDER BY maThue";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			String searchPattern = "%" + keyword + "%";
			stmt.setString(1, searchPattern);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					Thue thue = new Thue(
							rs.getString("maThue"), 
							rs.getString("tenThue"), 
							rs.getFloat("phanTram"),
							rs.getString("moTa"));
					list.add(thue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public Thue getById(String ID) {
		String sql = "SELECT * FROM Thue WHERE maThue=?";
		Thue thue = null;
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				thue = new Thue(
						rs.getString("maThue"), 
						rs.getString("tenThue"), 
						rs.getFloat("phanTram"),
						rs.getString("moTa"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return thue;
	}
}