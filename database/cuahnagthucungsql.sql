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
    MaImg int not null primary key auto_increment,
    MaPet VARCHAR(20) not null,
    Url VARCHAR(500) not null,
    ThoiGianDangTai DATETIME DEFAULT CURRENT_TIMESTAMP not null,
    CONSTRAINT FK_PetImage_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);



CREATE TABLE LICHSU_SUC_KHOE (
    MaLS int not null auto_increment PRIMARY KEY ,
    MaPet VARCHAR(20) not null ,
    MoTa VARCHAR(300) not null,
    Ngay DATETIME DEFAULT CURRENT_TIMESTAMP not null  ,
    Loai ENUM('Vaccine', 'Benh', 'Kham') not null,
    CONSTRAINT FK_LichSu_Pet FOREIGN KEY (MaPet) REFERENCES PET(MaPet)
);
-- cap nhat bo sung cac thuoc tinh cho pet

-- 1. Bổ sung 2 cột mới: NgayGui và NgayTra
ALTER TABLE PET
    ADD COLUMN NgayGui DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    ADD COLUMN NgayTra DATETIME;

-- 2. Cập nhật các cột hiện có thành NOT NULL (để khớp với @NotBlank/@NotNull trong Java)
ALTER TABLE PET
    MODIFY COLUMN TenPet VARCHAR(100) NOT NULL,
    MODIFY COLUMN Giong VARCHAR(100) NOT NULL,
    MODIFY COLUMN Tuoi INT NOT NULL,
    MODIFY COLUMN Gia DECIMAL(18, 2) NOT NULL,
    MODIFY COLUMN CanNang FLOAT NOT NULL,
    MODIFY COLUMN TinhTrang VARCHAR(100) NOT NULL,
    MODIFY COLUMN MaChuong VARCHAR(20) NOT NULL,
    MODIFY COLUMN MaKH VARCHAR(20) NOT NULL,
    MODIFY COLUMN MaNV VARCHAR(20) NOT NULL;

-- 3. Cập nhật ràng buộc CHECK(CHK) cho Cân nặng (khớp với @Positive và @Max trong Java)
ALTER TABLE PET
    ADD CONSTRAINT CHK_CanNang CHECK (CanNang > 0 AND CanNang <= 150);

-- 4. Cập nhật lại ràng buộc CHECK cho Giá (khớp với @DecimalMin > 0)
-- Lưu ý: Nếu đã có ràng buộc cũ, bạn nên DROP nó trước rồi ADD mới
SHOW CREATE TABLE PET;

-- 1. Chỉnh sửa cột Gia để có giá trị mặc định là 0
ALTER TABLE PET
    MODIFY COLUMN Gia DECIMAL(18, 2) NOT NULL DEFAULT 0;

-- Thay 'pet_chk_2' bằng tên thật bạn vừa tìm thấy ở Bước 1
ALTER TABLE PET DROP CONSTRAINT pet_chk_1;
ALTER TABLE PET DROP CONSTRAINT pet_chk_2;
ALTER TABLE PET DROP CONSTRAINT FK_Pet_Chuong;
-- 1. Cập nhật các cột bắt buộc (NOT NULL) để khớp với Java @NotBlank/@NotNull
ALTER TABLE PET
    MODIFY COLUMN TenPet VARCHAR(100) NOT NULL,
    MODIFY COLUMN Giong VARCHAR(100) NOT NULL,
    MODIFY COLUMN Tuoi INT NOT NULL,
    MODIFY COLUMN Gia DECIMAL(18, 2) NOT NULL ,
    MODIFY COLUMN CanNang FLOAT NOT NULL,
    MODIFY COLUMN TinhTrang VARCHAR(100) NOT NULL,
    MODIFY COLUMN MaChuong VARCHAR(20) NOT NULL,
    MODIFY COLUMN MaKH VARCHAR(20) NOT NULL,
    MODIFY COLUMN MaNV VARCHAR(20) NOT NULL;

ALTER TABLE PET DROP CONSTRAINT FK_Pet_Chuong;
ALTER TABLE PET
    MODIFY COLUMN MaChuong VARCHAR(20);

ALTER TABLE PET
    MODIFY COLUMN TinhTrang VARCHAR(100);

ALTER TABLE PET
    MODIFY COLUMN TenPet VARCHAR(100);

ALTER TABLE PET
    MODIFY COLUMN Giong VARCHAR(100);

ALTER TABLE PET
    MODIFY COLUMN CanNang FLOAT NOT NULL default 0;

ALTER TABLE PET
    MODIFY COLUMN Tuoi INT NOT NULL default 0;

ALTER TABLE PET
    ADD CONSTRAINT FK_Pet_Chuong
        FOREIGN KEY (MaChuong) REFERENCES CHUONG(MaChuong);


-- 2. Bổ sung các cột mới nếu chưa có
ALTER TABLE PET
    ADD COLUMN NgayGui DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
ADD COLUMN NgayTra DATETIME;

-- 3. Thiết lập các ràng buộc sạch sẽ
-- Nếu chạy dòng này báo lỗi 'Duplicate', nghĩa là bạn đã đặt tên này rồi, cứ yên tâm bỏ qua.
ALTER TABLE PET
    ADD CONSTRAINT CHK_Gia_HopLe CHECK (Gia >= 0),
ADD CONSTRAINT CHK_CanNang_HopLe CHECK (CanNang > 0 AND CanNang <= 150);

-- Cap nhat cac thuoc tinh cua CHUONG

ALTER TABLE LOAICHUONG
    MODIFY COLUMN TenLoai varchar(100) ;

ALTER TABLE LOAICHUONG
    MODIFY COLUMN GiaThue DECIMAL(18, 2) NOT NULL DEFAULT 0,
    MODIFY COLUMN SoLuong INT NOT NULL DEFAULT 0;

ALTER TABLE CHUONG DROP CONSTRAINT FK_Chuong_LoaiChuong;
ALTER TABLE CHUONG
    modify column MaLoaiChuong varchar(20) not null,
    modify column TrangThai enum('Sửa chữa', 'Trống', 'Kín');
ALTER TABLE CHUONG
    Add CONSTRAINT FK_Chuong_LoaiChuong
        FOREIGN KEY (MaLoaiChuong) REFERENCES LOAICHUONG(MaLoaiChuong);
ALTER TABLE CHUONG DROP column TrangThai;
ALTER TABLE CHUONG
    add column TrangThai enum('Sua_Chua', 'Trong', 'Kin');




