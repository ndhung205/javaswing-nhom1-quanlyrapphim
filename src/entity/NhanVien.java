package entity;

import java.time.LocalDate;

public class NhanVien {
	private String maNV;
	private String hoTen;
	private String sDT;
	private String email;
	private ChucVu chucVu;
	private LocalDate ngayVaoLam;

	public NhanVien() {}

	public NhanVien(String maNV, String hoTen, String sDT, String email, ChucVu chucVu, LocalDate ngayVaoLam) {
		setMaNV(maNV);
		setHoTen(hoTen);
		setsDT(sDT);
		setEmail(email);
        setChucVu(chucVu);
        setNgayVaoLam(ngayVaoLam);
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		if (maNV == null || maNV.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhân viên không được để trống");
        }
        this.maNV = maNV.trim();	
    }

	public String getHoTen() {
		return hoTen;
	}

	public void setHoTen(String hoTen) {
		if (hoTen == null || hoTen.trim().isEmpty()) {
            throw new IllegalArgumentException("Họ tên không được để trống");
        }
        this.hoTen = hoTen.trim();
	}

	public String getsDT() {
		return sDT;
	}

	public void setsDT(String sDT) {
		if ( sDT != null && ! sDT.trim().isEmpty()) {
            if (! sDT.matches("^(\\+84|0)(3|5|7|8|9)\\d{8}$")) {
                throw new IllegalArgumentException("Số điện thoại không hợp lệ");
            }
        }
        this. sDT =  sDT;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		 if (email != null && !email.trim().isEmpty()) {
	            if (!email.matches("^[\\w-.]+@[\\w-]+\\.[\\w-.]+$")) {
	                throw new IllegalArgumentException("Email không hợp lệ");
	            }
	        }
	        this.email = email;
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		if (chucVu == null ) {
            throw new IllegalArgumentException("Mã chức vụ không được để trống");
        }
        this.chucVu = chucVu;
	}

	public LocalDate getNgayVaoLam() {
		return ngayVaoLam;
	}

	public void setNgayVaoLam(LocalDate ngayVaoLam) {
		if (ngayVaoLam != null && ngayVaoLam.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException("Ngày vào làm không được lớn hơn ngày hiện tại");
        }
        this.ngayVaoLam = ngayVaoLam;
	}
	@Override
    public String toString() {
        return maNV +";" + hoTen + ";" + sDT + ";" + email + ";" + chucVu + ";" + ngayVaoLam;
                
    }
}
