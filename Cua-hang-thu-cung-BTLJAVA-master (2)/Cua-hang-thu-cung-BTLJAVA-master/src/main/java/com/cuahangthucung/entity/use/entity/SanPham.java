package com.cuahangthucung.entity.use.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SanPham {

    @Id
    private String maSP;

    private String tenSP;

    private double gia;

    private int soLuong;

    private String hanSuDung;

    private String viTri;

    @ManyToOne
    @JoinColumn(name = "maNCC")
    private NhaCungCap nhaCungCap;
}

