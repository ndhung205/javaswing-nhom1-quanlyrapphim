package connectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyRapChieuPhim1;encrypt=false";
    private static final String USER = "sa";
    private static final String PASSWORD = "sapassword";

    private static DatabaseConnection instance = new DatabaseConnection();
    private Connection con;

    private DatabaseConnection() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("✅ Kết nối SQL Server thành công!");
        } catch (SQLException e) {
            System.err.println("❌ Lỗi kết nối SQL Server:");
            e.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance() {
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        if (con == null || con.isClosed()) {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("⚠ Connection bị đóng, reconnect lại...");
        }
        return con;
    }

    public synchronized void disconnect() {
        if (con != null) {
            try {
                con.close();
                System.out.println("✅ Đã ngắt kết nối SQL Server");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
