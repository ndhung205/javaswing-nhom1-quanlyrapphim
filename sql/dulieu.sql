USE QuanLyRapChieuPhim


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

INSERT INTO LoaiPhim VALUES
('LPh01', N'Hành động', N'Phim có nhiều cảnh đánh nhau, cháy nổ'),
('LPh02', N'Hài hước', N'Phim giải trí, tạo tiếng cười'),
('LPh03', N'Tình cảm', N'Phim về tình yêu, cảm xúc'),
('LPh04', N'Kinh dị', N'Phim gây sợ hãi, hồi hộp'),
('LPh05', N'Phiêu lưu', N'Khám phá, hành trình mạo hiểm'),
('LPh06', N'Tâm lý', N'Phim khai thác nội tâm nhân vật'),
('LPh07', N'Hoạt hình', N'Dành cho trẻ em và gia đình'),
('LPh08', N'Khoa học viễn tưởng', N'Tương lai, vũ trụ, công nghệ'),
('LPh09', N'Hình sự', N'Tội phạm, điều tra, phá án'),
('LPh10', N'Lịch sử', N'Phim dựa trên sự kiện thật');

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


INSERT INTO Phim VALUES
('PH01', N'Avengers: Endgame', 'LPh01', N'Siêu anh hùng chống Thanos', 180, 2019),
('PH02', N'Frozen 2', 'LPh07', N'Nữ hoàng băng giá phần 2', 120, 2019),
('PH03', N'Spider-Man: No Way Home', 'LPh08', N'Người Nhện đa vũ trụ', 150, 2021),
('PH04', N'Titanic', 'LPh03', N'Chuyện tình trên biển', 195, 1997),
('PH05', N'Conjuring 3', 'LPh04', N'Phim kinh dị siêu nhiên', 140, 2021),
('PH06', N'Minions', 'LPh07', N'Những sinh vật nhỏ màu vàng', 95, 2015),
('PH07', N'Inception', 'LPh08', N'Giấc mơ trong giấc mơ', 160, 2010),
('PH08', N'John Wick 4', 'LPh01', N'Sát thủ báo thù', 170, 2023),
('PH09', N'Joker', 'LPh06', N'Tâm lý xã hội', 155, 2019),
('PH10', N'Avatar 2', 'LPh08', N'Cuộc chiến ở Pandora', 190, 2022);

INSERT INTO LichChieu VALUES
('LC01', 'PH01', 'P01', '2025-10-27', '2025-10-27 09:00:00', '2025-10-27 12:00:00'),
('LC02', 'PH02', 'P02', '2025-10-27', '2025-10-27 10:00:00', '2025-10-27 12:00:00'),
('LC03', 'PH03', 'P03', '2025-10-27', '2025-10-27 13:00:00', '2025-10-27 15:30:00'),
('LC04', 'PH04', 'P04', '2025-10-28', '2025-10-28 14:00:00', '2025-10-28 17:00:00'),
('LC05', 'PH05', 'P05', '2025-10-28', '2025-10-28 18:00:00', '2025-10-28 20:30:00'),
('LC06', 'PH06', 'P06', '2025-10-29', '2025-10-29 09:00:00', '2025-10-29 11:00:00'),
('LC07', 'PH07', 'P07', '2025-10-29', '2025-10-29 15:00:00', '2025-10-29 17:30:00'),
('LC08', 'PH08', 'P08', '2025-10-30', '2025-10-30 19:00:00', '2025-10-30 21:30:00'),
('LC09', 'PH09', 'P09', '2025-10-31', '2025-10-31 20:00:00', '2025-10-31 22:30:00'),
('LC10', 'PH10', 'P10', '2025-10-31', '2025-10-31 21:00:00', '2025-10-31 23:30:00');

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


INSERT INTO Ve VALUES
('V01', 'LC01', 'G01', 80000, N'Đã đặt', '2025-10-25 09:30:00'),
('V02', 'LC01', 'G02', 80000, N'Trống', '2025-10-25 09:35:00'),
('V03', 'LC02', 'G03', 100000, N'Đã đặt', '2025-10-25 09:40:00'),
('V04', 'LC02', 'G04', 100000, N'Trống', '2025-10-25 09:45:00'),
('V05', 'LC03', 'G05', 90000, N'Trống', '2025-10-25 09:50:00'),
('V06', 'LC03', 'G06', 90000, N'Đã đặt', '2025-10-25 09:55:00'),
('V07', 'LC04', 'G07', 110000, N'Trống', '2025-10-25 10:00:00'),
('V08', 'LC05', 'G08', 95000, N'Trống', '2025-10-25 10:05:00'),
('V09', 'LC06', 'G09', 100000, N'Đã đặt', '2025-10-25 10:10:00'),
('V10', 'LC07', 'G10', 120000, N'Trống', '2025-10-25 10:15:00');

