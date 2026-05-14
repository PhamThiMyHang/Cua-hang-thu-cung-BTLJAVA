
 
CREATE DATABASE IF NOT EXISTS cuahangthucung 
CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE cuahangthucung;

-- 1. Bảng Loại Chuồng
CREATE TABLE LOAICHUONG (
    MaLoaiChuong VARCHAR(20) PRIMARY KEY,
    TenLoai VARCHAR(100),
    GiaThue DECIMAL(18, 2) NOT NULL DEFAULT 0,
    SoLuong INT NOT NULL DEFAULT 0,
    CONSTRAINT CHK_LoaiChuong CHECK (GiaThue >= 0 AND SoLuong >= 0)
);

-- 2. Bảng Chuồng (Cập nhật trạng thái ENUM trước 5/3)
CREATE TABLE CHUONG (
    MaChuong VARCHAR(20) PRIMARY KEY,
    MaLoaiChuong VARCHAR(20) NOT NULL,
    TrangThai ENUM('Sua_Chua', 'Trong', 'Kin'),
    CONSTRAINT FK_Chuong_LoaiChuong FOREIGN KEY (MaLoaiChuong) REFERENCES LOAICHUONG(MaLoaiChuong)
);

-- 3. Bảng Pet (Cấu trúc trước khi đổi sang ENUM và INT)
CREATE TABLE PET (
    MaPet VARCHAR(20) PRIMARY KEY,
    TenPet VARCHAR(100),
    Giong VARCHAR(100),
    Tuoi INT NOT NULL DEFAULT 0,
    Gia DECIMAL(18, 2) NOT NULL DEFAULT 0,
    CanNang FLOAT NOT NULL DEFAULT 0,
    TinhTrang VARCHAR(100), -- Vẫn là VARCHAR trước ngày 5/3
    MaChuong VARCHAR(20),
    MaKH VARCHAR(20) NOT NULL, -- Vẫn là VARCHAR trước ngày 3/5
    MaNV VARCHAR(20) NOT NULL, -- Vẫn là VARCHAR trước ngày 3/5
    NgayGui DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    NgayTra DATETIME,
    CONSTRAINT FK_Pet_Chuong FOREIGN KEY (MaChuong) REFERENCES CHUONG(MaChuong),
    CONSTRAINT CHK_Gia_HopLe CHECK (Gia >= 0),
    CONSTRAINT CHK_CanNang_HopLe CHECK (CanNang > 0 AND CanNang <= 150)
);

-- 4. Bảng Hình ảnh Pet
CREATE TABLE PET_IMAGE (
    MaImg INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    MaPet VARCHAR(20) NOT NULL,
    Url VARCHAR(500) NOT NULL,
    ThoiGianDangTai DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT FK_PetImage_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);

-- 5. Bảng Lịch sử sức khỏe
CREATE TABLE LICHSU_SUC_KHOE (
    MaLS INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    MaPet VARCHAR(20) NOT NULL,
    MoTa VARCHAR(300) NOT NULL,
    Ngay DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Loai ENUM('Vaccine', 'Benh', 'Kham') NOT NULL,
    CONSTRAINT FK_LichSu_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);
 
 
-- 5/2/2026: Cập nhật lại thuộc tính tình trạng thành enum

alter table PET 
drop column TinhTrang;
 
alter table pet
 add column TinhTrang enum('Binh thuong', 'Benh');

 -- 3/5/2026: Cập nhật lại khóa ngoại cho Pet
  -- chạy lại lệnh CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci; để chuyển qua dùng tiếng Việt
 -- Cập nhật lại kiểu dữ liệu của MaKH và MaNv
  alter table PET 
  MODIFY COLUMN MaKH INT NOT NULL,
  MODIFY COLUMN MaNV INT NOT NULL;
  
 alter table PET 
 Add CONSTRAINT FK_PET_KHACHHANG
 foreign key (MaKH) references KHACHHANG(MaKH);
 alter table pet
 ADD constraint FK_PET_NHANVIEN
 foreign key(MaNV) references NHANVIEN(MaNV);


