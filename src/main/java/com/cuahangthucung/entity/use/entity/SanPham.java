package com.cuahangthucung.entity.use.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "SanPham")
@Data
public class SanPham {

    @Id
    @Column(name = "MaSP", length = 50)
    @NotBlank(message = "Mã sản phẩm không được để trống")
    @Size(max = 50, message = "Mã sản phẩm không được vượt quá 50 ký tự")
    private String maSP;

    @Column(name = "TenSP", nullable = false, length = 100)
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(min = 2, max = 100, message = "Tên sản phẩm phải từ 2 đến 100 ký tự")
    private String tenSP;

    @Column(name = "Gia", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Giá sản phẩm không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Giá sản phẩm phải lớn hơn hoặc bằng 0")
    private BigDecimal gia = BigDecimal.ZERO;

    @Column(name = "SoLuong", nullable = false)
    @NotNull(message = "Số lượng tồn kho không được để trống")
    @Min(value = 0, message = "Số lượng tồn kho không được là số âm") // Khớp với CHECK (SoLuong >= 0)
    private Integer soLuong = 0;

    @Column(name = "HanSuDung")
    private LocalDate hanSuDung;

    @Column(name = "ViTri", length = 100)
    @Size(max = 100, message = "Vị trí trong kho không được vượt quá 100 ký tự")
    private String viTri;

    // QUAN HỆ: Nhiều sản phẩm thuộc về một Nhà cung cấp
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNCC", nullable = false)
    @NotNull(message = "Nhà cung cấp không được để trống")
    @ToString.Exclude
    private NhaCungCap nhaCungCap;

    // QUAN HỆ ĐẢO NGƯỢC: Để thuận tiện cho việc truy vấn ngược từ Sản phẩm
    // CascadeType.ALL kết hợp với các ràng buộc ON DELETE CASCADE trong SQL của bạn

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<ChiTietDonHang> danhSachChiTietDonHang;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<PhieuNhapKho> danhSachPhieuNhập;

    @OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<PhieuXuatKho> danhSachPhieuXuat;
}