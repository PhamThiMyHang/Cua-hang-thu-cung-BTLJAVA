package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "CHAMCONG")
@Data
public class ChamCong {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaCC")
    private Integer maCC;

    @NotNull(message = "Nhân viên không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)
    @ToString.Exclude
    private NhanVien nhanVien;

    @NotNull(message = "Ngày không được để trống")
    @Column(name = "Ngay", nullable = false)
    private LocalDate ngay;

    @Column(name = "GioVao")
    private LocalTime gioVao;

    @Column(name = "GioRa")
    private LocalTime gioRa;
}