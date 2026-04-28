-- ================================
-- TẠO DATABASE
-- ================================
USE cuahangthucung;

-- ================================
-- USERS: tài khoản đăng nhập
-- ================================
CREATE TABLE IF NOT EXISTS USERS (
    UserID INT AUTO_INCREMENT PRIMARY KEY,         -- mã người dùng
    Username VARCHAR(50) NOT NULL UNIQUE,          -- tên đăng nhập
    Password VARCHAR(255) NOT NULL,                -- mật khẩu
    Status VARCHAR(20) NOT NULL DEFAULT 'Active',  -- trạng thái

    CHECK (Status IN ('Active', 'Inactive'))
);

-- ================================
-- ROLES: danh sách quyền
-- ================================
CREATE TABLE IF NOT EXISTS ROLES (
    RoleID INT AUTO_INCREMENT PRIMARY KEY,         -- mã quyền
    RoleName VARCHAR(20) NOT NULL UNIQUE           -- ADMIN, STAFF, KTV, CUSTOMER
);

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
);

-- ================================
-- NHANVIEN: nhân viên
-- ================================
CREATE TABLE IF NOT EXISTS NHANVIEN (
    MaNV INT AUTO_INCREMENT PRIMARY KEY,           -- mã nhân viên
    TenNV VARCHAR(100) NOT NULL,                   -- tên nhân viên
    SDT VARCHAR(15) UNIQUE,                        -- số điện thoại
    DiaChi VARCHAR(255),                           -- địa chỉ
    ChucVu VARCHAR(20) NOT NULL,                   -- STAFF hoặc KTV
    UserID INT UNIQUE,                             -- liên kết tài khoản

    CHECK (ChucVu IN ('STAFF', 'KTV')),

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE SET NULL ON UPDATE CASCADE
);

-- ================================
-- KHACHHANG: khách hàng
-- ================================
CREATE TABLE IF NOT EXISTS KHACHHANG (
    MaKH INT AUTO_INCREMENT PRIMARY KEY,           -- mã khách hàng
    TenKH VARCHAR(100) NOT NULL,                   -- tên khách hàng
    SDT VARCHAR(15) UNIQUE,                        -- số điện thoại
    DiaChi VARCHAR(255),                           -- địa chỉ

    LoaiKH VARCHAR(20) NOT NULL DEFAULT 'Thuong',  -- loại khách
    DiemTichLuy INT NOT NULL DEFAULT 0,            -- điểm tích luỹ

    UserID INT UNIQUE,                             -- liên kết tài khoản

    CHECK (LoaiKH IN ('Thuong', 'VIP', 'Si', 'Le')),
    CHECK (DiemTichLuy >= 0),

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE SET NULL ON UPDATE CASCADE
);

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
);

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

    CHECK (GioBatDau < GioKetThuc),

    UNIQUE (MaNV, Ngay, CaLamViec),

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ================================
-- CHAMCONG: chấm công
-- ================================
CREATE TABLE IF NOT EXISTS CHAMCONG (
    MaCC INT AUTO_INCREMENT PRIMARY KEY,           -- mã chấm công
    MaNV INT NOT NULL,                             -- nhân viên
    Ngay DATE NOT NULL,                            -- ngày

    GioVao TIME,
    GioRa TIME,

    CHECK (GioVao < GioRa),

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ================================
-- KPI_THUONGPHAT: thưởng phạt
-- ================================
CREATE TABLE IF NOT EXISTS KPI_THUONGPHAT (
    MaKPI INT AUTO_INCREMENT PRIMARY KEY,          -- mã KPI
    MaNV INT NOT NULL,                             -- nhân viên
    Thang VARCHAR(7) NOT NULL,                     -- tháng YYYY-MM

    Thuong DECIMAL(10,2) NOT NULL DEFAULT 0,
    Phat DECIMAL(10,2) NOT NULL DEFAULT 0,

    CHECK (Thuong >= 0),
    CHECK (Phat >= 0),

    FOREIGN KEY (MaNV) REFERENCES NHANVIEN(MaNV)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- ================================
-- LICH_SU_DANG_NHAP: lịch sử đăng nhập
-- ================================
CREATE TABLE IF NOT EXISTS LICH_SU_DANG_NHAP (
    MaLS INT AUTO_INCREMENT PRIMARY KEY,           -- mã lịch sử
    UserID INT NOT NULL,                           -- người đăng nhập
    ThoiGian DATETIME DEFAULT CURRENT_TIMESTAMP,   -- thời gian
    TrangThai VARCHAR(20),                         -- thành công / thất bại

    FOREIGN KEY (UserID) REFERENCES USERS(UserID)
        ON DELETE CASCADE ON UPDATE CASCADE
);