package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "KhuyenMai")
@Data
public class KhuyenMai {

    @Id
    @Column(name = "MaKM", length = 50)
    @NotBlank(message = "Mã khuyến mãi không được để trống")
    @Size(max = 50, message = "Mã khuyến mãi không được vượt quá 50 ký tự")
    private String maKM;

    @Column(name = "TenKM", nullable = false, length = 100)
    @NotBlank(message = "Tên khuyến mãi không được để trống")
    @Size(min = 2, max = 100, message = "Tên khuyến mãi phải từ 2 đến 100 ký tự")
    private String tenKM;

    @Column(name = "GiamGia", nullable = false, precision = 5, scale = 2)
    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Mức giảm giá nhỏ nhất là 0%")
    @DecimalMax(value = "100.0", inclusive = true, message = "Mức giảm giá tối đa là 100%")
    private BigDecimal giamGia = BigDecimal.ZERO;

    @Column(name = "NgayBD", nullable = false)
    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate ngayBD;

    @Column(name = "NgayKT", nullable = false)
    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate ngayKT;

    // Tự động kiểm tra ràng buộc logic: NgayKT phải >= NgayBD trước khi lưu/cập nhật vào DB
    @PrePersist
    @PreUpdate
    protected void validateDates() {
        if (ngayBD != null && ngayKT != null && ngayKT.isBefore(ngayBD)) {
            throw new IllegalArgumentException("Ngày kết thúc không được nhỏ hơn ngày bắt đầu chương trình khuyến mãi.");
        }
    }
}