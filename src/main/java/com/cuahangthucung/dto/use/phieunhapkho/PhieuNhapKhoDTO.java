package com.cuahangthucung.dto.use.phieunhapkho;
/*
import lombok.Data;
import java.time.LocalDate;

@Data
public class PhieuNhapKhoDTO {
    // Flat từ PhieuNhapKhoId (EmbeddedId)
    private String maPhieu;
    private String maSP;

    private LocalDate ngayNhap;
    private int soLuong;

    // Flatten từ SanPham
    private String tenSP;
}*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuNhapKhoDTO {

    private String maPhieu;
    private String maSP;
    private String tenSP;
    private LocalDate ngayNhap;
    private Integer soLuong;
    private BigDecimal donGia;      // giá nhập
    private BigDecimal thanhTien;

    // Thông tin bổ sung
    private String maNCC;
    private String tenNCC;
}