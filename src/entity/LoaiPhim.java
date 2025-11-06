package entity;

public class LoaiPhim {
	private String maLoaiPhim;
    private String tenLoaiPhim;

    public LoaiPhim() {}

    public LoaiPhim(String maLoaiPhim, String tenLoaiPhim) {
        this.maLoaiPhim = maLoaiPhim;
        this.tenLoaiPhim = tenLoaiPhim;
    }

    // Getters & Setters
    public String getMaLoaiPhim() { return maLoaiPhim; }
    public void setMaLoaiPhim(String maLoaiPhim) { this.maLoaiPhim = maLoaiPhim; }
    
    public String getTenLoaiPhim() { return tenLoaiPhim; }
    public void setTenLoaiPhim(String tenLoaiPhim) { this.tenLoaiPhim = tenLoaiPhim; }

	@Override
	public String toString() {
		return tenLoaiPhim + " (" + maLoaiPhim + ")";
	}
}
