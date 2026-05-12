package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Entity
@Table(name = "KPI_THUONGPHAT")
@Data
public class KPIThuongPhat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaKPI")
    private Integer maKPI;

    @NotNull(message = "Nhân viên không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)
    @ToString.Exclude
    private NhanVien nhanVien;

    @NotBlank(message = "Tháng không được để trống")
    @Column(name = "Thang", nullable = false, length = 7)
    private String thang;   // định dạng YYYY-MM

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "Thuong", nullable = false, precision = 10, scale = 2)
    private BigDecimal thuong = BigDecimal.ZERO;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "Phat", nullable = false, precision = 10, scale = 2)
    private BigDecimal phat = BigDecimal.ZERO;
}