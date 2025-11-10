package entity;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
	private ArrayList<ChiTietHoaDon> listCTHD;
	
	public HoaDon(String maHoaDon) {
		this.maHoaDon = maHoaDon;
	}
	


	public HoaDon(String maHoaDon, KhachHang khachHang, NhanVien nhanVien, DatVe datVe, LocalDateTime ngayLapHoaDon,
			Thue thue, KhuyenMai khuyenMai, PhuongThucThanhToan ptThanhToan, LocalDateTime ngayThanhToan,
			String tinhTrang, ArrayList<ChiTietHoaDon> listCTHD) {
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
		this.listCTHD = listCTHD;
	}

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
	public ArrayList<ChiTietHoaDon> getListCTHD() {
		return listCTHD;
	}

	public void setListCTHD(ArrayList<ChiTietHoaDon> listCTHD) {
		this.listCTHD = listCTHD;
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
	
	public double tinhThanhTien() {
	    if (listCTHD == null) return 0;

	    double tong = 0;
	    for (ChiTietHoaDon ct : listCTHD) {
			tong += ct.getDonGia();
		}
	    tong = tong + tong *thue.getPhanTram() - (khuyenMai.getSoTienGiam()+ khuyenMai.getPhanTram()*tong);

	    return tong;
	}
	
}
