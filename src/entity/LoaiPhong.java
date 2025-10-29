package entity;

public class LoaiPhong {
	private String maLoaiPhong;
    private String tenLoaiPhong;
    private String moTa;

    public LoaiPhong() {}

    public LoaiPhong(String maLoaiPhong, String tenLoaiPhong, String moTa) {
        this.maLoaiPhong = maLoaiPhong;
        this.tenLoaiPhong = tenLoaiPhong;
        this.moTa = moTa;
    }

    public String getMaLoaiPhong() { return maLoaiPhong; }
    public void setMaLoaiPhong(String maLoaiPhong) { this.maLoaiPhong = maLoaiPhong; }
    
    public String getTenLoaiPhong() { return tenLoaiPhong; }
    public void setTenLoaiPhong(String tenLoaiPhong) { this.tenLoaiPhong = tenLoaiPhong; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return tenLoaiPhong;
    }
}
