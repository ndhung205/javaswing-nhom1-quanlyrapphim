package entity;

import java.time.LocalDateTime;

public class HoaDon {
	private String maHoaDon;
	private KhachHang khachHang;
	private NhanVien nhanVien;
	private DatVe datVe;
	private LocalDateTime ngayLapHoaDon;
	private Thue thue;
	private KhuyenMai khuyenMai;
	private PhuongThucThanhToan ptThanhToan;
	private LocalDateTime ngayThanhToan;
	private String tinhTrang;
	
	public HoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	public HoaDon() {}
	

	public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVien, DatVe datVe, LocalDateTime ngayLapHoaDon,
			Thue thue, KhuyenMai khuyenMai, PhuongThucThanhToan ptThanhToan, LocalDateTime ngayThanhToan,
			String tinhTrang) {
		this.maHoaDon = maHoaDon;
		this.khachHang = khachHang;
		this.nhanVien = nhanVien;
		this.datVe = datVe;
		this.ngayLapHoaDon = ngayLapHoaDon;
		this.thue = thue;
		this.khuyenMai = khuyenMai;
		this.ptThanhToan = ptThanhToan;
		this.ngayThanhToan = ngayThanhToan;
		this.tinhTrang = tinhTrang;
	}
	public KhachHang getKhachHang() {
		return khachHang;
	}
	public void setKhachHang(KhachHang khachHang) {
		this.khachHang = khachHang;
	}
	public NhanVien getNhanVien() {
		return nhanVien;
	}
	public void setNhanVien(NhanVien nhanVien) {
		this.nhanVien = nhanVien;
	}
	public DatVe getDatVe() {
		return datVe;
	}
	public void setDatVe(DatVe datVe) {
		this.datVe = datVe;
	}
	public LocalDateTime getNgayLapHoaDon() {
		return ngayLapHoaDon;
	}
	public void setNgayLapHoaDon(LocalDateTime ngayLapHoaDon) {
		this.ngayLapHoaDon = ngayLapHoaDon;
	}
	public Thue getThue() {
		return thue;
	}
	public void setThue(Thue thue) {
		this.thue = thue;
	}
	public KhuyenMai getKhuyenMai() {
		return khuyenMai;
	}
	public void setKhuyenMai(KhuyenMai khuyenMai) {
		this.khuyenMai = khuyenMai;
	}
	public PhuongThucThanhToan getPtThanhToan() {
		return ptThanhToan;
	}
	public void setPtThanhToan(PhuongThucThanhToan ptThanhToan) {
		this.ptThanhToan = ptThanhToan;
	}
	public LocalDateTime getNgayThanhToan() {
		return ngayThanhToan;
	}
	public void setNgayThanhToan(LocalDateTime ngayThanhToan) {
		this.ngayThanhToan = ngayThanhToan;
	}
	public String getTinhTrang() {
		return tinhTrang;
	}
	public void setTinhTrang(String tinhTrang) {
		this.tinhTrang = tinhTrang;
	}
	public String getMaHoaDon() {
		return maHoaDon;
	}
	public double tinhTienThue(double tien) {
		return tien*thue.getPhanTram()/100;
	}
	public double tinhTienKhuyenMai(double tien) {
		if(khuyenMai.getPhanTram() != 0)
			return khuyenMai.getPhanTram()*tien/100;
		
		return  khuyenMai.getSoTienGiam();
	}
}
