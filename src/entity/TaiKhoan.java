package entity;

public class TaiKhoan {
    private String maTK;
    private NhanVien nhanVien; 
    private String tenTK;
    private String matKhau;
    private String vaiTro;
    private boolean trangThai;

    public TaiKhoan(String maTK, NhanVien nhanVien, String tenTK, String matKhau, String vaiTro, boolean trangThai) {
        setMaTK(maTK);
        setNhanVien(nhanVien);
        setTenTK(tenTK);
        setMatKhau(matKhau);
        setVaiTro(vaiTro);
        setTrangThai(trangThai);
    }

    public String getMaTK() {
        return maTK;
    }

    public void setMaTK(String maTK) {
        if (maTK == null || maTK.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã tài khoản không được rỗng");
        }
        this.maTK = maTK;
    }

    public NhanVien getNhanVien() { 
        return nhanVien;
    }

    public void setNhanVien(NhanVien nhanVien) { 
        if (nhanVien == null) {
            throw new IllegalArgumentException("Nhân viên không được null");
        }
        this.nhanVien = nhanVien;
    }

    public String getTenTK() {
        return tenTK;
    }

    public void setTenTK(String tenTK) {
        if (tenTK == null || tenTK.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên tài khoản không được rỗng");
        }
        this.tenTK = tenTK;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        if (matKhau == null || matKhau.trim().isEmpty()) {
            throw new IllegalArgumentException("Mật khẩu không được rỗng");
        }
        this.matKhau = matKhau;
    }

    public String getVaiTro() {
        return vaiTro;
    }

    public void setVaiTro(String vaiTro) {
        if (vaiTro == null || vaiTro.trim().isEmpty()) {
            throw new IllegalArgumentException("Vai trò không được rỗng");
        }
        if (!(vaiTro.equals("Admin") || vaiTro.equals("NhanVien") || vaiTro.equals("QuanLy"))) {
            throw new IllegalArgumentException("Vai trò phải là: Admin, NhanVien hoặc QuanLy");
        }
        this.vaiTro = vaiTro;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return maTK + ";" 
                + (nhanVien != null ? nhanVien.getMaNV() : "") + ";"  
                + tenTK + ";" + matKhau + ";" + vaiTro + ";" + trangThai;
    }
}
