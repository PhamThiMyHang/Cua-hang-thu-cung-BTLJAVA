package com.cuahangthucung.entity.user.entity;

import com.cuahangthucung.entity.user.enums.LoaiKH;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "KHACHHANG")
@Data
public class KhachHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKH")
    private Integer maKH;

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Column(name = "TenKH", nullable = false, length = 100)
    private String tenKH;

    @Column(name = "SDT", length = 15, unique = true)
    private String sdt;

    @Column(name = "DiaChi", length = 255)
    private String diaChi;

    @NotNull(message = "Loại khách hàng không được để trống")
    @Enumerated(EnumType.STRING)
    @Column(name = "LoaiKH", nullable = false)
    private LoaiKH loaiKH = LoaiKH.THUONG;

    @NotNull(message = "Điểm tích lũy không được để trống")
    @Column(name = "DiemTichLuy", nullable = false)
    private Integer diemTichLuy = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UserID")
    @ToString.Exclude
    private User user;
}