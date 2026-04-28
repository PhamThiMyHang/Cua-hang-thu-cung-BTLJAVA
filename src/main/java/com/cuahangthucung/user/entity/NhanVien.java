package com.cuahangthucung.user.entity;

import com.cuahangthucung.user.enums.ChucVu;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "NHANVIEN")
@Data
public class NhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNV; // mã nhân viên

    @NotBlank
    private String tenNV;

    private String sdt;

    private String diaChi;

    @Enumerated(EnumType.STRING)
    private ChucVu chucVu; // STAFF / KTV

    private Integer userID; // liên kết USERS
}