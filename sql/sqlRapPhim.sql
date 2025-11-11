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
	poster NVARCHAR(200),
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


-- Bảng Khách hàng
CREATE TABLE KhachHang (
    maKhachHang NVARCHAR(50) PRIMARY KEY,
    ten NVARCHAR(100) NOT NULL,
    soDienThoai NVARCHAR(15),
);

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

-- Bảng Vé
CREATE TABLE Ve (
    maVe NVARCHAR(50) PRIMARY KEY,
    maLichChieu NVARCHAR(50) NOT NULL,
    maGhe NVARCHAR(50) NOT NULL,
	maDatVe NVARCHAR(50) NOT NULL,
    gia DECIMAL(10,2) NOT NULL, -- Giá = GiaVeCoBan + PhuThuGhe
    trangThai NVARCHAR(50) NOT NULL DEFAULT N'Trống',
    thoiGianDat DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_Ve_LichChieu FOREIGN KEY (maLichChieu)
        REFERENCES LichChieu(maLichChieu)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FK_Ve_Ghe FOREIGN KEY (maGhe)
        REFERENCES Ghe(maGhe)
        ON DELETE NO ACTION ON UPDATE NO ACTION,
    CONSTRAINT UQ_Ve_LichChieu_Ghe UNIQUE (maLichChieu, maGhe),
	 CONSTRAINT FK_Ve_DatVe FOREIGN KEY (maDatVe)REFERENCES DatVe(maDatVe)
        ON DELETE CASCADE ON UPDATE CASCADE
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
        ON DELETE NO ACTION ON UPDATE NO ACTION
);


INSERT INTO LoaiPhong VALUES
('LP01', N'Phòng 2D', N'Phòng chiếu phim 2D tiêu chuẩn'),
('LP02', N'Phòng 3D', N'Phòng chiếu phim 3D hiện đại'),
('LP03', N'Phòng VIP', N'Ghế rộng, phục vụ riêng'),
('LP04', N'Phòng IMAX', N'Màn hình siêu lớn, âm thanh vòm'),
('LP05', N'Phòng 4DX', N'Hiệu ứng gió, nước, rung lắc'),
('LP06', N'Phòng Couple', N'Ghế đôi dành cho cặp đôi'),
('LP07', N'Phòng Mini', N'Sức chứa ít, chiếu phim nghệ thuật'),
('LP08', N'Phòng Premium', N'Trang thiết bị cao cấp'),
('LP09', N'Phòng Standard', N'Tiêu chuẩn phổ thông'),
('LP10', N'Phòng Classic', N'Phong cách truyền thống');

INSERT INTO LoaiPhim (maLoaiPhim, tenLoaiPhim) VALUES
('LPh001', N'Hành động'),
('LPh002', N'Hài hước'),
('LPh003', N'Kinh dị'),
('LPh004', N'Tâm lý'),
('LPh005', N'Phiêu lưu'),
('LPh006', N'Hoạt hình');

INSERT INTO LoaiGhe VALUES
('LG01', N'Thường', 0, N'Ghế tiêu chuẩn'),
('LG02', N'VIP', 20000, N'Ghế lớn, có chỗ để chân'),
('LG03', N'Dôi', 30000, N'Dành cho hai người'),
('LG04', N'4DX', 50000, N'Có hiệu ứng rung lắc'),
('LG05', N'IMAX', 40000, N'Dành riêng cho phòng IMAX'),
('LG06', N'Couple', 25000, N'Ghế đôi nhỏ gọn'),
('LG07', N'Premium', 35000, N'Cao cấp, có massage'),
('LG08', N'Classic', 10000, N'Ghế kiểu truyền thống'),
('LG09', N'Sofa', 30000, N'Rộng rãi, êm ái'),
('LG10', N'Lazy Chair', 40000, N'Có thể ngả ra nằm');

INSERT INTO ChucVu VALUES
('CV01', N'Quản lý', N'Giám sát toàn bộ hoạt động rạp'),
('CV02', N'Nhân viên bán vé', N'Phụ trách bán vé'),
('CV03', N'Nhân viên kỹ thuật', N'Vận hành thiết bị chiếu phim'),
('CV04', N'Nhân viên dọn dẹp', N'Giữ vệ sinh phòng chiếu'),
('CV05', N'Bảo vệ', N'Đảm bảo an ninh'),
('CV06', N'Thu ngân', N'Xử lý thanh toán'),
('CV07', N'Nhân viên marketing', N'Tuyên truyền, quảng bá'),
('CV08', N'Trợ lý quản lý', N'Hỗ trợ công việc quản lý'),
('CV09', N'Nhân viên chăm sóc khách hàng', N'Hỗ trợ khách'),
('CV10', N'Nhân viên kho', N'Quản lý hàng hóa');

