-- XÓA DATABASE CŨ VÀ TẠO MỚI
IF DB_ID(N'QLOTOANDROID') IS NOT NULL
BEGIN
    ALTER DATABASE QLOTOANDROID SET SINGLE_USER WITH ROLLBACK IMMEDIATE;
    DROP DATABASE QLOTOANDROID;
END
GO

CREATE DATABASE QLOTOANDROID;
GO
USE QLOTOANDROID;
GO


-- =========================
-- BẢNG ADMIN
-- =========================
CREATE TABLE ADMIN (
    MaAdmin INT IDENTITY(1,1) PRIMARY KEY,
    TenDangNhap NVARCHAR(100) UNIQUE NOT NULL,
    MatKhau VARCHAR(255) NOT NULL,
    VaiTro NVARCHAR(20) DEFAULT N'admin'
);

-- =========================
-- BẢNG NGƯỜI DÙNG
-- =========================
CREATE TABLE NGUOI_DUNG (
    MaND INT IDENTITY(1,1) PRIMARY KEY,
    HoTen NVARCHAR(100) NOT NULL,
    Email VARCHAR(100) UNIQUE NULL,
    DienThoai VARCHAR(20) UNIQUE NULL,
    NgaySinh VARCHAR(50) NOT NULL,
    MatKhau VARCHAR(255) NOT NULL,
    VaiTro VARCHAR(20) DEFAULT 'customer',
    NgayBaoDuong DATETIME NULL,
    SoLanBaoDuong INT NOT NULL DEFAULT 0,
	IsLocked BIT NOT NULL DEFAULT 0,
    CONSTRAINT CK_ND_LIENHE CHECK (Email IS NOT NULL OR DienThoai IS NOT NULL)
);

-- =========================
-- BẢNG HÃNG XE
-- =========================
CREATE TABLE HANG_XE (
    MaHangXe VARCHAR(10) PRIMARY KEY,
    TenHangXe NVARCHAR(100) NOT NULL UNIQUE,
    MoTa NVARCHAR(MAX) NULL
);

-- =========================
-- BẢNG LOẠI XE
-- =========================
CREATE TABLE LOAI_XE (
    MaHangXe VARCHAR(10) NOT NULL,
    MaLoaiXe VARCHAR(10) PRIMARY KEY,
    TenLoaiXe NVARCHAR(50) NOT NULL,
    MoTa NVARCHAR(255) NULL,
    CONSTRAINT FK_LOAIXE_HANGXE FOREIGN KEY (MaHangXe) REFERENCES HANG_XE(MaHangXe)
);

