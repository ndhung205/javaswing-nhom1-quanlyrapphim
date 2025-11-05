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
	path NVARCHAR(200),
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


<<<<<<< HEAD
USE QuanLyRapChieuPhim;
GO

-- ===============================
-- 1️⃣ BẢNG LOẠI / TRA CỨU
-- ===============================

-- Loại phòng
INSERT INTO LoaiPhong VALUES
('LP01', N'2D Standard', N'Phòng chiếu 2D tiêu chuẩn'),
('LP02', N'3D Standard', N'Phòng chiếu 3D hiện đại'),
('LP03', N'4DX', N'Ghế chuyển động, hiệu ứng đặc biệt'),
('LP04', N'IMAX', N'Màn hình cong kích thước lớn'),
('LP05', N'VIP', N'Phòng cao cấp với ghế sang trọng');

-- Loại phim
INSERT INTO LoaiPhim VALUES
('P01', N'Hành động', N'Nhiều pha rượt đuổi, đánh nhau'),
('P02', N'Tình cảm', N'Lãng mạn, cảm xúc'),
('P03', N'Hài', N'Giải trí, gây cười'),
('P04', N'Kinh dị', N'Gây sợ hãi, hồi hộp'),
('P05', N'Hoạt hình', N'Dành cho trẻ em');

-- Loại ghế
INSERT INTO LoaiGhe VALUES
('G01', N'Thường', 0, N'Ghế phổ thông'),
('G02', N'VIP', 30000, N'Ghế cao cấp'),
('G03', N'Đôi', 50000, N'Ghế sweetbox cho 2 người'),
('G04', N'4DX', 70000, N'Ghế chuyển động'),
('G05', N'IMAX', 60000, N'Ghế cho phòng IMAX');

-- Chức vụ
INSERT INTO ChucVu VALUES
('CV01', N'Quản lý', N'Giám sát toàn rạp'),
('CV02', N'Nhân viên bán vé', N'Bán và quản lý vé'),
('CV03', N'Nhân viên phục vụ', N'Hỗ trợ khách hàng'),
('CV04', N'Nhân viên kỹ thuật', N'Vận hành thiết bị chiếu'),
('CV05', N'Thu ngân', N'Thanh toán và lập hóa đơn');

-- Phương thức thanh toán
INSERT INTO PhuongThucThanhToan VALUES
('TT01', N'Tiền mặt', N'Thanh toán trực tiếp'),
('TT02', N'Chuyển khoản', N'Qua tài khoản ngân hàng'),
('TT03', N'Thẻ tín dụng', N'Sử dụng Visa/MasterCard'),
('TT04', N'Momo', N'Ví điện tử Momo'),
('TT05', N'ZaloPay', N'Thanh toán online');

-- Thuế
INSERT INTO Thue VALUES
('TH01', N'Thuế VAT 5%', 5.00, N'Áp dụng cho phim phổ thông'),
('TH02', N'Thuế VAT 8%', 8.00, N'Áp dụng cho phim 3D/4DX'),
('TH03', N'Thuế VAT 10%', 10.00, N'Áp dụng cho vé cao cấp'),
('TH04', N'Thuế ưu đãi', 2.00, N'Chương trình giảm đặc biệt'),
('TH05', N'Thuế dịch vụ', 3.00, N'Dịch vụ ngoài vé');

-- Khuyến mãi
INSERT INTO KhuyenMai VALUES
('KM01', N'Giảm 10% cuối tuần', 10, 0, '2025-10-01', '2025-12-31', N'Áp dụng thứ 7, CN', 1),
('KM02', N'Combo vé + nước', 0, 30000, '2025-09-01', '2025-12-31', N'Mua vé kèm nước', 1),
('KM03', N'Giảm sinh viên', 15, 0, '2025-01-01', '2025-12-31', N'Xuất trình thẻ SV', 1),
('KM04', N'Member VIP', 20, 0, '2025-05-01', '2025-12-31', N'Khách hàng thân thiết', 1),
('KM05', N'Morning Sale', 0, 20000, '2025-06-01', '2025-12-31', N'Suất chiếu trước 10h sáng', 1);\

INSERT INTO Phim VALUES
('PM01', N'Avengers: Endgame', 'P01', N'Siêu anh hùng Marvel', 180, 2019),
('PM02', N'Titanic', 'P02', N'Chuyện tình kinh điển', 195, 1997),
('PM03', N'Minions 2', 'P05', N'Hoạt hình vui nhộn', 90, 2022),
('PM04', N'IT Chapter 2', 'P04', N'Kinh dị, máu me', 165, 2019)

INSERT INTO Phong VALUES
('PH01', N'Phòng 1 - Tầng 1', 50, 'LP01', 1),
('PH02', N'Phòng 2 - Tầng 1', 60, 'LP01', 1),
('PH03', N'Phòng 3 - Tầng 2', 70, 'LP02', 1),
('PH04', N'Phòng 4 - Tầng 2', 80, 'LP02', 1),
('PH05', N'Phòng 5 - Tầng 3', 100, 'LP03', 1),
('PH06', N'Phòng 6 - Tầng 3', 100, 'LP03', 1),
('PH07', N'Phòng 7 - Tầng 4', 120, 'LP04', 1),
('PH08', N'Phòng 8 - Tầng 4', 120, 'LP04', 1),
('PH09', N'Phòng 9 - Tầng 5', 40, 'LP05', 1),
('PH10', N'Phòng 10 - Tầng 5', 40, 'LP05', 1)

=======
>>>>>>> 241491a234b8d9e450355d12200143d542efac53
