package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import dao.GheDAO;
import dao.PhongDAO;
import entity.Ghe;
import entity.Phong;
/***
 * @author Tuan Dat
 */
public class ChonGheGUI extends JFrame implements ActionListener {

    // Lưu danh sách ghế
	private Phong room;
    private JButton[] danhSachGhe;
	private JButton btnXacNhan;
	private JButton btnHuy;
	private GheDAO gheDao;
	private PhongDAO phong;
	private ArrayList<String> maGheChon;
	private DatVeGUI parentFrame;
   
    public ChonGheGUI(String maPhong,DatVeGUI parentFrame) {
    	this.phong = new PhongDAO();
    	this.room = phong.findPhongByMa(maPhong);
    	this.danhSachGhe = new JButton[room.getSoLuongGhe()];
    	this.gheDao = new GheDAO();
    	this.parentFrame = parentFrame;
    	
    	if(this.room == null) {
    		JOptionPane.showConfirmDialog(this, "Vui lòng chọn phòng");
    		return;
    	}
    	
        initGUI();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setTitle("Chọn ghế");
        setLayout(new BorderLayout());
        setSize(750, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // ----- Tiêu đề -----
        JPanel pnlTitle = new JPanel();
        JLabel lblTitle = new JLabel("SƠ ĐỒ GHẾ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.BLUE);
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);

        // ----- Khu vực trung tâm -----
        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBackground(Color.WHITE);

        // Màn hình rạp 
        ImageIcon manHinh = new ImageIcon(new ImageIcon("img/man_hinh.png").getImage().getScaledInstance(300, 50, java.awt.Image.SCALE_SMOOTH));
        JLabel lblScreen = new JLabel(manHinh, SwingConstants.CENTER);
        lblScreen.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel pnlScreen = colorBox(Color.BLACK, 600, 5);
        
        pnlScreen.setLayout(new BorderLayout());
        pnlScreen.add(lblScreen, BorderLayout.CENTER);
        pnlScreen.setAlignmentX(Component.CENTER_ALIGNMENT);
        pnlCenter.add(Box.createVerticalStrut(20));
        pnlCenter.add(pnlScreen);
        pnlCenter.add(Box.createVerticalStrut(20));

        // ----- Sơ đồ ghế -----
        JPanel pnlGhe = createGhe();

        // ----- Ghi chú màu -----
        JPanel pnlLegend = new JPanel();
        pnlLegend.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlLegend.add(new JLabel("Ghi chú: "));
        pnlLegend.add(colorBox(Color.LIGHT_GRAY, 20, 20));
        pnlLegend.add(new JLabel("Ghế trống "));
        pnlLegend.add(colorBox(Color.RED, 20, 20));
        pnlLegend.add(new JLabel("  Đã đặt "));
        pnlLegend.add(colorBox(Color.GREEN, 20, 20));
        pnlLegend.add(new JLabel("  Đang chọn "));
        

        // Gộp ghế + ghi chú
        JPanel pnlGheBox = new JPanel(new BorderLayout());
        pnlGheBox.add(pnlGhe, BorderLayout.CENTER);
        pnlGheBox.add(pnlLegend, BorderLayout.SOUTH);
        
        
        // Nut 
        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButton.add(btnXacNhan = new JButton("Xác nhận chọn"));
        pnlButton.add(btnHuy = new JButton("Hủy"));
        
        btnXacNhan.addActionListener(this); 
        btnHuy.addActionListener(this);
        pnlCenter.add(pnlGheBox);
       

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

//        setVisible(true);
    }

    private JPanel colorBox(Color color, int w, int h) {
        JPanel box = new JPanel();
        box.setBackground(color);
        box.setPreferredSize(new Dimension(w, h));
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return box;
    }
    private JPanel createGhe() {
    	int soHang = (int) Math.ceil((double) danhSachGhe.length / 10);
        JPanel pnlGhe = new JPanel(new GridLayout(soHang, 10, 7, 7));
        pnlGhe.setBackground(Color.WHITE);

        int rowChar = 64; 
        for (int i = 0; i < danhSachGhe.length; i++) {
            if (i % 10 == 0) {
                rowChar += 1;
            }
            int seatNumber = (i % 10) + 1;
            String seatLabel = String.valueOf((char) rowChar) + seatNumber;

            JButton ghe = new JButton(seatLabel);
            ghe.setBackground(Color.LIGHT_GRAY);
            ghe.setFocusPainted(false);
            ghe.addActionListener(this);

            danhSachGhe[i] = ghe;
            pnlGhe.add(ghe);
        }
        
        setGheTrong();
        return pnlGhe;
    }
    private void setGheTrong() {
        ArrayList<Ghe> listGheDat = gheDao.getAllGhe();

        for (JButton btnGhe : danhSachGhe) {
            String maGhe = btnGhe.getText();
            boolean isDat = false;
            
            // kiem tra ghe duoc dat hay chua
            for (Ghe ghe : listGheDat) {
                if (ghe.getMaGhe().equals(maGhe)) {
                    isDat = true;
                    break;
                }
            }
            // set mau
            if (!isDat) {
                btnGhe.setBackground(Color.lightGray); 
                btnGhe.setEnabled(true);
            } else {
                btnGhe.setBackground(Color.RED); 
                btnGhe.setEnabled(false);
            }
        }
    }

    private void setGheDaChon() {
    	maGheChon = new ArrayList<String>();
    	
    	int i =0 ;
    	for (JButton jButton : danhSachGhe) {
			if(jButton.getBackground() == Color.green) {
				maGheChon.add(jButton.getText());
			}
		}
    }
    
    public ArrayList<String> getGheDaChon() {
		return maGheChon;
	}
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if(source.equals(btnHuy)) {
    		this.dispose();
    		
    	}else if(source.equals(btnXacNhan)) {
    		setGheDaChon();
    		if(parentFrame != null) {
    			parentFrame.showGhe(maGheChon);
    		}
    		this.dispose();
    		
    	}else if(source instanceof JButton) {
    		JButton ghe = (JButton) e.getSource();

    		if (ghe == btnHuy || ghe == btnXacNhan) return;
    		
    		if (ghe.getBackground().equals(Color.GREEN)) {
    		    ghe.setBackground(Color.LIGHT_GRAY);
    		} else if (ghe.getBackground().equals(Color.LIGHT_GRAY)) {
    		    ghe.setBackground(Color.GREEN);
    		} else if (ghe.getBackground().equals(Color.RED)) {
    		    JOptionPane.showMessageDialog(this, "Ghế " + ghe.getText() + " đã được đặt!");
    		}
    	}
        
    }
}
