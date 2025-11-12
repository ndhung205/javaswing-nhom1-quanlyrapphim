package entity;

import java.time.LocalDateTime;

public class Ve {
	private String maVe;
	private double gia;
	private LichChieu lichChieu;
	private Ghe ghe;
	private DatVe datVe;
	private LocalDateTime thoiGianDat;
	

	public Ve() {}
	public Ve(String maVe) {this.maVe = maVe;}

	public Ve(String maVe, double gia, LichChieu lichChieu, Ghe ghe, DatVe datVe, LocalDateTime thoiGianDat) {
		super();
		this.maVe = maVe;
		this.gia = gia;
		this.lichChieu = lichChieu;
		this.ghe = ghe;
		this.datVe = datVe;
		this.thoiGianDat = thoiGianDat;
	}
	public Ve(String maVe, double gia, LichChieu lichChieu, Ghe ghe, LocalDateTime thoiGianDat) {
		super();
		this.maVe = maVe;
		this.gia = gia;
		this.lichChieu = lichChieu;
		this.ghe = ghe;
		this.thoiGianDat = thoiGianDat;
	}


	public DatVe getDatVe() {
		return datVe;
	}
	public void setDatVe(DatVe datVe) {
		this.datVe = datVe;
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
