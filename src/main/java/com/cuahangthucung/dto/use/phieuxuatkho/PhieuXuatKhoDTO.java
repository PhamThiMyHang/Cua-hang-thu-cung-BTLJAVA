package com.cuahangthucung.dto.use.phieuxuatkho;
/*
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuXuatKhoDTO {

    private String maPhieu;
    private String maSP;
    private String tenSP;
    private LocalDate ngayXuat;
    private Integer soLuong;
    private Double donGia;
    private Double thanhTien;

    private String maNV;
    private String tenNV;
    private String noiNhan;        // nơi nhận / khách hàng
    private String ghiChu;
}*/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuXuatKhoDTO {

    private String maPhieu;
    private String maSP;
    private String tenSP;
    private LocalDate ngayXuat;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    private String maNV;
    private String tenNV;
    private String noiNhan;        // nơi nhận / khách hàng
    private String ghiChu;
}