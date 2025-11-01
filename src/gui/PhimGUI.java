package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PhimGUI extends JPanel{
	
	private JPanel pNorth;

	public PhimGUI() {
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		pNorth = new JPanel();
		pNorth.add(new JLabel("Danh sách phim"));
		add(pNorth, BorderLayout.CENTER);
		setSize(WIDTH, HEIGHT);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			PhimGUI phimGUI = new PhimGUI();
			phimGUI.setVisible(true);
		});
	}
}