INSERT INTO PhuongThucThanhToan VALUES
('PT01', N'Tiền mặt', N'Thanh toán tại quầy'),
('PT02', N'Thẻ tín dụng', N'Sử dụng Visa/MasterCard'),
('PT03', N'Chuyển khoản', N'Thanh toán qua ngân hàng'),
('PT04', N'MoMo', N'Ví điện tử MoMo'),
('PT05', N'ZaloPay', N'Ví điện tử ZaloPay'),
('PT06', N'VNPay', N'Mã QR thanh toán VNPay'),
('PT07', N'ShopeePay', N'Ví điện tử ShopeePay'),
('PT08', N'Trả góp', N'Thanh toán dần theo kỳ hạn'),
('PT09', N'Gift Card', N'Thẻ quà tặng'),
('PT10', N'Thanh toán online', N'Thông qua website');


INSERT INTO Thue VALUES
('T01', N'Thuế VAT 5%', 5.00, N'Áp dụng cho phim học đường'),
('T02', N'Thuế VAT 8%', 8.00, N'Áp dụng chung'),
('T03', N'Thuế VAT 10%', 10.00, N'Phim 3D, 4DX'),
('T04', N'Thuế VAT 12%', 12.00, N'Phim nước ngoài'),
('T05', N'Thuế VAT 15%', 15.00, N'Phim cao cấp'),
('T06', N'Thuế ưu đãi', 3.00, N'Áp dụng ngày lễ'),
('T07', N'Thuế doanh nghiệp', 20.00, N'Chỉ cho hóa đơn đặc biệt'),
('T08', N'Thuế nhập khẩu', 25.00, N'Phim nước ngoài nhập'),
('T09', N'Thuế dịch vụ', 7.00, N'Phụ phí dịch vụ'),
('T10', N'Thuế khuyến mãi', 2.00, N'Giảm nhẹ cho khách thân thiết');


INSERT INTO KhuyenMai VALUES
('KM01', N'Giảm 10% cuối tuần', 10.00, 0, '2025-10-01', '2025-10-31', N'Áp dụng tất cả vé', 1),
('KM02', N'Mua 2 tặng 1', 0, 50000, '2025-09-01', '2025-12-01', N'Chỉ áp dụng phim 2D', 1),
('KM03', N'Khách hàng mới', 5.00, 0, '2025-10-01', '2025-12-31', N'Dành cho tài khoản mới', 1),
('KM04', N'Thứ 3 vui vẻ', 0, 30000, '2025-10-01', '2025-12-31', N'Áp dụng thứ 3 hàng tuần', 1),
('KM05', N'Ưu đãi VIP', 15.00, 0, '2025-09-01', '2025-11-30', N'Khách hàng VIP', 1),
('KM06', N'Giảm học sinh', 10.00, 0, '2025-09-01', '2026-01-01', N'Xuất trình thẻ học sinh', 1),
('KM07', N'Sinh nhật vàng', 20.00, 0, '2025-01-01', '2025-12-31', N'Giảm cho khách sinh nhật', 1),
('KM08', N'Giảm lễ Tết', 0, 40000, '2025-02-01', '2025-02-20', N'Áp dụng dịp Tết', 1),
('KM09', N'Combo vé + bắp + nước', 0, 60000, '2025-10-10', '2025-12-30', N'Áp dụng phim bom tấn', 1),
('KM10', N'Black Friday', 30.00, 0, '2025-11-20', '2025-11-30', N'Tất cả phim', 1);


