package com.cuahangthucung.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "PET")
@Data
public class Pet {
    @Id
    @Column(name = "MaPet")
    private String maPet;

    @Column(name = "TenPet")
    private String tenPet;

    @Column(name = "Giong")
    private String giong;

    @Column(name = "Tuoi")
    private Integer tuoi;

    @Column(name = "Gia")
    private BigDecimal gia;

    @Column(name = "CanNang")
    private Float canNang;

    @Column(name = "TinhTrang")
    private String tinhTrang;

    @Column(name = "MaChuong")
    private String maChuong;

    @Column(name = "MaKH")
    private String maKH;

    @Column(name = "MaNV")
    private String maNV;
}