package entity;

public class PhuongThucThanhToan {
	private String maPhuongThuc;
	private String tenPhuongThuc;
	private String moTa;
	public PhuongThucThanhToan() {
		// TODO Auto-generated constructor stub
	}
	
	public PhuongThucThanhToan(String maPhuongThuc) {
		super();
		this.maPhuongThuc = maPhuongThuc;
	}
	public PhuongThucThanhToan(String maPhuongThuc, String tenPhuongThuc, String moTa) {
		super();
		this.maPhuongThuc = maPhuongThuc;
		this.tenPhuongThuc = tenPhuongThuc;
		this.moTa = moTa;
	}
	public String getTenPhuongThuc() {
		return tenPhuongThuc;
	}
	public void setTenPhuongThuc(String tenPhuongThuc) {
		this.tenPhuongThuc = tenPhuongThuc;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getMaPhuongThuc() {
		return maPhuongThuc;
	}
	public void setMaPhuongThuc(String maPhuongThuc) {
		this.maPhuongThuc = maPhuongThuc;
	}
	
}
