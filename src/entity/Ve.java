package entity;

import java.time.LocalDateTime;

public class Ve {
	private String maVe;
	private double gia;
	private LichChieu lichChieu;
	private Ghe ghe;
	private LocalDateTime thoiGianDat;
	

	public Ve() {}


	public Ve(String maVe, double gia, LichChieu lichChieu, Ghe ghe, LocalDateTime thoiGianDat) {
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


	public LocalDateTime getThoiGianDat() {
		return thoiGianDat;
	}


	public void setThoiGianDat(LocalDateTime thoiGianDat) {
		this.thoiGianDat = thoiGianDat;
	}


	public String getMaVe() {
		return maVe;
	}


	

	
	

}
