package entity;

public class LoaiGhe {
	private String maLoaiGhe;
    private String tenLoaiGhe;
    private double phuThu;
    private String moTa;

    public LoaiGhe() {}

    public LoaiGhe(String maLoaiGhe, String tenLoaiGhe, double phuThu, String moTa) {
        this.maLoaiGhe = maLoaiGhe;
        this.tenLoaiGhe = tenLoaiGhe;
        this.phuThu = phuThu;
        this.moTa = moTa;
    }

    public String getMaLoaiGhe() { return maLoaiGhe; }
    public void setMaLoaiGhe(String maLoaiGhe) { this.maLoaiGhe = maLoaiGhe; }
    
    public String getTenLoaiGhe() { return tenLoaiGhe; }
    public void setTenLoaiGhe(String tenLoaiGhe) { this.tenLoaiGhe = tenLoaiGhe; }
    
    public double getPhuThu() { return phuThu; }
    public void setPhuThu(double phuThu) { this.phuThu = phuThu; }
    
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    @Override
    public String toString() {
        return tenLoaiGhe + " (" + maLoaiGhe + ") "  + phuThu; 
    }
}