INSERT INTO KhachHang VALUES
('KH01', N'Nguyễn Văn A', '0901111111'),
('KH02', N'Trần Thị B', '0902222222'),
('KH03', N'Lê Văn C', '0903333333'),
('KH04', N'Phạm Thị D', '0904444444'),
('KH05', N'Hoàng Văn E', '0905555555'),
('KH06', N'Vũ Thị F', '0906666666'),
('KH07', N'Ngô Văn G', '0907777777'),
('KH08', N'Đặng Thị H', '0908888888'),
('KH09', N'Bùi Văn I', '0909999999'),
('KH10', N'Đỗ Thị K', '0910000000');

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

INSERT INTO DatVe VALUES
('DV01', 'KH01', '2025-10-25 09:00:00', N'Đã xác nhận', N'Đặt qua web'),
('DV02', 'KH02', '2025-10-25 10:00:00', N'Chờ xác nhận', NULL),
('DV03', 'KH03', '2025-10-25 11:00:00', N'Đã hủy', N'Không tới rạp'),
('DV04', 'KH04', '2025-10-25 12:00:00', N'Đã xác nhận', NULL),
('DV05', 'KH05', '2025-10-25 13:00:00', N'Chờ xác nhận', NULL),
('DV06', 'KH06', '2025-10-25 14:00:00', N'Đã xác nhận', N'Thanh toán online'),
('DV07', 'KH07', '2025-10-25 15:00:00', N'Chờ xác nhận', NULL),
('DV08', 'KH08', '2025-10-25 16:00:00', N'Đã xác nhận', NULL),
('DV09', 'KH09', '2025-10-25 17:00:00', N'Đã hủy', NULL),
('DV10', 'KH10', '2025-10-25 18:00:00', N'Đã xác nhận', NULL);

INSERT INTO ChiTietDatVe VALUES
('DV01', 'V01'),
('DV02', 'V03'),
('DV03', 'V06'),
('DV04', 'V04'),
('DV05', 'V02'),
('DV06', 'V05'),
('DV07', 'V07'),
('DV08', 'V08'),
('DV09', 'V09'),
('DV10', 'V10');

INSERT INTO HoaDon VALUES
('HD01', 'KH01', 'NV06', 'DV01', '2025-10-26 09:00:00', 'T02', 'KM01', 'PT04', '2025-10-26 09:10:00', N'Đã thanh toán'),
('HD02', 'KH02', 'NV06', 'DV02', '2025-10-26 10:00:00', 'T02', NULL, 'PT01', NULL, N'Chưa thanh toán'),
('HD03', 'KH03', 'NV05', 'DV03', '2025-10-26 11:00:00', 'T03', NULL, 'PT03', NULL, N'Chưa thanh toán'),
('HD04', 'KH04', 'NV06', 'DV04', '2025-10-26 12:00:00', 'T02', 'KM02', 'PT04', '2025-10-26 12:05:00', N'Đã thanh toán'),
('HD05', 'KH05', 'NV06', 'DV05', '2025-10-26 13:00:00', 'T01', NULL, 'PT02', NULL, N'Chưa thanh toán'),
('HD06', 'KH06', 'NV07', 'DV06', '2025-10-26 14:00:00', 'T02', 'KM03', 'PT06', '2025-10-26 14:05:00', N'Đã thanh toán'),
('HD07', 'KH07', 'NV08', 'DV07', '2025-10-26 15:00:00', 'T02', NULL, 'PT05', NULL, N'Chưa thanh toán'),
('HD08', 'KH08', 'NV09', 'DV08', '2025-10-26 16:00:00', 'T02', 'KM04', 'PT07', '2025-10-26 16:10:00', N'Đã thanh toán'),
('HD09', 'KH09', 'NV09', 'DV09', '2025-10-26 17:00:00', 'T03', NULL, 'PT02', NULL, N'Chưa thanh toán'),
('HD10', 'KH10', 'NV10', 'DV10', '2025-10-26 18:00:00', 'T02', 'KM01', 'PT04', '2025-10-26 18:10:00', N'Đã thanh toán');

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


INSERT INTO Ve (maVe, maLichChieu, maGhe, gia, trangThai, thoiGianDat)
VALUES
('VE01', 'LC01', 'G01', 120000, N'Đã bán', GETDATE()),
('VE02', 'LC02', 'G02', 100000, N'Trống', NULL),
('VE03', 'LC03', 'G03', 150000, N'Đã bán', GETDATE()),
('VE04', 'LC04', 'G04', 110000, N'Trống', NULL),
('VE05', 'LC05', 'G05', 130000, N'Đã bán', GETDATE()),
('VE06', 'LC06', 'G06', 100000, N'Trống', NULL),
('VE07', 'LC07', 'G07', 140000, N'Đã bán', GETDATE()),
('VE08', 'LC08', 'G08', 120000, N'Trống', NULL),
('VE09', 'LC09', 'G09', 110000, N'Đã bán', GETDATE()),
('VE10', 'LC10', 'G10', 100000, N'Trống', NULL);

