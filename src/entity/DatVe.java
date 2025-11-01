package entity;

import java.util.Date;

public class DatVe {
	private String maDatVe;
	private String trangThai;
	private Date ngayDat;
	
	public DatVe() {
	}
	public DatVe(String maDatVe, String trangThai, Date ngayDat) {
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
	public Date getNgayDat() {
		return ngayDat;
	}
	public void setNgayDat(Date ngayDat) {
		this.ngayDat = ngayDat;
	}
	public String getMaDatVe() {
		return maDatVe;
	}
	
	
}
