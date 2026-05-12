package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "HOSO_NHANVIEN")
@Data
public class HoSoNhanVien {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaHoSo")
    private Integer maHoSo;

    @NotNull(message = "Nhân viên không được để trống")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MaNV", nullable = false)
    @ToString.Exclude
    private NhanVien nhanVien;

    @Column(name = "BangCap", length = 255)
    private String bangCap;

    @Column(name = "KinhNghiem", columnDefinition = "TEXT")
    private String kinhNghiem;

    @Column(name = "TrinhDo", length = 100)
    private String trinhDo;
}