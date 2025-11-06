package entity;

import java.time.LocalDateTime;

public class DatVe {
	private String maDatVe;
	private String trangThai;
	private LocalDateTime ngayDat;
	private KhachHang khachHang;
	

	public DatVe(String maDatVe, String trangThai, LocalDateTime ngayDat, KhachHang khachHang) {
		super();
		this.maDatVe = maDatVe;
		this.trangThai = trangThai;
		this.ngayDat = ngayDat;
		this.khachHang = khachHang;
	}
	
	
	public KhachHang getKhachHang() {
		return khachHang;
	}


	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}


	public String getTrangThai() {
		return trangThai;
	}
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}
	public LocalDateTime getNgayDat() {
		return ngayDat;
	}
	public void setNgayDat(LocalDateTime ngayDat) {
		this.ngayDat = ngayDat;
	}
	public String getMaDatVe() {
		return maDatVe;
	}
	
	
}
