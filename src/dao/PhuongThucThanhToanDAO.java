package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import connectDB.DatabaseConnection;
import entity.PhuongThucThanhToan;

public class PhuongThucThanhToanDAO {

	public List<PhuongThucThanhToan> getAllPhuongThucThanhToan() {
		List<PhuongThucThanhToan> list = new ArrayList<PhuongThucThanhToan>();
		String sql = "SELECT * FROM PhuongThucThanhToan";

		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql);
				ResultSet rs = stmt.executeQuery()) {
			while (rs.next()) {
				PhuongThucThanhToan pttt = new PhuongThucThanhToan(
						rs.getString("maPhuongThuc"), 
						rs.getString("tenPhuongThuc"),
						rs.getString("moTa"));
				list.add(pttt);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public boolean isMaPhuongThucExists(String maPhuongThuc) {
		String sql = "SELECT COUNT(*) FROM PhuongThucThanhToan WHERE maPhuongThuc = ?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhuongThuc);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean insert(PhuongThucThanhToan pttt) {
		if (isMaPhuongThucExists(pttt.getMaPhuongThuc())) {
			System.err.println("Ma phuong thuc ton tai!");
			return false;
		}
		String sql = "INSERT INTO PhuongThucThanhToan(maPhuongThuc, tenPhuongThuc, moTa) VALUES(?,?,?)";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, pttt.getMaPhuongThuc());
			stmt.setString(2, pttt.getTenPhuongThuc());
			stmt.setString(3, pttt.getMoTa());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi them phuong thuc thanh toan: " + pttt.getMaPhuongThuc());
			return false;
		}
	}

	public boolean update(PhuongThucThanhToan pttt) {
		String sql = "UPDATE PhuongThucThanhToan SET tenPhuongThuc=?, moTa=? WHERE maPhuongThuc=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, pttt.getTenPhuongThuc());
			stmt.setString(2, pttt.getMoTa());
			stmt.setString(3, pttt.getMaPhuongThuc());
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi update phuong thuc thanh toan");
			return false;
		}
	}

	public boolean delete(String maPhuongThuc) {
		String sql = "DELETE FROM PhuongThucThanhToan WHERE maPhuongThuc=?";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, maPhuongThuc);
			int n = stmt.executeUpdate();
			return n > 0;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Loi khi xoa phuong thuc thanh toan");
			return false;
		}
	}

	public List<PhuongThucThanhToan> search(String keyword) {
		List<PhuongThucThanhToan> list = new ArrayList<PhuongThucThanhToan>();
		String sql = "SELECT * FROM PhuongThucThanhToan WHERE tenPhuongThuc LIKE ? ORDER BY maPhuongThuc";
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			String searchPattern = "%" + keyword + "%";
			stmt.setString(1, searchPattern);
			try (ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					PhuongThucThanhToan pttt = new PhuongThucThanhToan(
							rs.getString("maPhuongThuc"), 
							rs.getString("tenPhuongThuc"),
							rs.getString("moTa"));
					list.add(pttt);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public PhuongThucThanhToan getById(String ID) {
		String sql = "SELECT * FROM PhuongThucThanhToan WHERE maPhuongThuc=?";
		PhuongThucThanhToan pttt = null;
		try (Connection conn = DatabaseConnection.getInstance().getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setString(1, ID);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				pttt = new PhuongThucThanhToan(
						rs.getString("maPhuongThuc"), 
						rs.getString("tenPhuongThuc"),
						rs.getString("moTa"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pttt;
	}
}