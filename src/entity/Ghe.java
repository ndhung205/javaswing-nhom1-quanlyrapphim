package entity;

public class Ghe {
	private String maGhe;
    private Phong phong;
    private LoaiGhe loaiGhe;
    private String trangThai; // "Trống", "Đã đặt", "Đang sửa"

    public Ghe() {}

    public Ghe(String maGhe, Phong phong, LoaiGhe loaiGhe, String trangThai) {
        this.maGhe = maGhe;
        this.phong = phong;
        this.loaiGhe = loaiGhe;
        this.trangThai = trangThai;
    }

    public String getMaGhe() { return maGhe; }
    public void setMaGhe(String maGhe) { this.maGhe = maGhe; }
    
    public Phong getPhong() { return phong; }
    public void setPhong(Phong phong) { this.phong = phong; }
    
    public LoaiGhe getLoaiGhe() { return loaiGhe; }
    public void setLoaiGhe(LoaiGhe loaiGhe) { this.loaiGhe = loaiGhe; }
    
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }

    @Override
    public String toString() {
        return maGhe;
    }
}
