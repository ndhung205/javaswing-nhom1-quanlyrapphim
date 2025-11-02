package entity;

import java.time.LocalDateTime;

public class DatVe {
	private String maDatVe;
	private String trangThai;
	private LocalDateTime ngayDat;
	

	public DatVe(String maDatVe, String trangThai, LocalDateTime ngayDat) {
		super();
		this.maDatVe = maDatVe;
		this.trangThai = trangThai;
		this.ngayDat = ngayDat;
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
