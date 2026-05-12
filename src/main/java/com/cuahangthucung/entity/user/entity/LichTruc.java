package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "LICHTRUC")
@Data
public class LichTruc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_LichTruc")
    private Integer id;

    @NotNull(message = "Ngày làm việc không được để trống")
    @Column(name = "Ngay")
    private LocalDate ngay;

    @NotBlank(message = "Ca làm việc không được để trống")
    @Column(name = "CaLamViec")
    private String caLamViec;

    @NotNull(message = "Giờ bắt đầu không được để trống")
    @Column(name = "GioBatDau")
    private LocalTime gioBatDau;

    @NotNull(message = "Giờ kết thúc không được để trống")
    @Column(name = "GioKetThuc")
    private LocalTime gioKetThuc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV")
    @ToString.Exclude
    private NhanVien nhanVien;
}