INSERT INTO Phim(maPhim, tenPhim, maLoaiPhim, moTa,thoiLuongChieu, namPhatHanh, poster) VALUES
('PH001', N'Người Hùng Thời Tận Thế', 'LPh001', N'Sau một trận bão mặt trời tàn phá Trái Đất, nhân loại rơi vào hỗn loạn. Một thợ săn kho báu liều lĩnh lên đường tìm Mona Lisa nhưng trở thành người hùng của nhân loại.', 105, 2025, N'img/poster/Nguoi_Hung_Thoi_Tan_The.jpg'),
('PH002', N'Cuộc Chiến Giữa Các Thế Giới', 'LPh005', N'Một chuyên gia an ninh mạng phát hiện chính phủ che giấu một thực thể bí ẩn từ thế giới khác.', 91, 2025, N'img/poster/Cuoc_Chien_Giua_Cac_The_Gioi.jpg'),
('PH003', N'Culpa Nuestra', 'LPh004', N'Câu chuyện tình cảm phức tạp giữa Noah và Nick khi định mệnh đưa họ gặp lại sau nhiều năm chia tay.', 112, 2025, N'img/poster/Culpa_Nuestra.jpg'),
('PH004', N'Marvels: Secret Wars', 'LPh001', N'Các siêu anh hùng Marvel hợp lực để ngăn chặn đa vũ trụ sụp đổ trong trận chiến cuối cùng.', 135, 2025, N'img/poster/Marvels_Secret_Wars.jpg'),
('PH005', N'Inside Out 2', 'LPh006', N'Riley bước vào tuổi dậy thì với những cảm xúc mới đầy hỗn loạn và bất ngờ.', 95, 2024, N'img/poster/Inside_Out_2.jpg'),
('PH006', N'Nhà Bà Nữ 2', 'LPh002', N'Tiếp nối thành công phần đầu, câu chuyện hài hước nhưng cảm động về gia đình bà Nữ.', 120, 2025, N'img/poster/Nha_Ba_Nu_2.jpg'),
('PH007', N'The Batman: Rebirth', 'LPh001', N'Batman tái sinh trong một Gotham hỗn loạn sau khi Joker trốn thoát khỏi Arkham.', 140, 2025, N'img/poster/The_Batman_Rebirth.jpg'),
('PH008', N'Frozen 3', 'LPh006', N'Anna và Elsa bắt đầu chuyến phiêu lưu mới khi một bí mật về nguồn gốc băng giá được tiết lộ.', 102, 2026, N'img/poster/Frozen_3.jpg'),
('PH009', N'Mất Tích Ở Đà Lạt', 'LPh003', N'Một nhóm bạn trẻ mất tích bí ẩn trong rừng thông Đà Lạt, nơi tồn tại truyền thuyết rùng rợn.', 98, 2025, N'img/poster/Mat_Tich_o_Da_Lat.jpg'),
('PH010', N'The Lion King Reborn', 'LPh006', N'Câu chuyện kinh điển được làm lại với công nghệ CGI chân thực và âm nhạc mới.', 118, 2024, N'img/poster/The_Lion_King_Reborn.jpg'),
('PH011', N'Thanh Xuân Rực Rỡ', 'LPh004', N'Một cô gái tỉnh lẻ lên thành phố theo đuổi ước mơ ca hát và tìm thấy tình yêu.', 107, 2024, N'img/poster/Thanh_Xuan_Ruc_Ro.jpg'),
('PH012', N'Avengers: Legacy', 'LPh001', N'Đội Avengers thế hệ mới xuất hiện để bảo vệ Trái Đất khỏi mối đe dọa vũ trụ.', 150, 2026, N'img/poster/Avengers_Legacy.jpg'),
('PH013', N'Nhà Không Bán', 'LPh003', N'Câu chuyện kinh dị Việt Nam về một ngôi nhà ma ám và lời nguyền gia tộc.', 102, 2022, N'img/poster/Nha_Khong_Ban.jpg'),
('PH014', N'Sonic 3', 'LPh005', N'Sonic cùng bạn bè đối đầu kẻ thù mới từ không gian, mở ra cuộc phiêu lưu tốc độ cao.', 115, 2025, N'img/poster/Sonic_3.jpg'),
('PH015', N'Titanic 2', 'LPh004', N'Một chuyến tàu xa hoa mới được xây dựng, nhưng lịch sử có lặp lại?', 125, '2025', N'img/poster/Titanic_2.jpg'),
('PH016', N'Spider-Man: Beyond Time', 'LPh001', N'Spiderman du hành qua các chiều không gian để ngăn chặn sự sụp đổ đa vũ trụ.', 130, 2026, N'img/poster/SpiderMan_Beyond_Time.jpg'),
('PH017', N'Hài Kịch Cuộc Đời', 'LPh002', N'Một chàng diễn viên hài thất bại tìm thấy cơ hội thứ hai khi bước lên sân khấu cuộc đời.', 90, 2024, N'img/poster/Hai_Kich_Cuoc_Doi.jpg'),
('PH018', N'Avatar: The Seed', 'LPh005', N'Một thế hệ mới của người Na’vi bắt đầu hành trình bảo vệ hành tinh Pandora.', 155, 2026, N'img/poster/Avatar_The_Seed.jpg'),
('PH019', N'IT: Legacy', 'LPh003', N'Thị trấn Derry lại chìm trong ác mộng khi nỗi sợ cũ sống lại cùng thế hệ mới.', 128, 2025, N'img/poster/IT_Legacy.jpg'),
('PH020', N'Bay Cùng Yêu Thương', 'LPh004', N'Một tiếp viên hàng không tìm thấy ý nghĩa cuộc sống qua những hành khách đặc biệt.', 101, 2024, N'img/poster/Bay_Cung_Yeu_Thuong.jpg'),
('PH021', N'Bad Boys 5', 'LPh001', N'Hai cảnh sát già tiếp tục phá án trong phi vụ cuối cùng trước khi nghỉ hưu.', 122, 2025, N'img/poster/Bad_Boys_5.jpg'),
('PH022', N'Kung Fu Panda 4', 'LPh006', N'Po quay lại với sứ mệnh đào tạo Thần Long chiến binh kế tiếp.', 98, 2024, N'img/poster/KungFu_Panda_4.jpg'),
('PH023', N'Trò Chơi Sinh Tồn', 'LPh003', N'Một nhóm người bị nhốt trong trò chơi thực tế ảo mà sinh mạng là cái giá phải trả.', 115, 2025, N'img/poster/Tro_Choi_Sinh_Ton.jpg'),
('PH024', N'Ngày Mai Bình Yên', 'LPh004', N'Câu chuyện về lòng nhân ái và sự tha thứ giữa những con người tổn thương.', 100, 2023, N'img/poster/Ngay_Mai_Binh_Yen.jpg'),
('PH025', N'Fast & Furious 11', 'LPh001', N'Dominic Toretto cùng đội của anh đối đầu kẻ thù nguy hiểm nhất từ trước đến nay.', 145, 2025, N'img/poster/Fast_Furious_11.jpg'),
('PH026', N'Vùng Đất Câm Lặng 3', 'LPh003', N'Tiếp tục câu chuyện sinh tồn trong thế giới bị thống trị bởi quái vật âm thanh.', 108, 2024, N'img/poster/Vung_Dat_Cam_Lang_3.jpg'),
('PH027', N'Hành Trình Kỳ Diệu', 'LPh005', N'Một cậu bé cùng chú chó máy khám phá thế giới kỳ diệu bên ngoài Trái Đất.', 110, 2024, N'img/poster/Hanh_Trinh_Ky_Dieu.jpg'),
('PH028', N'Minions: Rise Again', 'LPh006', N'Các Minion tiếp tục hành trình nghịch ngợm với ông chủ Gru thời trẻ.', 89, 2024, N'img/poster/Minions_Rise_Again.jpg'),
('PH029', N'Lật Mặt 7: Cú Lừa', 'LPh002', N'Một bộ phim Việt đầy kịch tính xen lẫn hài hước của Lý Hải.', 123, 2025, N'img/poster/Lat_Mat_7_Cu_Lua.jpg'),
('PH030', N'Godzilla x Kong: The New Empire', 'LPh001', N'Hai quái thú huyền thoại hợp lực đối đầu mối đe dọa từ lòng đất.', 138, 2024, N'img/poster/Godzilla_x_Kong_The_New_Empire.jpg');


