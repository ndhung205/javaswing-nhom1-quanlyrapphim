package entity;

public class ChucVu {
	private String maChucVu;
	private String tenChucVu;
	private String moTa;

	// Constructor không tham số
	public ChucVu() {
	}

	// Constructor đầy đủ tham số
	public ChucVu(String maChucVu, String tenChucVu, String moTa) {
		setMaChucVu(maChucVu);
		setTenChucVu(tenChucVu);
		setMoTa(moTa);
	}

	public String getMaChucVu() {
		return maChucVu;
	}

	public void setMaChucVu(String maChucVu) {
		if (maChucVu == null || maChucVu.trim().isEmpty())
			throw new IllegalArgumentException("Mã chức vụ không được rỗng!");
		this.maChucVu = maChucVu;
	}

	public String getTenChucVu() {
		return tenChucVu;
	}

	public void setTenChucVu(String tenChucVu) {
		if (tenChucVu == null || tenChucVu.trim().isEmpty())
			throw new IllegalArgumentException("Tên chức vụ không được rỗng!");
		this.tenChucVu = tenChucVu;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	
	@Override
	public String toString() {
	    return tenChucVu + " (" + maChucVu + ")";
	}

}
