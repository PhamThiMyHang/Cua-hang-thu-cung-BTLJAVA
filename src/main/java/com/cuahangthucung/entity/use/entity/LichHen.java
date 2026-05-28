package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.pet.entity.Pet;
import com.cuahangthucung.entity.use.enums.TrangThai;
import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.entity.NhanVien;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "LichHen")
@Data
public class LichHen {

    @Id
    @Column(name = "MaLich", length = 50)
    @NotBlank(message = "Mã lịch hẹn không được để trống")
    @Size(max = 50, message = "Mã lịch hẹn không được vượt quá 50 ký tự")
    private String maLich;

    // QUAN HỆ: Nhiều lịch hẹn có thể thuộc về một Khách hàng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaKH", nullable = true) // nullable = true vì SQL không bắt buộc NOT NULL
    @ToString.Exclude
    private KhachHang khachHang;

    // QUAN HỆ: Nhiều lịch hẹn có thể đặt cho một Thú cưng
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaPet", nullable = true)
    @ToString.Exclude
    private Pet pet;

    // QUAN HỆ: Nhiều lịch hẹn có thể được phụ trách bởi một Nhân viên
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = true)
    @ToString.Exclude
    private NhanVien nhanVien;

    // QUAN HỆ: Nhiều lịch hẹn áp dụng một Dịch vụ cụ thể
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaDV", nullable = false) // Bắt buộc phải có dịch vụ theo thiết kế SQL
    @NotNull(message = "Dịch vụ của lịch hẹn không được để trống")
    @ToString.Exclude
    private com.cuahangthucung.entity.use.entity.DichVu dichVu;

    @Column(name = "ThoiGian", nullable = false)
    @NotNull(message = "Thời gian hẹn không được để trống")
    private LocalDateTime thoiGian;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrangThai", columnDefinition = "ENUM('PENDING', 'CONFIRMED', 'IN_PROGRESS', 'DONE', 'CANCEL') DEFAULT 'PENDING'")
    private TrangThai trangThai = TrangThai.PENDING;
}