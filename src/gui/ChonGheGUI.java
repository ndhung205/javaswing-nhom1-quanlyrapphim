package gui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;

import dao.GheDAO;
import entity.Ghe;
import entity.LoaiGhe;
import entity.Phong;
/***
 * @author Tuan Dat
 */
@SuppressWarnings("serial")
public class ChonGheGUI extends JDialog implements ActionListener {

    // Lưu danh sách ghế
	private Phong room;
    private JButton[] danhSachGhe;
	private JButton btnXacNhan;
	private JButton btnHuy;
	private GheDAO gheDao;
	private ArrayList<Ghe> listGheChonTam, listGheChonTruoc;
	private DatVeGUI parentFrame;
   
    public ChonGheGUI(Phong p, ArrayList<Ghe> gheChonTruoc,DatVeGUI parentFrame) {
    	this.room = p;
    	this.listGheChonTruoc = (gheChonTruoc != null) ? gheChonTruoc : null;;
    	this.danhSachGhe = new JButton[room.getSoLuongGhe()];
    	this.gheDao = new GheDAO();
    	this.parentFrame = parentFrame;
    	
    	if(this.room == null) {
    		JOptionPane.showConfirmDialog(this, "Vui lòng chọn phòng");
    		dispose();
    		return;
    	}
    	
        initGUI();
        setLocationRelativeTo(null);
    }

    private void initGUI() {
        setTitle("Chọn ghế");
        setLayout(new BorderLayout());
        setSize(750, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // ----- Tiêu đề -----
        JPanel pnlTitle = new JPanel();
        JLabel lblTitle = new JLabel("SƠ ĐỒ GHẾ");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitle.setForeground(Color.BLUE);
        pnlTitle.add(lblTitle);
        add(pnlTitle, BorderLayout.NORTH);


        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BoxLayout(pnlCenter, BoxLayout.Y_AXIS));
        pnlCenter.setBackground(Color.WHITE);


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

        JPanel pnlGhe = createGhe();

        JPanel pnlLegend = new JPanel();
        pnlLegend.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlLegend.add(new JLabel("Ghi chú: "));
        pnlLegend.add(colorBox(Color.LIGHT_GRAY, 20, 20));
        pnlLegend.add(new JLabel("Ghế trống "));
        pnlLegend.add(colorBox(Color.RED, 20, 20));
        pnlLegend.add(new JLabel("  Đã đặt "));
        pnlLegend.add(colorBox(Color.GREEN, 20, 20));
        pnlLegend.add(new JLabel("  Đang chọn "));
        

        JPanel pnlGheBox = new JPanel(new BorderLayout());
        pnlGheBox.add(pnlGhe, BorderLayout.CENTER);
        pnlGheBox.add(pnlLegend, BorderLayout.SOUTH);
        
        

        JPanel pnlButton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlButton.add(btnXacNhan = new JButton("Xác nhận chọn"));
        pnlButton.add(btnHuy = new JButton("Hủy"));
        
        btnXacNhan.addActionListener(this); 
        btnHuy.addActionListener(this);
        pnlCenter.add(pnlGheBox);
       

        add(pnlCenter, BorderLayout.CENTER);
        add(pnlButton, BorderLayout.SOUTH);

        setGheTrong();
        danhDauGheDaChonTruoc();
    }

    private JPanel colorBox(Color color, int w, int h) {
        JPanel box = new JPanel();
        box.setBackground(color);
        box.setPreferredSize(new Dimension(w, h));
        box.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        return box;
    }
    private JPanel createGhe() {
        int soCot = 10; 
        int soHang = (int) Math.ceil((double) danhSachGhe.length / soCot);

        JPanel pnlGhe = new JPanel(new GridLayout(soHang, soCot, 7, 7));
        pnlGhe.setBackground(Color.WHITE);

        int gheIndex = 0;
        int rowChar = 'A'; 

        for (int hang = 0; hang < soHang; hang++) {
            for (int cot = 1; cot <= soCot; cot++) {
                if (gheIndex >= danhSachGhe.length)
                    break;

                String seatLabel = (char) rowChar + String.valueOf(cot);
                JButton ghe = new JButton(seatLabel);
                ghe.setFocusPainted(false);

                if (rowChar >= 'C' && rowChar <= 'F') {
                	if(cot >= 3 && cot <= 8) {
                		ghe.setBackground(new Color(255, 193, 7));
                        ghe.setToolTipText("VIP");
                	}
                    
                } else {
                    ghe.setBackground(Color.LIGHT_GRAY); 
                    ghe.setToolTipText("Thường");
                }

                ghe.addActionListener(this);
                danhSachGhe[gheIndex++] = ghe;
                pnlGhe.add(ghe);
            }
            rowChar++; 
        }
        return pnlGhe;
    }

    private void setGheTrong() {
        ArrayList<Ghe> listGheDat = gheDao.getAllGhe();

        for (JButton btnGhe : danhSachGhe) {
            String maGhe = btnGhe.getText();
            boolean isDat = false;
            
            for (Ghe ghe : listGheDat) {
                if (ghe.getMaGhe().equals(maGhe)) {
                    isDat = true;
                    break;
                }
            }
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
    	listGheChonTam = new ArrayList<Ghe>();
    	
    	for (JButton btn: danhSachGhe) {
			if(btn.getBackground().equals(Color.green)) {
				String tooltip = btn.getToolTipText();
				String loaiGhe = null;
				
				if(tooltip != null && tooltip.contains("VIP")) {
				    loaiGhe = "VIP";
				} else {
				    loaiGhe = "Thường";
				}

				String maLoai = loaiGhe.equals("VIP") ? "LG02" :"LG01";
				double phThu =  loaiGhe.equals("VIP") ? 20000 : 0;
				LoaiGhe lg = new LoaiGhe(maLoai, loaiGhe, phThu, loaiGhe);
				Ghe ghe = new Ghe(btn.getText(), room, lg, "Đã đặt");
				listGheChonTam.add(ghe);
			}
		}
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Object source = e.getSource();
    	if(source.equals(btnHuy)) {
    		this.dispose();
    		
    	}else if(source.equals(btnXacNhan)) {
    		setGheDaChon();
    	    if (listGheChonTam.isEmpty()) {
    	        JOptionPane.showMessageDialog(this, "Vui lòng chọn ít nhất một ghế!");
    	        return;
    	    }
    	    if (parentFrame != null) {
    	        parentFrame.getGheChonTuGUI(listGheChonTam);
    	    }
    	    dispose();
    		
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
    private void danhDauGheDaChonTruoc() {
        if (listGheChonTruoc == null || listGheChonTruoc.isEmpty()) return;

        for (JButton btn : danhSachGhe) {
            for (Ghe ghe : listGheChonTruoc) {
                if (btn.getText().equals(ghe.getMaGhe())) {
                    btn.setBackground(Color.GREEN);
                    btn.setEnabled(true); 
                }
            }
        }
    }

}
