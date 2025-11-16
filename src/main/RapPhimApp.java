package main;

import gui.DangNhapGUI;

import javax.swing.*;

/**
 * Main entry point của ứng dụng Quản lý Rạp Chiếu Phim
 * 
 * @author Hùng
 */
public class RapPhimApp {

    public static void main(String[] args) {

    	// Chạy GUI trên Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
	        // Mở form đăng nhập trước
	        DangNhapGUI loginGUI = new DangNhapGUI();
	        loginGUI.setVisible(true);
	        
        });
    }
}