-- =========================
-- BẢNG XE
-- =========================
CREATE TABLE XE (
    MaXe VARCHAR(10) PRIMARY KEY,
    MaHangXe VARCHAR(10) NOT NULL,
    MaND INT NOT NULL,
    MaLoaiXe VARCHAR(10) NOT NULL,
    BienSo NVARCHAR(20) NOT NULL,
    DungTich VARCHAR(10) NOT NULL,
    SoKhung VARCHAR(50) NOT NULL,
    MauSac NVARCHAR(30) NOT NULL,
    HinhAnh NVARCHAR(255) NOT NULL,
    CONSTRAINT FK_XE_HANGXE FOREIGN KEY (MaHangXe) REFERENCES HANG_XE(MaHangXe),
    CONSTRAINT FK_XE_LOAIXE FOREIGN KEY (MaLoaiXe) REFERENCES LOAI_XE(MaLoaiXe),
    CONSTRAINT FK_XE_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

-- =========================
-- DANH MỤC PHỤ TÙNG
-- =========================
CREATE TABLE DM_PHUTUNG (
    MaDanhMuc VARCHAR(10) PRIMARY KEY,
    TenDanhMuc NVARCHAR(100) NOT NULL
);

-- =========================
-- PHỤ TÙNG
-- =========================
CREATE TABLE PHU_TUNG (
    MaPhuTung VARCHAR(10) PRIMARY KEY,
    MaHangXe VARCHAR(10) NOT NULL,
    MaLoaiXe VARCHAR(10) NOT NULL,
    TenPhuTung NVARCHAR(100) NOT NULL,
    GiaBan DECIMAL(18,0) NOT NULL CHECK (GiaBan >= 0),
    SoLuong INT DEFAULT 1 CHECK (SoLuong >= 0),
    MoTa NVARCHAR(MAX) NULL,
    MaDanhMuc VARCHAR(10) NOT NULL,
    HinhAnh VARCHAR(255) NOT NULL,
    NhaCC NVARCHAR(100) NOT NULL,
    CONSTRAINT FK_PT_LOAIXE FOREIGN KEY (MaLoaiXe) REFERENCES LOAI_XE(MaLoaiXe),
    CONSTRAINT FK_PT_DM FOREIGN KEY (MaDanhMuc) REFERENCES DM_PHUTUNG(MaDanhMuc),
    CONSTRAINT FK_PT_HX FOREIGN KEY (MaHangXe) REFERENCES HANG_XE(MaHangXe)
);

-- =========================
-- VOUCHER
-- =========================
CREATE TABLE VOUCHER (
    MaVC INT IDENTITY(1,1) PRIMARY KEY,
    MaND INT NOT NULL,
    LoaiVoucher NVARCHAR(50) NOT NULL
        CHECK (LoaiVoucher IN (N'Giảm 50% phí vận chuyển', N'Miễn phí Vận chuyển')),
    HanSuDung DATE NULL,
    TrangThai NVARCHAR(20) DEFAULT N'Còn hiệu lực'
        CHECK (TrangThai IN (N'Còn hiệu lực', N'Đã sử dụng')),
    CONSTRAINT FK_VC_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

-- =========================
-- PHƯƠNG THỨC VẬN CHUYỂN
-- =========================
CREATE TABLE PHUONG_THUC_VAN_CHUYEN (
    MaPTVC INT IDENTITY(1,1) PRIMARY KEY,
    TenPTVC NVARCHAR(100) NOT NULL,
    GiaVanChuyen DECIMAL(18,0) NOT NULL,
    SoNgayDuKien INT NOT NULL,
    MoTa NVARCHAR(255) NULL
);

-- =========================
-- ĐƠN HÀNG
-- =========================
CREATE TABLE DON_HANG (
    MaDH VARCHAR(20) PRIMARY KEY,
    NgayDat DATETIME DEFAULT GETDATE(),
    TenPhuTung NVARCHAR(50) NOT NULL,
    HinhAnh NVARCHAR(50) NOT NULL,
    TongTien DECIMAL(18,0) NOT NULL DEFAULT 0,
    PhiVanChuyen DECIMAL(18,0) DEFAULT 0,
    TongThanhToan AS (TongTien + PhiVanChuyen),
    DiaChiGiao NVARCHAR(255) NOT NULL,
    GhiChu NVARCHAR(255) NULL,
    MaND INT NOT NULL,
    MaVC INT NULL,
    NgayNhanDuKien DATETIME NOT NULL,
    MaPTVC INT NULL,
    PhuongThucThanhToan NVARCHAR(50) DEFAULT N'Tiền mặt'
        CHECK (PhuongThucThanhToan IN (N'Tiền mặt', N'Apple Pay', N'Ngân hàng liên kết')),
    TrangThai NVARCHAR(50) DEFAULT N'Đang giao'
        CHECK (TrangThai IN (N'Đang giao', N'Đã giao')),
    CONSTRAINT FK_DH_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND),
    CONSTRAINT FK_DH_VC FOREIGN KEY (MaVC) REFERENCES VOUCHER(MaVC),
    CONSTRAINT FK_DH_PTVC FOREIGN KEY (MaPTVC) REFERENCES PHUONG_THUC_VAN_CHUYEN(MaPTVC),
    CONSTRAINT CK_DH_TONGTIEN CHECK (TongTien >= 0),
    CONSTRAINT CK_DH_PHIVC CHECK (PhiVanChuyen >= 0)
);

-- =========================
-- CHI TIẾT ĐƠN HÀNG
-- =========================
CREATE TABLE CHI_TIET_DON_HANG (
    MaCTDH INT IDENTITY(1,1) PRIMARY KEY,
    MaDH VARCHAR(20) NOT NULL,
    MaPhuTung VARCHAR(10) NOT NULL,
    SoLuong INT DEFAULT 1 CHECK (SoLuong > 0),
    GiaTien DECIMAL(18,0) NOT NULL CHECK (GiaTien >= 0),
    CONSTRAINT FK_CTDH_DH FOREIGN KEY (MaDH) REFERENCES DON_HANG(MaDH),
    CONSTRAINT FK_CTDH_PT FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung)
);

