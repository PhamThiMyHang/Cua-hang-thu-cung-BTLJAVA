package com.cuahangthucung.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "LICHSU_SUC_KHOE")
@Data
public class LichSuSucKhoe {
    @Id
    @Column(name = "MaLS")
    private String maLS;

    @Column(name = "MaPet")
    private String maPet;

    @Column(name = "MoTa")
    private String moTa;

    @Column(name = "Ngay")
    private LocalDateTime ngay;

    @Column(name = "Loai")
    private String loai; // 'Vaccine', 'Benh', 'Kham'
}