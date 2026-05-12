package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class PhieuNhapKho {

    @EmbeddedId
    private PhieuNhapKhoId id;

    private LocalDate ngayNhap;

    // SoLuong > 0 (CHECK trong SQL)
    private int soLuong;

    // =====================================
    // Khóa chính kép (MaPhieu, MaSP)
    // =====================================
    @Embeddable
    @Data
    public static class PhieuNhapKhoId implements Serializable {
        private String maPhieu;

        @Column(name = "maSP")
        private String maSP;
    }

    // Quan hệ tới SanPham (dựa vào maSP trong Id)
    @ManyToOne
    @MapsId("maSP")
    @JoinColumn(name = "maSP")
    private SanPham sanPham;
}