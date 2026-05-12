package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Data
public class PhieuXuatKho {

    @EmbeddedId
    private PhieuXuatKhoId id;

    private LocalDate ngayXuat;

    // SoLuong > 0 (CHECK trong SQL)
    private int soLuong;

    private String maNV;

    // noiNhan thay cho nhaCC (đúng với cột NoiNhan trong SQL)
    private String noiNhan;

    // =====================================
    // Khóa chính kép (MaPhieu, MaSP)
    // =====================================
    @Embeddable
    @Data
    public static class PhieuXuatKhoId implements Serializable {
        private String maPhieu;

        @Column(name = "maSP")
        private String maSP;
    }

    // Quan hệ tới SanPham
    @ManyToOne
    @MapsId("maSP")
    @JoinColumn(name = "maSP")
    private SanPham sanPham;
}