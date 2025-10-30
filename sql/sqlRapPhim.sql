CREATE DATABASE QuanLyRapChieuPhim

USE QuanLyRapChieuPhim

-- ===============================
-- QUẢN LÝ RẠP CHIẾU PHIM - SQL SERVER (CHUẨN HÓA CAO)
-- ===============================

-- ========== BẢNG TRA CỨU (LOOKUP TABLES) ==========

-- Bảng Loại phòng
CREATE TABLE LoaiPhong (
    maLoaiPhong NVARCHAR(50) PRIMARY KEY,
    tenLoaiPhong NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);

-- Bảng Loại phim
CREATE TABLE LoaiPhim (
    maLoaiPhim NVARCHAR(50) PRIMARY KEY,
    tenLoaiPhim NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);

-- Bảng Loại ghế
CREATE TABLE LoaiGhe (
    maLoaiGhe NVARCHAR(50) PRIMARY KEY,
    tenLoaiGhe NVARCHAR(100) NOT NULL,
    phuThu DECIMAL(10,2) DEFAULT 0, -- Phụ thu so với giá cơ bản
    moTa NVARCHAR(255)
);

-- Bảng Chức vụ
CREATE TABLE ChucVu (
    maChucVu NVARCHAR(50) PRIMARY KEY,
    tenChucVu NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);

-- Bảng Phương thức thanh toán
CREATE TABLE PhuongThucThanhToan (
    maPhuongThuc NVARCHAR(50) PRIMARY KEY,
    tenPhuongThuc NVARCHAR(100) NOT NULL,
    moTa NVARCHAR(255)
);

-- Bảng Thuế
CREATE TABLE Thue (
    maThue NVARCHAR(50) PRIMARY KEY,
    tenThue NVARCHAR(100) NOT NULL,
    phanTram DECIMAL(5,2) NOT NULL, -- VD: 8.00 = 8%
    moTa NVARCHAR(255)
);

-- Bảng Khuyến mãi
CREATE TABLE KhuyenMai (
    maKhuyenMai NVARCHAR(50) PRIMARY KEY,
    tenKhuyenMai NVARCHAR(200) NOT NULL,
    phanTramGiam DECIMAL(5,2) DEFAULT 0, -- Giảm theo %
    soTienGiam DECIMAL(10,2) DEFAULT 0,  -- Giảm theo số tiền cố định
    ngayBatDau DATE NOT NULL,
    ngayKetThuc DATE NOT NULL,
    dieuKien NVARCHAR(MAX), -- Điều kiện áp dụng
    trangThai BIT NOT NULL DEFAULT 1
);

-- ========== BẢNG CHÍNH ==========

