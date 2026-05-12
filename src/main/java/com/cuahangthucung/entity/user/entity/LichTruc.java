package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LICHTRUC", 
       uniqueConstraints = @UniqueConstraint(
           columnNames = {"MaNV", "Ngay", "CaLamViec"}
       ))
@Data
public class LichTruc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LichTruc")
    private Integer id;

    @NotNull(message = "Ngày làm việc không được để trống")
    @Column(name = "Ngay", nullable = false)
    private LocalDate ngay;

    @NotBlank(message = "Ca làm việc không được để trống")
    @Column(name = "CaLamViec", nullable = false)
    private String caLamViec;

    @NotNull(message = "Giờ bắt đầu không được để trống")
    @Column(name = "GioBatDau", nullable = false)
    private LocalTime gioBatDau;

    @NotNull(message = "Giờ kết thúc không được để trống")
    @Column(name = "GioKetThuc", nullable = false)
    private LocalTime gioKetThuc;

    @NotNull(message = "Nhân viên không được để trống")   // Thêm
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)           // Thêm nullable = false
    @ToString.Exclude
    private NhanVien nhanVien;
}