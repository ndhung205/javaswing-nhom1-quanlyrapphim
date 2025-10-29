package entity;

public class Phong {
	private String maPhong;
    private String tenPhong;
    private int soLuongGhe;
    private LoaiPhong loaiPhong;
    private boolean trangThai; // true = hoạt động, false = bảo trì

    public Phong() {}

    public Phong(String maPhong, String tenPhong, int soLuongGhe, 
                 LoaiPhong loaiPhong, boolean trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.soLuongGhe = soLuongGhe;
        this.loaiPhong = loaiPhong;
        this.trangThai = trangThai;
    }

    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    
    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    
    public int getSoLuongGhe() { return soLuongGhe; }
    public void setSoLuongGhe(int soLuongGhe) { this.soLuongGhe = soLuongGhe; }
    
    public LoaiPhong getLoaiPhong() { return loaiPhong; }
    public void setLoaiPhong(LoaiPhong loaiPhong) { this.loaiPhong = loaiPhong; }
    
    public boolean isTrangThai() { return trangThai; }
    public void setTrangThai(boolean trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return tenPhong;
    }
}
