package entity;

public class Thue {
	private String maThue;
	private String tenThue;
	private float phanTram;
	private String moTa;
	public Thue() {
		// TODO Auto-generated constructor stub
	}
	
	public Thue(String maThue) {
		super();
		this.maThue = maThue;
	}
	public Thue(String maThue, String tenThue, float phanTram, String moTa) {
		super();
		this.maThue = maThue;
		this.tenThue = tenThue;
		this.phanTram = phanTram;
		this.moTa = moTa;
	}
	public String getTenThue() {
		return tenThue;
	}
	public void setTenThue(String tenThue) {
		this.tenThue = tenThue;
	}
	public float getPhanTram() {
		return phanTram;
	}
	public void setPhanTram(float phanTram) {
		this.phanTram = phanTram;
	}
	public String getMoTa() {
		return moTa;
	}
	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	public String getMaThue() {
		return maThue;
	}
	public void setMaThue(String maThue) {
		this.maThue = maThue;
	}
	
}
