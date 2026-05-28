package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.user.entity.NhanVien;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "PhieuXuatKho")
@IdClass(PhieuXuatKhoId.class) // Khai báo lớp bọc khóa chính kép
@Data
public class PhieuXuatKho {

    @Id
    @Column(name = "MaPhieu", length = 50)
    @NotBlank(message = "Mã phiếu xuất không được để trống")
    @Size(max = 50, message = "Mã phiếu xuất không được vượt quá 50 ký tự")
    private String maPhieu;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaSP", referencedColumnName = "MaSP")
    @NotNull(message = "Sản phẩm xuất kho không được để trống")
    private SanPham sanPham;

    @Column(name = "NgayXuat", nullable = false)
    @NotNull(message = "Ngày xuất kho không được để trống")
    private LocalDate ngayXuat;

    @Column(name = "SoLuong", nullable = false)
    @NotNull(message = "Số lượng xuất không được để trống")
    @Min(value = 1, message = "Số lượng xuất kho phải lớn hơn 0") // Khớp với CHECK (SoLuong > 0)
    private Integer soLuong;

    // QUAN HỆ: Nhiều phiếu xuất được thực hiện bởi một Nhân viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = true) // nullable = true vì SQL không để NOT NULL
    @ToString.Exclude
    private NhanVien nhanVien;

    @Column(name = "NoiNhan", length = 100)
    @Size(max = 100, message = "Nơi nhận hàng không được vượt quá 100 ký tự")
    private String noiNhan;

    // Tự động gán ngày xuất hiện tại nếu hệ thống không truyền vào khi tạo phiếu
    @PrePersist
    protected void onCreate() {
        if (this.ngayXuat == null) {
            this.ngayXuat = LocalDate.now();
        }
    }
}