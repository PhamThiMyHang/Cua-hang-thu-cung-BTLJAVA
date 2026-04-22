package com.cuahangthucung.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "LOAICHUONG")
@Data
public class LoaiChuong {
    @Id
    @Column(name = "MaLoaiChuong")
    private String maLoaiChuong;

    @Column(name = "TenLoai")
    private String tenLoai;

    @Column(name = "GiaThue")
    private BigDecimal giaThue;

    @Column(name = "SoLuong")
    private Integer soLuong;
}