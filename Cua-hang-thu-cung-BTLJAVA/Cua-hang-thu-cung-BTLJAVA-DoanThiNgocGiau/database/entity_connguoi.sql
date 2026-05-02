-- ================================
-- TẠO DATABASE
-- ================================
ALTER DATABASE cuahangthucung
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE cuahangthucung;

-- ================================
-- USERS: tài khoản đăng nhập
-- ================================
CREATE TABLE IF NOT EXISTS USERS (
    UserID INT AUTO_INCREMENT PRIMARY KEY,         -- mã người dùng
    Username VARCHAR(50) NOT NULL UNIQUE,          -- tên đăng nhập
    Password VARCHAR(255) NOT NULL,                -- mật khẩu
    Status ENUM('ACTIVE', 'INACTIVE') NOT NULL DEFAULT 'ACTIVE'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- ROLES: danh sách quyền
-- ================================
CREATE TABLE IF NOT EXISTS ROLES (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,         -- mã quyền
    RoleName VARCHAR(20) NOT NULL UNIQUE           -- ADMIN, STAFF, KTV, CUSTOMER
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- USER_ROLES: nhiều quyền cho 1 user
-- ================================
CREATE TABLE IF NOT EXISTS USER_ROLES (
    UserID INT,
    RoleID INT,

    PRIMARY KEY (UserID, RoleID),

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE,

    FOREIGN KEY (RoleID) REFERENCES ROLES(RoleID)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- NHANVIEN: nhân viên
-- ================================
CREATE TABLE IF NOT EXISTS NHANVIEN (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,           -- mã nhân viên
    TenNV VARCHAR(100) NOT NULL,                   -- tên nhân viên
    SDT VARCHAR(15) UNIQUE,                        -- số điện thoại
    DiaChi VARCHAR(255),                           -- địa chỉ
    ChucVu ENUM('STAFF', 'KTV') NOT NULL,          -- STAFF hoặc KTV
    UserID INT UNIQUE,                             -- liên kết tài khoản

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- KHACHHANG: khách hàng
-- ================================
CREATE TABLE IF NOT EXISTS KHACHHANG (
    MaKH INT AUTO_INCREMENT PRIMARY KEY,           -- mã khách hàng
    TenKH VARCHAR(100) NOT NULL,                   -- tên khách hàng
    SDT VARCHAR(15) UNIQUE,                        -- số điện thoại
    DiaChi VARCHAR(255),                           -- địa chỉ

    LoaiKH ENUM('THUONG', 'VIP', 'SI', 'LE') NOT NULL DEFAULT 'THUONG',
    DiemTichLuy INT NOT NULL DEFAULT 0,

    UserID INT UNIQUE,                             -- liên kết tài khoản

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- HOSO_NHANVIEN: hồ sơ nhân viên
-- ================================
CREATE TABLE IF NOT EXISTS HOSO_NHANVIEN (
    MaHoSo INT AUTO_INCREMENT PRIMARY KEY,         -- mã hồ sơ
    MaNV INT NOT NULL,                             -- nhân viên
    BangCap VARCHAR(255),                          -- bằng cấp
    KinhNghiem TEXT,                               -- kinh nghiệm
    TrinhDo VARCHAR(100),                          -- trình độ

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- LICHTRUC: lịch làm việc
-- ================================
CREATE TABLE IF NOT EXISTS LICHTRUC (
    ID_LichTruc INT AUTO_INCREMENT PRIMARY KEY,    -- mã lịch
    MaNV INT NOT NULL,                             -- nhân viên
    Ngay DATE NOT NULL,                            -- ngày làm

    CaLamViec VARCHAR(20) NOT NULL,                -- ca làm
    GioBatDau TIME NOT NULL,                       -- giờ bắt đầu
    GioKetThuc TIME NOT NULL,                      -- giờ kết thúc

    UNIQUE (MaNV, Ngay, CaLamViec),

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- CHAMCONG: chấm công
-- ================================
CREATE TABLE IF NOT EXISTS CHAMCONG (
    MaCC INT AUTO_INCREMENT PRIMARY KEY,           -- mã chấm công
    MaNV INT NOT NULL,                             -- nhân viên
    Ngay DATE NOT NULL,                            -- ngày

    GioVao TIME,
    GioRa TIME,

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- KPI_THUONGPHAT: thưởng phạt
-- ================================
CREATE TABLE IF NOT EXISTS KPI_THUONGPHAT (
    MaKPI INT AUTO_INCREMENT PRIMARY KEY,          -- mã KPI
    MaNV INT NOT NULL,                             -- nhân viên
    Thang VARCHAR(7) NOT NULL,                     -- tháng YYYY-MM

    Thuong DECIMAL(10,2) NOT NULL DEFAULT 0,
    Phat DECIMAL(10,2) NOT NULL DEFAULT 0,

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ================================
-- LICH_SU_DANG_NHAP: lịch sử đăng nhập
-- ================================
CREATE TABLE IF NOT EXISTS LICH_SU_DANG_NHAP (
    MaLS INT AUTO_INCREMENT PRIMARY KEY,           -- mã lịch sử
    UserID INT NOT NULL,                           -- người đăng nhập
    ThoiGian DATETIME DEFAULT CURRENT_TIMESTAMP,   -- thời gian
    TrangThai VARCHAR(20),

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;