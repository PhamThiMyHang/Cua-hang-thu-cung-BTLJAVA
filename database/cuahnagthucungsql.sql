-- Tao db--
create database if not exists cuahangthucung
-- Su dung tieng Viet--
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
-- su dung db--
USE cuahangthucung;
CREATE TABLE if not exists LOAICHUONG (
    MaLoaiChuong VARCHAR(20) PRIMARY KEY,
    TenLoai VARCHAR(100),
    GiaThue DECIMAL(18, 2) DEFAULT 0 CHECK (GiaThue >= 0),
    SoLuong INT DEFAULT 0 CHECK (SoLuong >= 0)
);
CREATE TABLE if not exists CHUONG (
    MaChuong VARCHAR(20) PRIMARY KEY,
    MaLoaiChuong VARCHAR(20),
    TrangThai VARCHAR(50), 
    CONSTRAINT FK_Chuong_LoaiChuong
    FOREIGN KEY (MaLoaiChuong) REFERENCES LOAICHUONG(MaLoaiChuong)
);
CREATE TABLE PET (
    MaPet VARCHAR(20) PRIMARY KEY,
    TenPet VARCHAR(100),
    Giong VARCHAR(50),
    Tuoi INT CHECK (Tuoi >= 0),
    Gia DECIMAL(18, 2) DEFAULT 0 CHECK (Gia >= 0),
    CanNang FLOAT,
    TinhTrang VARCHAR(100),
    MaChuong VARCHAR(20),
    MaKH VARCHAR(20), 
    MaNV VARCHAR(20),
    CONSTRAINT FK_Pet_Chuong FOREIGN KEY (MaChuong) REFERENCES CHUONG(MaChuong)
);
CREATE TABLE PET_IMAGE (
    MaImg VARCHAR(20) PRIMARY KEY,
    MaPet VARCHAR(20),
    Url VARCHAR(500),
    ThoiGianDangTai DATETIME DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_PetImage_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);
CREATE TABLE LICHSU_SUC_KHOE (
    MaLS VARCHAR(20) PRIMARY KEY,
    MaPet VARCHAR(20),
    MoTa VARCHAR(300), 
    Ngay DATETIME DEFAULT CURRENT_TIMESTAMP,
    Loai ENUM('Vaccine', 'Benh', 'Kham'),
    CONSTRAINT FK_LichSu_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);