-- =========================
-- BẢNG ĐẠI LÝ
-- =========================
CREATE TABLE DAI_LY (
    MaDaiLy INT IDENTITY(1,1) PRIMARY KEY,
    TenDaiLy NVARCHAR(150) NOT NULL,
    DiaChi NVARCHAR(255) NOT NULL,
    SoDienThoai VARCHAR(20) NOT NULL,
    GioLamViec NVARCHAR(100) NULL,
    MoTa NVARCHAR(255) NULL
);

-- =========================
-- DỊCH VỤ
-- =========================
CREATE TABLE DICH_VU (
    MaDV INT IDENTITY(1,1) PRIMARY KEY,
    MaND INT NOT NULL,
    LoaiDichVu NVARCHAR(50) NOT NULL
        CHECK (LoaiDichVu IN (N'Bảo dưỡng', N'Sửa chữa')),
    MoTa NVARCHAR(MAX) NULL,
	TrangThai NVARCHAR(50) DEFAULT N'Chưa hoàn thành'
        CHECK (TrangThai IN (N'Chưa hoàn thành', N'Đã hoàn thành')),
    NgayTao DATETIME DEFAULT GETDATE(),
	MaDaiLy int not null,
	CONSTRAINT FK_DV_DL FOREIGN KEY (MaDaiLy) REFERENCES DAI_LY(MaDaiLy),
    CONSTRAINT FK_DV_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

-- =========================
-- BẢO DƯỠNG
-- =========================
CREATE TABLE BAO_DUONG (
    MaBD INT IDENTITY(1,1) PRIMARY KEY,
    NgayBaoDuong DATETIME DEFAULT GETDATE(),
    MoTa NVARCHAR(255) NULL,
    MaVC INT NULL,
    TrangThai NVARCHAR(50) DEFAULT N'Nhắc nhở'
        CHECK (TrangThai IN (N'Nhắc nhở', N'Đã nhắc nhở', N'Chưa đến hạn')),
    DaNhacNho BIT DEFAULT 0,
    MaND INT NOT NULL,
    CONSTRAINT FK_BD_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND),
    CONSTRAINT FK_BD_VC FOREIGN KEY (MaVC) REFERENCES VOUCHER(MaVC)
);

-- =========================
-- CHI TIẾT BẢO DƯỠNG
-- =========================
CREATE TABLE CHI_TIET_BAO_DUONG (
    MaCTBD INT IDENTITY(1,1) PRIMARY KEY,
    MaBD INT NOT NULL,
    MaXe VARCHAR(10) NULL,
    MoTa NVARCHAR(255) NULL,
	SoLanBaoDuong INT NOT NULL DEFAULT 0,
    CONSTRAINT FK_CTBD_BD FOREIGN KEY (MaBD) REFERENCES BAO_DUONG(MaBD),
    CONSTRAINT FK_CTBD_XE FOREIGN KEY (MaXe) REFERENCES XE(MaXe)
);

-- =========================
-- LỊCH SỬ BẢO DƯỠNG
-- =========================
CREATE TABLE LICH_SU_BAO_DUONG (
    MaLSBD INT IDENTITY(1,1) PRIMARY KEY,
    NgayThucHien DATETIME NOT NULL DEFAULT GETDATE(),
    MaND INT NOT NULL,
    MaXe VARCHAR(10) NOT NULL,
    MaDaiLy INT NOT NULL,
    CONSTRAINT FK_LSBD_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND),
    CONSTRAINT FK_LSBD_XE FOREIGN KEY (MaXe) REFERENCES XE(MaXe),
    CONSTRAINT FK_LSBD_DL FOREIGN KEY (MaDaiLy) REFERENCES DAI_LY(MaDaiLy)
);

-- =========================
-- CHI TIẾT LỊCH SỬ BẢO DƯỠNG
-- =========================
CREATE TABLE CHI_TIET_LS_BAO_DUONG (
    MaCTLSBD INT IDENTITY(1,1) PRIMARY KEY,
    MaLSBD INT NOT NULL,
    MaDaiLy INT NOT NULL,
    NoiDung NVARCHAR(255) NOT NULL,
    ChiPhi DECIMAL(18,0) DEFAULT 0,
    CONSTRAINT FK_CTLSBD_LSBD FOREIGN KEY (MaLSBD) REFERENCES LICH_SU_BAO_DUONG(MaLSBD),
    CONSTRAINT FK_CTLSBD_DL FOREIGN KEY (MaDaiLy) REFERENCES DAI_LY(MaDaiLy)
);

