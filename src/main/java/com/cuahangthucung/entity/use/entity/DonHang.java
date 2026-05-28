package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.use.enums.TrangThai;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.NhanVien;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "DonHang")
@Data
public class DonHang {

    @Id
    @Column(name = "MaDH", length = 50)
    @NotBlank(message = "Mã đơn hàng không được để trống")
    @Size(max = 50, message = "Mã đơn hàng không được vượt quá 50 ký tự")
    private String maDH;

    // QUAN HỆ: Nhiều đơn hàng thuộc về một Khách hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH", nullable = true) // nullable = true vì SQL không để NOT NULL
    @ToString.Exclude
    private KhachHang khachHang;

    // QUAN HỆ: Nhiều đơn hàng được lập bởi một Nhân viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = true) // nullable = true vì SQL không để NOT NULL
    @ToString.Exclude
    private NhanVien nhanVien;

    @Column(name = "NgayTao", nullable = false)
    @NotNull(message = "Ngày tạo đơn hàng không được để trống")
    private LocalDate ngayTao;

    @Column(name = "TongTien", nullable = false, precision = 10, scale = 2)
    @NotNull(message = "Tổng tiền không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Tổng tiền phải lớn hơn hoặc bằng 0")
    private BigDecimal tongTien = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrangThai", columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'DONE', 'CANCEL') DEFAULT 'PENDING'")
    private TrangThai trangThai = TrangThai.PENDING;

    // Tự động gán ngày tạo nếu phía client không truyền lên khi lưu mới
    @PrePersist
    protected void onCreate() {
        if (this.ngayTao == null) {
            this.ngayTao = LocalDate.now();
        }
    }

    // QUAN HỆ: Một Đơn hàng chứa nhiều Chi tiết đơn hàng
    // CascadeType.ALL kết hợp với ON DELETE CASCADE phía DB: Xóa đơn hàng sẽ tự động xóa hết các chi tiết của nó
    @OneToMany(mappedBy = "donHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    private List<ChiTietDonHang> danhSachChiTiet;
}