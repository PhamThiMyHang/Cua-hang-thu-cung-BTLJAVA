-- =====================================
-- TẠO DATABASE
-- =====================================

CREATE DATABASE IF NOT EXISTS cuahangthucung
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE cuahangthucung;

-- Tắt kiểm tra khóa ngoại để xóa bảng không lỗi
SET FOREIGN_KEY_CHECKS = 0;

-- =====================================
-- XÓA BẢNG
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

-- Bật lại khóa ngoại
SET FOREIGN_KEY_CHECKS = 1;

-- =====================================
-- BẢNG NHÀ CUNG CẤP
-- =====================================

CREATE TABLE NhaCungCap (

    -- Mã nhà cung cấp
    MaNCC VARCHAR(50) PRIMARY KEY,

    -- Tên nhà cung cấp
    TenNCC VARCHAR(100) NOT NULL,

    -- Số điện thoại
    SDT VARCHAR(15) NOT NULL UNIQUE,

    -- Địa chỉ
    DiaChi VARCHAR(255) NOT NULL

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG SẢN PHẨM
-- =====================================

CREATE TABLE SanPham (

    -- Mã sản phẩm
    MaSP VARCHAR(50) PRIMARY KEY,

    -- Tên sản phẩm
    TenSP VARCHAR(100) NOT NULL,

    -- Giá sản phẩm
    Gia DECIMAL(10,2) NOT NULL
        CHECK (Gia >= 0),

    -- Số lượng tồn kho
    SoLuong INT NOT NULL DEFAULT 0
        CHECK (SoLuong >= 0),

    -- Hạn sử dụng
    HanSuDung DATE,

    -- Vị trí trong kho
    ViTri VARCHAR(100),

    -- Nhà cung cấp
    MaNCC VARCHAR(50) NOT NULL,

    -- Khóa ngoại
    FOREIGN KEY (MaNCC)
        REFERENCES NhaCungCap(MaNCC)

        -- Xóa NCC -> xóa sản phẩm liên quan
        ON DELETE CASCADE

        -- Sửa mã NCC -> tự cập nhật
        ON UPDATE CASCADE

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG DỊCH VỤ
-- =====================================

CREATE TABLE DichVu (

    -- Mã dịch vụ
    MaDV VARCHAR(50) PRIMARY KEY,

    -- Tên dịch vụ
    TenDV VARCHAR(100) NOT NULL,

    -- Giá dịch vụ
    Gia DECIMAL(10,2) NOT NULL
        CHECK (Gia >= 0),

    -- Mô tả
    MoTa TEXT

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG ĐƠN HÀNG
-- =====================================

CREATE TABLE DonHang (

    -- Mã đơn hàng
    MaDH VARCHAR(50) PRIMARY KEY,

    -- Mã khách hàng
    MaKH VARCHAR(50),

    -- Mã nhân viên
    MaNV VARCHAR(50),

    -- Ngày tạo
    NgayTao DATE NOT NULL,

    -- Tổng tiền
    TongTien DECIMAL(10,2) NOT NULL
        CHECK (TongTien >= 0),

    -- Trạng thái đơn hàng
    TrangThai ENUM(
        'PENDING',
        'CONFIRMED',
        'IN_PROGRESS',
        'DONE',
        'CANCEL'
    ) DEFAULT 'PENDING'

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG CHI TIẾT ĐƠN HÀNG
-- =====================================

CREATE TABLE ChiTietDonHang (

    -- Mã đơn hàng
    MaDH VARCHAR(50),

    -- Mã sản phẩm
    MaSP VARCHAR(50),

    -- Số lượng mua
    SoLuong INT NOT NULL
        CHECK (SoLuong > 0),

    -- Đơn giá
    DonGia DECIMAL(10,2) NOT NULL
        CHECK (DonGia >= 0),

    -- Khóa chính kép
    PRIMARY KEY (MaDH, MaSP),

    -- Liên kết bảng DonHang
    FOREIGN KEY (MaDH)
        REFERENCES DonHang(MaDH)

        -- Xóa đơn -> xóa chi tiết đơn
        ON DELETE CASCADE

        ON UPDATE CASCADE,

    -- Liên kết bảng SanPham
    FOREIGN KEY (MaSP)
        REFERENCES SanPham(MaSP)

        -- Xóa sản phẩm -> xóa chi tiết liên quan
        ON DELETE CASCADE

        ON UPDATE CASCADE

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG PHIẾU NHẬP KHO
-- =====================================

CREATE TABLE PhieuNhapKho (

    -- Mã phiếu nhập
    MaPhieu VARCHAR(50),

    -- Mã sản phẩm
    MaSP VARCHAR(50),

    -- Ngày nhập
    NgayNhap DATE NOT NULL,

    -- Số lượng nhập
    SoLuong INT NOT NULL
        CHECK (SoLuong > 0),

    -- Khóa chính kép
    PRIMARY KEY (MaPhieu, MaSP),

    -- Khóa ngoại sản phẩm
    FOREIGN KEY (MaSP)
        REFERENCES SanPham(MaSP)

        ON DELETE CASCADE

        ON UPDATE CASCADE

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG PHIẾU XUẤT KHO
-- =====================================

CREATE TABLE PhieuXuatKho (

    -- Mã phiếu xuất
    MaPhieu VARCHAR(50),

    -- Mã sản phẩm
    MaSP VARCHAR(50),

    -- Ngày xuất
    NgayXuat DATE NOT NULL,

    -- Số lượng xuất
    SoLuong INT NOT NULL
        CHECK (SoLuong > 0),

    -- Nhân viên xuất kho
    MaNV VARCHAR(50),

    -- Nơi nhận hàng
    NoiNhan VARCHAR(100),

    -- Khóa chính kép
    PRIMARY KEY (MaPhieu, MaSP),

    -- Khóa ngoại sản phẩm
    FOREIGN KEY (MaSP)
        REFERENCES SanPham(MaSP)

        ON DELETE CASCADE

        ON UPDATE CASCADE

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG LỊCH HẸN
-- =====================================

CREATE TABLE LichHen (

    -- Mã lịch hẹn
    MaLich VARCHAR(50) PRIMARY KEY,

    -- Mã khách hàng
    MaKH VARCHAR(50),

    -- Mã thú cưng
    MaPet VARCHAR(50),

    -- Mã nhân viên
    MaNV VARCHAR(50),

    -- Mã dịch vụ
    MaDV VARCHAR(50) NOT NULL,

    -- Thời gian lịch hẹn
    ThoiGian DATETIME NOT NULL,

    -- Trạng thái lịch hẹn
    TrangThai ENUM(
        'PENDING',
        'CONFIRMED',
        'IN_PROGRESS',
        'DONE',
        'CANCEL'
    ) DEFAULT 'PENDING',

    -- Liên kết bảng dịch vụ
    FOREIGN KEY (MaDV)
        REFERENCES DichVu(MaDV)

        ON DELETE CASCADE

        ON UPDATE CASCADE

)
ENGINE=InnoDB;

-- =====================================
-- BẢNG KHUYẾN MÃI
-- =====================================

CREATE TABLE KhuyenMai (

    -- Mã khuyến mãi
    MaKM VARCHAR(50) PRIMARY KEY,

    -- Tên khuyến mãi
    TenKM VARCHAR(100) NOT NULL,

    -- Phần trăm giảm giá
    GiamGia DECIMAL(5,2) NOT NULL
        CHECK (GiamGia BETWEEN 0 AND 100),

    -- Ngày bắt đầu
    NgayBD DATE NOT NULL,

    -- Ngày kết thúc
    NgayKT DATE NOT NULL,

    -- Ngày kết thúc phải >= ngày bắt đầu
    CHECK (NgayKT >= NgayBD)

)
ENGINE=InnoDB;