INSERT INTO Phong (maPhong, tenPhong, soLuongGhe, maLoaiPhong, trangThai)
VALUES
('P01', N'Phòng 1 - VIP', 50, 'LP01', 1),
('P02', N'Phòng 2 - 3D', 60, 'LP02', 1),
('P03', N'Phòng 3 - 4DX', 40, 'LP03', 1),
('P04', N'Phòng 4 - Thường', 80, 'LP02', 1),
('P05', N'Phòng 5 - VIP', 55, 'LP01', 1),
('P06', N'Phòng 6 - 3D', 70, 'LP02', 1),
('P07', N'Phòng 7 - 4DX', 45, 'LP03', 1),
('P08', N'Phòng 8 - Thường', 90, 'LP02', 1),
('P09', N'Phòng 9 - VIP', 65, 'LP01', 1),
('P10', N'Phòng 10 - 3D', 75, 'LP02', 1);

INSERT INTO LichChieu VALUES
('LC01', 'PH001', 'P01', '2025-10-27', '2025-10-27 09:00:00', '2025-10-27 12:00:00'),
('LC02', 'PH002', 'P02', '2025-10-27', '2025-10-27 10:00:00', '2025-10-27 12:00:00'),
('LC03', 'PH003', 'P03', '2025-10-27', '2025-10-27 13:00:00', '2025-10-27 15:30:00'),
('LC04', 'PH004', 'P04', '2025-10-28', '2025-10-28 14:00:00', '2025-10-28 17:00:00'),
('LC05', 'PH005', 'P05', '2025-10-28', '2025-10-28 18:00:00', '2025-10-28 20:30:00'),
('LC06', 'PH006', 'P06', '2025-10-29', '2025-10-29 09:00:00', '2025-10-29 11:00:00'),
('LC07', 'PH007', 'P07', '2025-10-29', '2025-10-29 15:00:00', '2025-10-29 17:30:00'),
('LC08', 'PH008', 'P08', '2025-10-30', '2025-10-30 19:00:00', '2025-10-30 21:30:00'),
('LC09', 'PH009', 'P09', '2025-10-31', '2025-10-31 20:00:00', '2025-10-31 22:30:00'),
('LC10', 'PH010', 'P10', '2025-10-31', '2025-10-31 21:00:00', '2025-10-31 23:30:00');