-- =========================
-- GIỎ HÀNG
-- =========================
CREATE TABLE GIO_HANG (
    MaGioHang INT IDENTITY(1,1) PRIMARY KEY,
    MaND INT NOT NULL,
    CONSTRAINT FK_GH_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

CREATE TABLE CHI_TIET_GIO_HANG (
    MaCTGH INT IDENTITY(1,1) PRIMARY KEY,
    MaGioHang INT NOT NULL,
    MaPhuTung VARCHAR(10) NOT NULL,
    HinhAnh VARCHAR(255) NOT NULL,
    SoLuong INT DEFAULT 1 CHECK (SoLuong > 0),
    DonGia DECIMAL(18,0) NOT NULL CHECK (DonGia >= 0),
    CONSTRAINT FK_CTG_GH FOREIGN KEY (MaGioHang) REFERENCES GIO_HANG(MaGioHang),
    CONSTRAINT FK_CTG_PT FOREIGN KEY (MaPhuTung) REFERENCES PHU_TUNG(MaPhuTung),
    CONSTRAINT UQ_GH_PT UNIQUE (MaGioHang, MaPhuTung)
);

-- =========================
-- THÔNG BÁO
-- =========================
CREATE TABLE THONG_BAO (
    MaThongBao INT IDENTITY(1,1) PRIMARY KEY,
    MaND INT NOT NULL,
    TieuDe NVARCHAR(200) NOT NULL,
    NoiDung NVARCHAR(MAX) NOT NULL,
    NgayTao DATETIME DEFAULT GETDATE(),
    CONSTRAINT FK_TB_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

-- =========================
-- OTP
-- =========================
CREATE TABLE OTP_VERIFICATION (
    MaOTP INT IDENTITY(1,1) PRIMARY KEY,
    MaND INT NOT NULL,
    MaOTP_Code VARCHAR(6) NOT NULL,
    NgayHetHan DATETIME NOT NULL,
    CONSTRAINT FK_OTP_ND FOREIGN KEY (MaND) REFERENCES NGUOI_DUNG(MaND)
);

-- ====================================================
--                 DỮ LIỆU MẪU
-- ====================================================

INSERT INTO ADMIN (TenDangNhap, MatKhau, VaiTro)
VALUES
(N'phangiahai', '123456', N'admin'),
(N'phanminhphuc', '123456', N'admin'),
(N'trandieuhavi', '123456', N'admin'),
(N'phanviethoang', '123456', N'admin');
GO

INSERT INTO NGUOI_DUNG (HoTen, Email, DienThoai, NgaySinh, MatKhau, VaiTro, NgayBaoDuong, SoLanBaoDuong, IsLocked)
VALUES
(N'Vi Trần', 'trandieuhavi0307@gmail.com', '0911222333','2001-03-12', 'an1234', 'customer', NULL, 1, 0),
(N'Hải Phan', 'haip59621@gmail.com', '0931951811','2003-01-01', 'Abc123!@#', 'customer', NULL, 4, 0),
(N'Phúc Phan', 'phanminhphuc05@gmail.com', '0988666777','1999-07-20', 'long567', 'customer', NULL, 2, 0),
(N'Hoàng', 'hoang4410viet579@gmail.com', '0909001122','2000-09-15', 'hau999', 'customer', NULL, 0, 0);
GO


INSERT INTO HANG_XE (MaHangXe, TenHangXe, MoTa)
VALUES ('BMW', N'BMW', N'Hãng xe Đức – chất lượng cao');
GO

INSERT INTO LOAI_XE (MaHangXe, MaLoaiXe, TenLoaiXe, MoTa) VALUES
('BMW', 'LX1', N'BMW 5 SERIES', N'Oto hiện đại'),
('BMW', 'LX2', N'BMW Z4', N'Oto cao cấp'),
('BMW', 'LX3', N'BMW I5', N'Oto hiện đại'),
('BMW', 'LX4', N'BMW 3 SERIES', N'Oto siêu cấp');
GO

INSERT INTO XE (MaXe, MaHangXe, MaND, MaLoaiXe, BienSo, DungTich, SoKhung, MauSac, HinhAnh) VALUES
('X001', 'BMW', 1, 'LX1', N'74B1-32188', '1988 cc', 'WBA5X-A1B2C-000000000', N'Đỏ', 's1000rr.png'),
('X002', 'BMW', 2, 'LX4', N'75D51-45678', '1988 cc', 'WBA5X-A1B2C-000000000', N'Xanh ngọc', 's1000rr.png'),
('X003', 'BMW', 3, 'LX3', N'73D1-78910', '1988 cc', 'WBA5X-A1B2C-000000000', N'Trắng', 's1000rr.png'),
('X004', 'BMW', 4, 'LX2', N'73D1-18204', '1988 cc', 'WBA5X-A1B2C-000000000', N'Xanh đen','s1000rr.png');
GO

INSERT INTO DM_PHUTUNG (MaDanhMuc, TenDanhMuc) VALUES
('DM01', N'Phụ tùng thân vỏ'),
('DM02', N'Phụ tùng truyền động'),
('DM03', N'Phụ tùng điện'),
('DM04', N'Phụ tùng động cơ');
GO

INSERT INTO PHU_TUNG 
(MaPhuTung, MaHangXe, MaLoaiXe, TenPhuTung, GiaBan, SoLuong, MoTa, MaDanhMuc, HinhAnh, NhaCC)
VALUES
-- Nhóm 1 (LX1 – DM01)
('PT01','BMW','LX1',N'Ống gió nạp hơi',7000000,10,N'Ống gió cao cấp cho xe BMW','DM01','ong_gio_nap_hoi.png',N'Royal Auto'),
('PT02','BMW','LX1',N'Bộ đèn pha',8500000,7,N'Đèn pha LED siêu sáng','DM01','den_pha.png',N'Royal Auto'),
('PT03','BMW','LX1',N'Động cơ bơm nước DFT',10000000,5,N'Bơm nước DFT chuyên dụng','DM01','bom_nuoc_dft.png',N'Royal Auto'),
('PT04','BMW','LX1',N'Cao su chân máy',1500000,3,N'Cao su chân máy chính hãng','DM01','caosuchanmay.png',N'Royal Auto'),

-- Nhóm 2 (LX2 – DM02)
('PT05','BMW','LX2',N'Mô tơ quạt két nước',8000000,6,N'Mô tơ quạt két nước cao cấp','DM02','mo_to_quat_ket_nuoc.png',N'Royal Auto'),
('PT06','BMW','LX2',N'Động cơ bơm nước AW4020',8500000,4,N'Bơm nước AW4020 chính hãng','DM02','dongcobnhai.png',N'Royal Auto'),
('PT07','BMW','LX2',N'Động cơ bơm nước AW40DB',9000000,4,N'Bơm nước AW40DB chất lượng cao','DM02','dongcobnba.png',N'Royal Auto'),
('PT08','BMW','LX2',N'Giảm xóc sau xe',6000000,8,N'Giảm xóc sau êm ái, bền bỉ','DM02','giam_xoc_sau.png',N'Royal Auto'),

-- Nhóm 3 (LX3 – DM03)
('PT09','BMW','LX3',N'Cây láp trước',5000000,6,N'Cây láp trước chịu lực tốt','DM03','cay_lap_truoc.png',N'Royal Auto'),
('PT10','BMW','LX3',N'Giảm xóc trục',8500000,7,N'Giảm xóc trục độ bền cao','DM03','giamxoctruc.png',N'Royal Auto'),
('PT11','BMW','LX3',N'Heo côn trên',1000000,6,N'Heo côn trên cho xe BMW','DM03','heo_con_tren.png',N'Royal Auto'),
('PT12','BMW','LX3',N'Thước lái BMW',8000000,5,N'Thước lái độ chính xác cao','DM03','thuoc_lai_mercedes.png',N'Royal Auto'),

-- Nhóm 4 (LX4 – DM04)
('PT13','BMW','LX4',N'Hộp điều khiển',7000000,4,N'Hộp điều khiển ECU xe','DM04','hop_dieu_khien.png',N'Royal Auto'),
('PT14','BMW','LX4',N'Đầu DVD xe hơi',7500000,9,N'Đầu DVD xe hơi màn hình lớn','DM04','daudvdxe.png',N'Royal Auto'),
('PT15','BMW','LX4',N'Mô bin đánh lửa',7000000,10,N'Mô bin đánh lửa công suất cao','DM04','mo_bin_danh_lua.png',N'Royal Auto'),
('PT16','BMW','LX4',N'Động cơ bơm nước DFT (bản phụ)',5000000,5,N'Bơm nước DFT loại phụ','DM04','dongcobnbon.png',N'Royal Auto');

INSERT INTO VOUCHER (MaND, LoaiVoucher, HanSuDung, TrangThai) VALUES
(1, N'Giảm 50% phí vận chuyển', '2025-12-31', N'Còn hiệu lực'),
(2, N'Miễn phí Vận chuyển', '2025-10-20', N'Còn hiệu lực'),
(3, N'Giảm 50% phí vận chuyển', '2025-06-01', N'Đã sử dụng'),
(4, N'Miễn phí Vận chuyển', '2025-12-30', N'Còn hiệu lực');
GO

INSERT INTO PHUONG_THUC_VAN_CHUYEN (TenPTVC, GiaVanChuyen, SoNgayDuKien, MoTa) VALUES
(N'Giao hàng nhanh', 500000, 1, N'Chỉ áp dụng nội thành'),
(N'Giao tiết kiệm', 450000, 5, N'Rẻ nhất');
GO



INSERT INTO DAI_LY (TenDaiLy, DiaChi, SoDienThoai, GioLamViec, MoTa) VALUES
(N'BMW DATRACO 1', N'18-20 HOÀNG HOA THÁM, THANH KHÊ, ĐÀ NẴNG', '0901122334', N'8h–18h', N'Đại lý chính hãng'),
(N'BMW DATRACO 2', N'168-170 HÙNG VƯƠNG, HẢI CHÂU, ĐÀ NẴNG', '0905566778', N'8h–17h', N'Service Center'),
(N'BMW Tường Phát 1', N'179 PHAN CHÂU TRINH, HẢI CHÂU, ĐÀ NẴNG', '0911998877', N'9h–18h', N'Bảo dưỡng nhanh'),
(N'BMW Tường Phát 2', N'LÔ 82 ĐIỆN BIÊN PHỦ, THANH KHÊ, ĐÀ NẴNG', '0903222111', N'8h–17h', N'Bảo dưỡng – sửa chữa');
GO

INSERT INTO BAO_DUONG (MoTa, MaVC, MaND, TrangThai) VALUES
(N'Kiểm tra định kỳ', 1, 1, N'Nhắc nhở'),
(N'Thay nhớt + lọc', 2, 2, N'Nhắc nhở'),
(N'Vệ sinh kim phun', NULL, 3, N'Đã nhắc nhở');
GO

INSERT INTO DICH_VU (MaND, LoaiDichVu, MoTa, MaDaiLy) VALUES
(1, N'Bảo dưỡng', N'Thay nhớt định kỳ, kiểm tra phanh.', 1),
(2, N'Sửa chữa', N'Sửa chữa rò rỉ động cơ nước làm mát.', 1),
(3, N'Bảo dưỡng', N'Ve sinh lọc gió, siết lại bulong.', 1),
(4, N'Sửa chữa', N'Thay cao su chân máy.', 1);
GO


INSERT INTO CHI_TIET_BAO_DUONG (MaBD, MaXe, MoTa) VALUES
(1, 'X001', N'Kiểm tra tổng quát'),
(2, 'X002', N'Thay nhớt Liqui Moly'),
(3, 'X003', N'Ve sinh buồng đốt');
GO

INSERT INTO LICH_SU_BAO_DUONG (NgayThucHien, MaND, MaXe, MaDaiLy)
VALUES
('2025-5-06', 1, 'X001', 1),
('2025-8-09', 1, 'X001', 1);
GO

INSERT INTO CHI_TIET_LS_BAO_DUONG (MaLSBD, MaDaiLy, NoiDung, ChiPhi)
VALUES
(1, 1, N'Thay nhớt máy', 250000),
(2, 2, N'Vệ sinh lọc gió', 150000);
GO

INSERT INTO GIO_HANG (MaND) VALUES
(1),(2),(3),(4);
GO

INSERT INTO THONG_BAO (MaND, TieuDe, NoiDung)
VALUES
(1, N'Thông báo bảo dưỡng', N'Xe bạn sắp đến hạn bảo dưỡng định kỳ.'),
(2, N'Khuyến mãi phụ tùng', N'Giảm 20% cho bộ đèn pha trong tuần này.'),
(3, N'Xác nhận đơn hàng', N'Đơn hàng PT03 của bạn đang được chuẩn bị.'),
(4, N'Cảnh báo an toàn', N'Phát hiện lỗi hệ thống phanh, vui lòng kiểm tra ngay.');
GO

INSERT INTO OTP_VERIFICATION (MaND, MaOTP_Code, NgayHetHan)
VALUES 
(1, '482915', '2025-12-10 23:59:59'),
(2, '930124', '2025-12-11 23:59:59'),
(3, '715602', '2025-12-12 23:59:59'),
(4, '119843', '2025-12-13 23:59:59');
GO


