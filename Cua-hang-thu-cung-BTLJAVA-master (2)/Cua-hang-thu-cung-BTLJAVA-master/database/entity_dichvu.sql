-- =====================================
-- TẠO DATABASE
-- =====================================
CREATE DATABASE IF NOT EXISTS cuahangthucung;
USE cuahangthucung;

SET FOREIGN_KEY_CHECKS = 0;

-- =====================================
-- XÓA BẢNG (chạy lại không lỗi)
-- =====================================
DROP TABLE IF EXISTS ChiTietDonHang;
DROP TABLE IF EXISTS PhieuXuatKho;
DROP TABLE IF EXISTS PhieuNhapKho;
DROP TABLE IF EXISTS LichHen;
DROP TABLE IF EXISTS DonHang;
DROP TABLE IF EXISTS SanPham;
DROP TABLE IF EXISTS DichVu;
DROP TABLE IF EXISTS KhuyenMai;
DROP TABLE IF EXISTS NhaCungCap;

SET FOREIGN_KEY_CHECKS = 1;

-- =====================================
-- NHÀ CUNG CẤP
-- =====================================
CREATE TABLE NhaCungCap (
    MaNCC VARCHAR(50) PRIMARY KEY,     -- mã nhà cung cấp
    TenNCC VARCHAR(100),               -- tên nhà cung cấp
    SDT VARCHAR(15),                   -- số điện thoại
    DiaChi VARCHAR(255)                -- địa chỉ
);

-- =====================================
-- SẢN PHẨM
-- =====================================
CREATE TABLE SanPham (
    MaSP VARCHAR(50) PRIMARY KEY,      -- mã sản phẩm
    TenSP VARCHAR(100),                -- tên sản phẩm

    Gia DOUBLE CHECK (Gia >= 0),       -- giá >= 0
    SoLuong INT CHECK (SoLuong >= 0),  -- số lượng >= 0

    HanSuDung DATE,                    -- hạn sử dụng
    ViTri VARCHAR(100),                -- vị trí

    MaNCC VARCHAR(50),                 -- nhà cung cấp

    FOREIGN KEY (MaNCC) REFERENCES NhaCungCap(MaNCC)
);

-- =====================================
-- DỊCH VỤ
-- =====================================
CREATE TABLE DichVu (
    MaDV VARCHAR(50) PRIMARY KEY,      -- mã dịch vụ
    TenDV VARCHAR(100),                -- tên dịch vụ
    Gia DOUBLE CHECK (Gia >= 0),       -- giá
    MoTa TEXT                          -- mô tả
);

-- =====================================
-- ĐƠN HÀNG
-- =====================================
CREATE TABLE DonHang (
    MaDH VARCHAR(50) PRIMARY KEY,      -- mã đơn hàng
    MaKH VARCHAR(50),                  -- khách hàng
    MaNV VARCHAR(50),                  -- nhân viên

    NgayTao DATE,                      -- ngày tạo
    TongTien DOUBLE CHECK (TongTien >= 0),

    TrangThai VARCHAR(20)              -- Pending, Confirmed...
);

-- =====================================
-- CHI TIẾT ĐƠN HÀNG (KHÓA KÉP)
-- =====================================
CREATE TABLE ChiTietDonHang (
    MaDH VARCHAR(50),                  -- mã đơn
    MaSP VARCHAR(50),                  -- sản phẩm

    SoLuong INT CHECK (SoLuong > 0),   -- > 0
    DonGia DOUBLE CHECK (DonGia >= 0), -- >= 0

    PRIMARY KEY (MaDH, MaSP),

    FOREIGN KEY (MaDH) REFERENCES DonHang(MaDH)
        ON DELETE CASCADE,

    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- =====================================
-- PHIẾU NHẬP KHO (KHÓA KÉP)
-- =====================================
CREATE TABLE PhieuNhapKho (
    MaPhieu VARCHAR(50),               -- mã phiếu
    MaSP VARCHAR(50),                  -- sản phẩm

    NgayNhap DATE,                     -- ngày nhập
    SoLuong INT,                       -- số lượng

    PRIMARY KEY (MaPhieu, MaSP),

    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- =====================================
-- PHIẾU XUẤT KHO (KHÓA KÉP)
-- =====================================
CREATE TABLE PhieuXuatKho (
    MaPhieu VARCHAR(50),               -- mã phiếu
    MaSP VARCHAR(50),                  -- sản phẩm

    NgayXuat DATE,                     -- ngày xuất
    SoLuong INT,                       -- số lượng

    MaNV VARCHAR(50),                  -- nhân viên
    NhaCC VARCHAR(50),                 -- nơi nhận

    PRIMARY KEY (MaPhieu, MaSP),

    FOREIGN KEY (MaSP) REFERENCES SanPham(MaSP)
);

-- =====================================
-- LỊCH HẸN
-- =====================================
CREATE TABLE LichHen (
    MaLich VARCHAR(50) PRIMARY KEY,    -- mã lịch

    MaKH VARCHAR(50),                  -- khách hàng
    MaPet VARCHAR(50),                 -- thú cưng
    MaNV VARCHAR(50),                  -- nhân viên

    MaDV VARCHAR(50),                  -- dịch vụ

    ThoiGian DATETIME,                 -- thời gian
    TrangThai VARCHAR(20),             -- trạng thái

    FOREIGN KEY (MaDV) REFERENCES DichVu(MaDV)
);

-- =====================================
-- KHUYẾN MÃI
-- =====================================
CREATE TABLE KhuyenMai (
    MaKM VARCHAR(50) PRIMARY KEY,      -- mã KM
    TenKM VARCHAR(100),                -- tên chương trình

    GiamGia DOUBLE CHECK (GiamGia BETWEEN 0 AND 100),

    NgayBD DATE,                       -- bắt đầu
    NgayKT DATE                        -- kết thúc
);