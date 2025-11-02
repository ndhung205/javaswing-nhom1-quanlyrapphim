package entity;

import java.sql.Date;

public class Ve {
	private String maVe;
	private double gia;
	private LichChieu lichChieu;
	private Ghe ghe;
	private Date thoiGianDat;
	

	public Ve() {}


	public Ve(String maVe, double gia, LichChieu lichChieu, Ghe ghe, Date thoiGianDat) {
		super();
		this.maVe = maVe;
		this.gia = gia;
		this.lichChieu = lichChieu;
		this.ghe = ghe;
		this.thoiGianDat = thoiGianDat;
	}


	public double getGia() {
		return gia;
	}


	public void setGia(double gia) {
		this.gia = gia;
	}


	public LichChieu getLichChieu() {
		return lichChieu;
	}


	public void setLichChieu(LichChieu lichChieu) {
		this.lichChieu = lichChieu;
	}


	public Ghe getGhe() {
		return ghe;
	}


	public void setGhe(Ghe ghe) {
		this.ghe = ghe;
	}


	public Date getThoiGianDat() {
		return thoiGianDat;
	}


	public void setThoiGianDat(Date thoiGianDat) {
		this.thoiGianDat = thoiGianDat;
	}


	public String getMaVe() {
		return maVe;
	}


	

	
	

}