INSERT INTO Ghe VALUES
('G01', 'P01', 'LG01', N'Trống'),
('G02', 'P01', 'LG01', N'Trống'),
('G03', 'P02', 'LG02', N'Đã đặt'),
('G04', 'P02', 'LG02', N'Trống'),
('G05', 'P03', 'LG03', N'Trống'),
('G06', 'P03', 'LG03', N'Đã đặt'),
('G07', 'P04', 'LG04', N'Trống'),
('G08', 'P05', 'LG05', N'Trống'),
('G09', 'P06', 'LG06', N'Trống'),
('G10', 'P07', 'LG07', N'Trống');



INSERT INTO KhachHang (maKhachHang, ten, soDienThoai) VALUES
('KH001', N'Nguyễn Minh Anh', '0905123456'),
('KH002', N'Trần Thị Thu Hà', '0938234567'),
('KH003', N'Lê Hoàng Long', '0974123456'),
('KH004', N'Phạm Thị Ngọc Hân', '0906765432'),
('KH005', N'Hoàng Đức Trung', '0398456123'),
('KH006', N'Vũ Thị Mai Hương', '0707123987'),
('KH007', N'Ngô Quang Huy', '0939789456'),
('KH008', N'Đặng Thị Thanh Tâm', '0378456129'),
('KH009', N'Bùi Anh Tuấn', '0909345678'),
('KH010', N'Đỗ Thị Kim Ngân', '0798456789'),
('KH011', N'Phan Văn Dũng', '0907213456'),
('KH012', N'Tạ Thị Mỹ Linh', '0379564821'),
('KH013', N'Lý Hoàng Nam', '0937632589'),
('KH014', N'Đinh Thị Thùy Dung', '0909347852'),
('KH015', N'Nguyễn Thanh Tùng', '0978456132'),
('KH016', N'Phạm Thị Minh Ngọc', '0399845671'),
('KH017', N'Võ Hữu Nghĩa', '0908456321'),
('KH018', N'Trịnh Thị Cẩm Tiên', '0797345896'),
('KH019', N'Trương Văn Kiệt', '0939567412'),
('KH020', N'Cao Thị Diễm Quỳnh', '0379845126'),
('KH021', N'Nguyễn Văn Phúc', '0908457123'),
('KH022', N'Lê Thị Mai Anh', '0978456123'),
('KH023', N'Trần Quốc Bảo', '0907345689'),
('KH024', N'Phan Thị Hồng Nhung', '0399345678'),
('KH025', N'Bùi Văn Hòa', '0798456234'),
('KH026', N'Đặng Thị Hồng Hạnh', '0938456321'),
('KH027', N'Nguyễn Đình Khải', '0909567123'),
('KH028', N'Trần Thị Huyền Trang', '0379845632'),
('KH029', N'Lê Quốc Khánh', '0707123984'),
('KH030', N'Phạm Ngọc Bích', '0907345981'),
('KH031', N'Nguyễn Thanh Bình', '0939345678'),
('KH032', N'Vũ Thị Lan Anh', '0978234561'),
('KH033', N'Ngô Minh Hòa', '0909345782'),
('KH034', N'Đỗ Thị Bảo Yến', '0378456791'),
('KH035', N'Trần Văn Lực', '0398456213'),
('KH036', N'Lê Thị Thu Trang', '0939456321'),
('KH037', N'Phan Hữu Tài', '0909456231'),
('KH038', N'Đinh Thị Mai', '0797345892'),
('KH039', N'Nguyễn Hồng Phúc', '0379845134'),
('KH040', N'Cao Thị Hồng Gấm', '0908456734'),
('KH041', N'Vũ Hoàng Nam', '0939567456'),
('KH042', N'Nguyễn Thị Thu Thảo', '0978456124'),
('KH043', N'Trương Đình Hậu', '0708234569'),
('KH044', N'Phạm Thị Bảo Trân', '0909123789'),
('KH045', N'Lê Đức Hiếu', '0938456987'),
('KH046', N'Tạ Thị Hồng Vân', '0399345612'),
('KH047', N'Nguyễn Thanh Huyền', '0378456219'),
('KH048', N'Bùi Minh Quân', '0907123459'),
('KH049', N'Trần Thị Phương Linh', '0978456234'),
('KH050', N'Phan Anh Tuấn', '0937345890');

