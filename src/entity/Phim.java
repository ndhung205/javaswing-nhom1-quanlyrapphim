package entity;

public class Phim {
	private String maPhim;
    private String tenPhim;
    private LoaiPhim loaiPhim;
    private String moTa;
    private int thoiLuongChieu; // phút
    private int namPhatHanh;

    public Phim() {}

    public Phim(String maPhim, String tenPhim, LoaiPhim loaiPhim, 
                String moTa, int thoiLuongChieu, int namPhatHanh) {
        this.maPhim = maPhim;
        this.tenPhim = tenPhim;
        this.loaiPhim = loaiPhim;
        this.moTa = moTa;
        this.thoiLuongChieu = thoiLuongChieu;
        this.namPhatHanh = namPhatHanh;
    }

    // Getters & Setters
    public String getMaPhim() { return maPhim; }
    public void setMaPhim(String maPhim) { this.maPhim = maPhim; }
    
    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) { this.tenPhim = tenPhim; }
    
    public LoaiPhim getLoaiPhim() { return loaiPhim; }
    public void setLoaiPhim(LoaiPhim loaiPhim) { this.loaiPhim = loaiPhim; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    
    public int getThoiLuongChieu() { return thoiLuongChieu; }
    public void setThoiLuongChieu(int thoiLuongChieu) { this.thoiLuongChieu = thoiLuongChieu; }
    
    public int getNamPhatHanh() { return namPhatHanh; }
    public void setNamPhatHanh(int namPhatHanh) { this.namPhatHanh = namPhatHanh; }

	@Override
	public String toString() {
		return "Phim [maPhim=" + maPhim + ", tenPhim=" + tenPhim + ", loaiPhim=" + loaiPhim + ", moTa=" + moTa
				+ ", thoiLuongChieu=" + thoiLuongChieu + ", namPhatHanh=" + namPhatHanh + "]";
	}
}
