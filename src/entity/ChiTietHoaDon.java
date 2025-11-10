package entity;

public class ChiTietHoaDon {
	private HoaDon hoaDon;
	private Ve ve;
	private double donGia;
	public ChiTietHoaDon(HoaDon hoaDon, Ve ve, double donGia) {
		super();
		this.hoaDon = hoaDon;
		this.ve = ve;
		this.donGia = donGia;
	}
	public HoaDon getHoaDon() {
		return hoaDon;
	}
	public void setHoaDon(HoaDon hoaDon) {
		this.hoaDon = hoaDon;
	}
	public Ve getVe() {
		return ve;
	}
	public void setVe(Ve ve) {
		this.ve = ve;
	}
	public double getDonGia() {
		return donGia;
	}
	public void setDonGia(double donGia) {
		this.donGia = donGia;
	}

	
}
