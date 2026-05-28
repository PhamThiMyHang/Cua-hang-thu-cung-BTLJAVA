package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "ChiTietDonHang")
@IdClass(ChiTietDonHangId.class) // Khai báo lớp bọc khóa chính kép ở đây
@Data
public class ChiTietDonHang {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDH", referencedColumnName = "MaDH")
    private DonHang donHang;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", referencedColumnName = "MaSP")
    private SanPham sanPham;

    @Column(name = "SoLuong", nullable = false)
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng mua phải lớn hơn 0") // Khớp với CHECK (SoLuong > 0)
    private Integer soLuong;

    @Column(name = "DonGia", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Đơn giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Đơn giá không được là số âm") // Khớp với CHECK (DonGia >= 0)
    private BigDecimal donGia = BigDecimal.ZERO;
}