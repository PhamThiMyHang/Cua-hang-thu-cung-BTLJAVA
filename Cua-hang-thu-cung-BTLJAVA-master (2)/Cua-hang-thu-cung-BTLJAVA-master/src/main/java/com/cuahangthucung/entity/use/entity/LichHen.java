package com.cuahangthucung.entity.use.entity;

import com.cuahangthucung.entity.use.enums.TrangThai;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class LichHen {

    @Id
    private String maLich;

    private String maKH;
    private String maPet;
    private String maNV;

    @ManyToOne
    @JoinColumn(name = "maDV")
    private DichVu dichVu;

    private String thoiGian;

    @Enumerated(EnumType.STRING)
    private TrangThai trangThai;
}

