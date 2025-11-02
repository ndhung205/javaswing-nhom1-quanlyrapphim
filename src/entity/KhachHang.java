package entity;
import java.util.regex.Pattern;

public class KhachHang {
	 private String maKH;
	    private String tenKH;
	    private String soDienThoai;

	    public KhachHang(String maKH, String tenKH, String soDienThoai) {
	        setMaKH(maKH);
	        setTenKH(tenKH);
	        setSoDienThoai(soDienThoai);
	    }

	    public String getMaKH() {
	        return maKH;
	    }

	    public void setMaKH(String maKH) {
	        if (maKH == null || maKH.trim().isEmpty()) {
	            throw new IllegalArgumentException("Mã khách hàng không được rỗng");
	        }
	        this.maKH = maKH;
	    }

	    public String getTenKH() {
	        return tenKH;
	    }

	    public void setTenKH(String tenKH) {
	        if (tenKH == null || tenKH.trim().isEmpty()) {
	            throw new IllegalArgumentException("Tên khách hàng không được rỗng");
	        }
	        this.tenKH = tenKH;
	    }

	    public String getSoDienThoai() {
	        return soDienThoai;
	    }

	    public void setSoDienThoai(String soDienThoai) {
	        if (soDienThoai == null || soDienThoai.trim().isEmpty()) {
	            throw new IllegalArgumentException("Số điện thoại không được rỗng");
	        }
	        // Regex kiểm tra số điện thoại Việt Nam (VD: 090..., +8490..., 088...)
	        if (!Pattern.matches("^(\\+84|0)(3|5|7|8|9)\\d{8}$", soDienThoai)) {
	            throw new IllegalArgumentException("Số điện thoại không hợp lệ (phải theo định dạng Việt Nam)");
	        }
	        this.soDienThoai = soDienThoai;
	    }

	    @Override
	    public String toString() {
	        return maKH + ";" + tenKH + ";" + soDienThoai;
	    }
}