INSERT INTO DatVe VALUES
('DV01', 'KH001', '2025-10-25 09:00:00', N'Đã xác nhận', N'Đặt qua web'),
('DV02', 'KH001', '2025-10-25 10:00:00', N'Chờ xác nhận', NULL),
('DV03', 'KH003', '2025-10-25 11:00:00', N'Đã hủy', N'Không tới rạp'),
('DV04', 'KH004', '2025-10-25 12:00:00', N'Đã xác nhận', NULL),
('DV05', 'KH005', '2025-10-25 13:00:00', N'Chờ xác nhận', NULL),
('DV06', 'KH006', '2025-10-25 14:00:00', N'Đã xác nhận', N'Thanh toán online'),
('DV07', 'KH007', '2025-10-25 15:00:00', N'Chờ xác nhận', NULL),
('DV08', 'KH008', '2025-10-25 16:00:00', N'Đã xác nhận', NULL),
('DV09', 'KH009', '2025-10-25 17:00:00', N'Đã hủy', NULL),
('DV10', 'KH010', '2025-10-25 18:00:00', N'Đã xác nhận', NULL);


INSERT INTO Ve VALUES
('V01', 'LC01', 'G01', 'DV01',80000, N'Đã đặt', '2025-10-25 09:30:00'),
('V02', 'LC01', 'G02', 'DV01',80000, N'Đã đặt', '2025-10-25 09:35:00'),
('V03', 'LC02', 'G03', 'DV02',100000, N'Đã đặt', '2025-10-25 09:40:00'),
('V04', 'LC02', 'G04', 'DV03',100000, N'Đã đặt', '2025-10-25 09:45:00'),
('V05', 'LC03', 'G05', 'DV04',90000, N'Đã đặt', '2025-10-25 09:50:00'),
('V06', 'LC03', 'G06', 'DV05',90000, N'Đã đặt', '2025-10-25 09:55:00'),
('V07', 'LC04', 'G07', 'DV06',110000, N'Đã đặt', '2025-10-25 10:00:00'),
('V08', 'LC05', 'G08', 'DV07',95000, N'Đã đặt', '2025-10-25 10:05:00'),
('V09', 'LC06', 'G09', 'DV08',100000, N'Đã đặt', '2025-10-25 10:10:00'),
('V10', 'LC07', 'G10', 'DV03',120000, N'Đã đặt', '2025-10-25 10:15:00');


