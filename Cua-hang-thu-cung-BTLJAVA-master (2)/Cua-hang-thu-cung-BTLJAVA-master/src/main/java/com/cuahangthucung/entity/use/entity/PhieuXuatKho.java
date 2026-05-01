package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PhieuXuatKho {

    @Id
    private String maPhieu;

    private String ngayXuat;
    private int soLuong;

    private String maNV;
    private String nhaCC;

    @ManyToOne
    @JoinColumn(name = "maSP")
    private SanPham sanPham;
}
