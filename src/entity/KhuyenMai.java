package entity;

import java.sql.Date;

public class KhuyenMai {
	private String maKM;
	private String tenKM;
	private double phanTram;
	private double soTienGiam;
	private Date ngayBD;
	private Date ngayKT;
	private String dieuKien;
	private boolean trangThai;
	
	
	public KhuyenMai(String maKM) {
		super();
		this.maKM = maKM;
	}
	public KhuyenMai(String maKM, String tenKM, double phanTram, 
			double soTienGiam, Date ngayBD, Date ngayKT,
			String dieuKien, boolean trangThai) {
		super();
		this.maKM = maKM;
		this.tenKM = tenKM;
		this.phanTram = phanTram;
		this.soTienGiam = soTienGiam;
		this.ngayBD = ngayBD;
		this.ngayKT = ngayKT;
		this.dieuKien = dieuKien;
		this.trangThai = trangThai;
	}
	public String getTenKM() {
		return tenKM;
	}
	public void setTenKM(String tenKM) {
		this.tenKM = tenKM;
	}
	public double getPhanTram() {
		return phanTram;
	}
	public void setPhanTram(double phanTram) {
		this.phanTram = phanTram;
	}
	public double getSoTienGiam() {
		return soTienGiam;
	}
	public void setSoTienGiam(double soTienGiam) {
		this.soTienGiam = soTienGiam;
	}
	public Date getNgayBD() {
		return ngayBD;
	}
	public void setNgayBD(Date ngayBD) {
		this.ngayBD = ngayBD;
	}
	public Date getNgayKT() {
		return ngayKT;
	}
	public void setNgayKT(Date ngayKT) {
		this.ngayKT = ngayKT;
	}
	public String getDieuKien() {
		return dieuKien;
	}
	public void setDieuKien(String dieuKien) {
		this.dieuKien = dieuKien;
	}
	public boolean isTrangThai() {
		return trangThai;
	}
	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}
	public String getMaKM() {
		return maKM;
	}
	
	
}