-- Bảng Phòng chiếu
CREATE TABLE Phong (
    maPhong NVARCHAR(50) PRIMARY KEY,
    tenPhong NVARCHAR(100) NOT NULL,
    soLuongGhe INT NOT NULL,
    maLoaiPhong NVARCHAR(50) NOT NULL,
    trangThai BIT NOT NULL DEFAULT 1,
    CONSTRAINT FK_Phong_LoaiPhong FOREIGN KEY (maLoaiPhong)
        REFERENCES LoaiPhong(maLoaiPhong)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Phim
CREATE TABLE Phim (
    maPhim NVARCHAR(50) PRIMARY KEY,
    tenPhim NVARCHAR(200) NOT NULL,
    maLoaiPhim NVARCHAR(50) NOT NULL,
    moTa NVARCHAR(MAX),
    thoiLuongChieu INT NOT NULL, -- phút
    namPhatHanh INT,
    CONSTRAINT FK_Phim_LoaiPhim FOREIGN KEY (maLoaiPhim)
        REFERENCES LoaiPhim(maLoaiPhim)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Lịch chiếu
CREATE TABLE LichChieu (
    maLichChieu NVARCHAR(50) PRIMARY KEY,
    maPhim NVARCHAR(50) NOT NULL,
    maPhong NVARCHAR(50) NOT NULL,
    ngayChieu DATE NOT NULL,
    gioBatDau DATETIME NOT NULL,
    gioKetThuc DATETIME NOT NULL,
    CONSTRAINT FK_LichChieu_Phim FOREIGN KEY (maPhim)
        REFERENCES Phim(maPhim)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_LichChieu_Phong FOREIGN KEY (maPhong)
        REFERENCES Phong(maPhong)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Ghế
CREATE TABLE Ghe (
    maGhe NVARCHAR(50) PRIMARY KEY,
    maPhong NVARCHAR(50) NOT NULL,
    maLoaiGhe NVARCHAR(50) NOT NULL,
    trangThai NVARCHAR(50),
    CONSTRAINT FK_Ghe_Phong FOREIGN KEY (maPhong)
        REFERENCES Phong(maPhong)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Ghe_LoaiGhe FOREIGN KEY (maLoaiGhe)
        REFERENCES LoaiGhe(maLoaiGhe)
        ON DELETE NO ACTION ON UPDATE CASCADE,
);

-- Bảng Vé
CREATE TABLE Ve (
    maVe NVARCHAR(50) PRIMARY KEY,
    maLichChieu NVARCHAR(50) NOT NULL,
    maGhe NVARCHAR(50) NOT NULL,
    gia DECIMAL(10,2) NOT NULL, -- Giá = GiaVeCoBan + PhuThuGhe
    trangThai NVARCHAR(50) NOT NULL DEFAULT N'Trống',
    thoiGianDat DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Ve_LichChieu FOREIGN KEY (maLichChieu)
        REFERENCES LichChieu(maLichChieu)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Ve_Ghe FOREIGN KEY (maGhe)
        REFERENCES Ghe(maGhe)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT UQ_Ve_LichChieu_Ghe UNIQUE (maLichChieu, maGhe)
);

-- Bảng Khách hàng
CREATE TABLE KhachHang (
    maKhachHang NVARCHAR(50) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL,
    soDienThoai NVARCHAR(15),
);

-- Bảng Nhân viên
CREATE TABLE NhanVien (
    maNhanVien NVARCHAR(50) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL,
    soDienThoai NVARCHAR(15),
    email NVARCHAR(100),
    maChucVu NVARCHAR(50) NOT NULL,
    ngayVaoLam DATE,
    CONSTRAINT FK_NhanVien_ChucVu FOREIGN KEY (maChucVu)
        REFERENCES ChucVu(maChucVu)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Tài khoản
CREATE TABLE TaiKhoan (
    maTaiKhoan NVARCHAR(50) PRIMARY KEY,
    maNhanVien NVARCHAR(50) NOT NULL UNIQUE,
    tenTaiKhoan NVARCHAR(100) NOT NULL UNIQUE,
    matKhau NVARCHAR(255) NOT NULL,
    vaiTro NVARCHAR(50) NOT NULL CHECK (vaiTro IN (N'Admin', N'NhanVien', N'QuanLy')) DEFAULT N'NhanVien',
    trangThai BIT NOT NULL DEFAULT 1,
    CONSTRAINT FK_TaiKhoan_NhanVien FOREIGN KEY (maNhanVien)
        REFERENCES NhanVien(maNhanVien)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Bảng Đặt vé
CREATE TABLE DatVe (
    maDatVe NVARCHAR(50) PRIMARY KEY,
    maKhachHang NVARCHAR(50) NOT NULL,
    ngayDat DATETIME NOT NULL DEFAULT GETDATE(),
    trangThai NVARCHAR(50) NOT NULL DEFAULT N'Chờ xác nhận', -- Chờ xác nhận, Đã xác nhận, Đã hủy
    ghiChu NVARCHAR(MAX),
    CONSTRAINT FK_DatVe_KhachHang FOREIGN KEY (maKhachHang)
        REFERENCES KhachHang(maKhachHang)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Chi tiết đặt vé
CREATE TABLE ChiTietDatVe (
    maDatVe NVARCHAR(50) NOT NULL,
    maVe NVARCHAR(50) NOT NULL,
    CONSTRAINT PK_ChiTietDatVe PRIMARY KEY (maDatVe, maVe),
    CONSTRAINT FK_ChiTietDatVe_DatVe FOREIGN KEY (maDatVe)
        REFERENCES DatVe(maDatVe)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_ChiTietDatVe_Ve FOREIGN KEY (maVe)
        REFERENCES Ve(maVe)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Hóa đơn
CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(50) PRIMARY KEY,
    maKhachHang NVARCHAR(50),
    maNhanVien NVARCHAR(50) NOT NULL,
    maDatVe NVARCHAR(50), -- Liên kết với đặt vé (nếu có)
    ngayLapHoaDon DATETIME NOT NULL DEFAULT GETDATE(),
    maThue NVARCHAR(50),
    maKhuyenMai NVARCHAR(50),
    maPhuongThuc NVARCHAR(50),
    ngayThanhToan DATETIME NULL,
    tinhTrang NVARCHAR(100) NOT NULL DEFAULT N'Chưa thanh toán',
    CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY (maKhachHang)
        REFERENCES KhachHang(maKhachHang)
        ON DELETE SET NULL ON UPDATE CASCADE,
    CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY (maNhanVien)
        REFERENCES NhanVien(maNhanVien)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_HoaDon_DatVe FOREIGN KEY (maDatVe)
        REFERENCES DatVe(maDatVe)
        ON DELETE NO ACTION ON UPDATE NO ACTION, 
    CONSTRAINT FK_HoaDon_Thue FOREIGN KEY (maThue)
        REFERENCES Thue(maThue)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_HoaDon_KhuyenMai FOREIGN KEY (maKhuyenMai)
        REFERENCES KhuyenMai(maKhuyenMai)
        ON DELETE NO ACTION ON UPDATE CASCADE,
    CONSTRAINT FK_HoaDon_PhuongThuc FOREIGN KEY (maPhuongThuc)
        REFERENCES PhuongThucThanhToan(maPhuongThuc)
        ON DELETE NO ACTION ON UPDATE CASCADE
);

-- Bảng Chi tiết hóa đơn
CREATE TABLE ChiTietHoaDon (
    maHoaDon NVARCHAR(50) NOT NULL,
    maVe NVARCHAR(50) NOT NULL,
    donGia DECIMAL(10,2) NOT NULL,
	soLuong INT NOT NULL,
    CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (maHoaDon, maVe),
    CONSTRAINT FK_CTHD_HoaDon FOREIGN KEY (maHoaDon)
        REFERENCES HoaDon(maHoaDon)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_CTHD_Ve FOREIGN KEY (maVe)
        REFERENCES Ve(maVe)
        ON DELETE NO ACTION ON UPDATE CASCADE
);


