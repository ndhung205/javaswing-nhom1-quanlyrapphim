package entity;

public class LoaiPhim {
	private String maLoaiPhim;
    private String tenLoaiPhim;
    private String moTa;

    public LoaiPhim() {}

    public LoaiPhim(String maLoaiPhim, String tenLoaiPhim, String moTa) {
        this.maLoaiPhim = maLoaiPhim;
        this.tenLoaiPhim = tenLoaiPhim;
        this.moTa = moTa;
    }

    // Getters & Setters
    public String getMaLoaiPhim() { return maLoaiPhim; }
    public void setMaLoaiPhim(String maLoaiPhim) { this.maLoaiPhim = maLoaiPhim; }
    
    public String getTenLoaiPhim() { return tenLoaiPhim; }
    public void setTenLoaiPhim(String tenLoaiPhim) { this.tenLoaiPhim = tenLoaiPhim; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return tenLoaiPhim; // Để hiển thị trong ComboBox
    }
}
