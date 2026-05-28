package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "PhieuNhapKho")
@IdClass(PhieuNhapKhoId.class) // Khai báo lớp bọc khóa chính kép
@Data
public class PhieuNhapKho {

    @Id
    @Column(name = "MaPhieu", length = 50)
    @NotBlank(message = "Mã phiếu nhập không được để trống")
    @Size(max = 50, message = "Mã phiếu nhập không được vượt quá 50 ký tự")
    private String maPhieu;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", referencedColumnName = "MaSP")
    @NotNull(message = "Sản phẩm nhập kho không được để trống")
    private SanPham sanPham;

    @Column(name = "NgayNhap", nullable = false)
    @NotNull(message = "Ngày nhập kho không được để trống")
    private LocalDate ngayNhap;

    @Column(name = "SoLuong", nullable = false)
    @NotNull(message = "Số lượng nhập không được để trống")
    @Min(value = 1, message = "Số lượng nhập kho phải lớn hơn 0") // Khớp với CHECK (SoLuong > 0)
    private Integer soLuong;

    // Tự động gán ngày nhập hiện tại nếu hệ thống không truyền vào khi tạo phiếu
    @PrePersist
    protected void onCreate() {
        if (this.ngayNhap == null) {
            this.ngayNhap = LocalDate.now();
        }
    }
}