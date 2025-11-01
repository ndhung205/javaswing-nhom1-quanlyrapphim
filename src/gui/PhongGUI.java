package gui;

import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class PhongGUI extends JPanel{
	private JPanel pNorth;

	public PhongGUI() {
		initComponents();
	}
	
	public void initComponents() {
		setLayout(new BorderLayout());
		pNorth = new JPanel();
		pNorth.add(new JLabel("Danh sách phòng"));
		add(pNorth, BorderLayout.CENTER);
		setSize(WIDTH, HEIGHT);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(()-> {
			PhongGUI phongGUI = new PhongGUI();
			phongGUI.setVisible(true);
		});
	}
}