INSERT INTO NhanVien VALUES
('NV01', N'Nguyễn Minh Quân', '0981111111', 'quan@cinema.vn', 'CV01', '2022-01-05'),
('NV02', N'Lê Thị Hoa', '0982222222', 'hoa@cinema.vn', 'CV02', '2023-03-10'),
('NV03', N'Phạm Văn Bình', '0983333333', 'binh@cinema.vn', 'CV03', '2021-07-01'),
('NV04', N'Ngô Thị Cúc', '0984444444', 'cuc@cinema.vn', 'CV04', '2022-11-11'),
('NV05', N'Hoàng Văn Nam', '0985555555', 'nam@cinema.vn', 'CV05', '2023-01-01'),
('NV06', N'Trần Thị Mai', '0986666666', 'mai@cinema.vn', 'CV06', '2022-12-20'),
('NV07', N'Lý Văn Phong', '0987777777', 'phong@cinema.vn', 'CV07', '2024-05-09'),
('NV08', N'Đặng Thu Hằng', '0988888888', 'hang@cinema.vn', 'CV08', '2021-09-22'),
('NV09', N'Vũ Quang Khải', '0989999999', 'khai@cinema.vn', 'CV09', '2023-02-14'),
('NV10', N'Bùi Minh Đức', '0990000000', 'duc@cinema.vn', 'CV10', '2022-10-05');



INSERT INTO TaiKhoan VALUES
('TK01', 'NV01', N'admin', N'123456', N'Admin', 1),
('TK02', 'NV02', N'lehoa', N'123456', N'NhanVien', 1),
('TK03', 'NV03', N'binh03', N'123456', N'NhanVien', 1),
('TK04', 'NV04', N'ngocuc', N'123456', N'NhanVien', 1),
('TK05', 'NV05', N'namhv', N'123456', N'NhanVien', 1),
('TK06', 'NV06', N'maitt', N'123456', N'NhanVien', 1),
('TK07', 'NV07', N'phonglv', N'123456', N'QuanLy', 1),
('TK08', 'NV08', N'hangdt', N'123456', N'NhanVien', 1),
('TK09', 'NV09', N'khaivq', N'123456', N'NhanVien', 1),
('TK10', 'NV10', N'ducbm', N'123456', N'NhanVien', 1);




INSERT INTO HoaDon VALUES
('HD01', 'KH001', 'NV06', 'DV01', '2025-10-26 09:00:00', 'T02', 'KM01', 'PT04', '2025-10-26 09:10:00', N'Đã thanh toán'),
('HD02', 'KH002', 'NV06', 'DV02', '2025-10-26 10:00:00', 'T02', NULL, 'PT01', NULL, N'Chưa thanh toán'),
('HD03', 'KH003', 'NV05', 'DV03', '2025-10-26 11:00:00', 'T03', NULL, 'PT03', NULL, N'Chưa thanh toán'),
('HD04', 'KH004', 'NV06', 'DV04', '2025-10-26 12:00:00', 'T02', 'KM02', 'PT04', '2025-10-26 12:05:00', N'Đã thanh toán'),
('HD05', 'KH005', 'NV06', 'DV05', '2025-10-26 13:00:00', 'T01', NULL, 'PT02', NULL, N'Chưa thanh toán'),
('HD06', 'KH006', 'NV07', 'DV06', '2025-10-26 14:00:00', 'T02', 'KM03', 'PT06', '2025-10-26 14:05:00', N'Đã thanh toán'),
('HD07', 'KH007', 'NV08', 'DV07', '2025-10-26 15:00:00', 'T02', NULL, 'PT05', NULL, N'Chưa thanh toán'),
('HD08', 'KH008', 'NV09', 'DV08', '2025-10-26 16:00:00', 'T02', 'KM04', 'PT07', '2025-10-26 16:10:00', N'Đã thanh toán'),
('HD09', 'KH009', 'NV09', 'DV09', '2025-10-26 17:00:00', 'T03', NULL, 'PT02', NULL, N'Chưa thanh toán'),
('HD10', 'KH010', 'NV10', 'DV10', '2025-10-26 18:00:00', 'T02', 'KM01', 'PT04', '2025-10-26 18:10:00', N'Đã thanh toán');

INSERT INTO ChiTietHoaDon VALUES
('HD01', 'V01', 80000, 1),
('HD02', 'V03', 100000, 1),
('HD03', 'V06', 90000, 1),
('HD04', 'V04', 100000, 1),
('HD05', 'V02', 80000, 1),
('HD06', 'V05', 90000, 1),
('HD07', 'V07', 110000, 1),
('HD08', 'V08', 95000, 1),
('HD09', 'V09', 100000, 1),
('HD10', 'V10', 120000, 1);








