package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class LichChieuGUI extends JPanel{
	private JPanel pNorth;

	public LichChieuGUI() {
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		pNorth = new JPanel();
		pNorth.add(new JLabel("Danh sách Lịch chiếu"));
		add(pNorth, BorderLayout.CENTER);
		setSize(WIDTH, HEIGHT);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			LichChieuGUI lichChieuGUI = new LichChieuGUI();
			lichChieuGUI.setVisible(true);
		});
	}
}
