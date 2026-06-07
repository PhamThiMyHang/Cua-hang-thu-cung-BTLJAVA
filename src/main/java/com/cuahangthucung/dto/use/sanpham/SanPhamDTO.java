package com.cuahangthucung.dto.use.sanpham;
/*
import lombok.Data;

@Data
public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private double gia;
    private int soLuong;
    private String hanSuDung;
    private String viTri;

    // Flatten từ NhaCungCap
    private String maNCC;
    private String tenNCC;
}
*/


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SanPhamDTO {
    private String maSP;
    private String tenSP;
    private BigDecimal gia;
    private Integer soLuong;
    private LocalDate hanSuDung;
    private String viTri;
    private String maNCC;
    private String tenNCC;
    private String urlImg;
    private boolean isLiked;
}