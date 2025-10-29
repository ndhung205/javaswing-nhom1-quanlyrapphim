package main;

import connectDB.DatabaseConnection;
import gui.DangNhapGUI_Test;

import javax.swing.*;
import java.sql.SQLException;

/**
 * Main entry point của ứng dụng Quản lý Rạp Chiếu Phim
 * 
 * @author Hùng
 */
public class RapPhimApp {

    public static void main(String[] args) {

        // Test database connection trước khi chạy app
        try {
            var conn = DatabaseConnection.getInstance().getConnection();

            if (conn != null && !conn.isClosed()) {
                System.out.println("===========================================");
                System.out.println("   QUẢN LÝ RẠP CHIẾU PHIM - v1.0");
                System.out.println("===========================================");
                System.out.println("✅ Database: Connected");
                System.out.println("📌 Khởi động ứng dụng...");
                System.out.println("===========================================\n");

                // Chạy GUI trên Event Dispatch Thread
                SwingUtilities.invokeLater(() -> {
                    // Mở form đăng nhập trước
                    DangNhapGUI_Test loginGUI = new DangNhapGUI_Test();
                    loginGUI.setVisible(true);
                });
            }

        } catch (SQLException e) {
            System.err.println("❌ Không thể kết nối database!");
            System.err.println("Vui lòng kiểm tra:");
            System.err.println("1. SQL Server đã chạy chưa");
            System.err.println("2. Database 'QuanLyRapChieuPhim' đã tạo chưa");
            System.err.println("3. Username/Password trong DatabaseConnection.java");
            System.err.println("\nLỗi chi tiết:");
            e.printStackTrace();
            System.exit(1);
        }
    }
}
