package com.cuahangthucung.user.entity;

import com.cuahangthucung.user.enums.LoaiKH;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "KHACHHANG")
@Data
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKH; // mã khách hàng

    @NotBlank
    private String tenKH;

    private String sdt;

    private String diaChi;

    @Enumerated(EnumType.STRING)
    private LoaiKH loaiKH; // THUONG, VIP...

    @Column(nullable = false)
    private Integer diemTichLuy = 0; // mặc định

    private Integer userID; // liên kết USERS
}