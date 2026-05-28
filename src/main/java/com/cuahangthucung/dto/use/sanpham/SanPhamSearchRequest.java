package com.cuahangthucung.dto.use.sanpham;
/*
import lombok.Data;

@Data
public class SanPhamSearchRequest {
    private String tenSP;
    private String maNCC;
    private Double giaMin;
    private Double giaMax;
    private Integer soLuongMax; // Dùng để lọc sắp hết hàng

    private String sortBy = "maSP";
    private String sortDir = "asc";
}
*/


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SanPhamSearchRequest {

    private String maSP;
    private String tenSP;
    private String maNCC;
    private String viTri;
    private BigDecimal minGia;
    private BigDecimal maxGia;
    private Integer minSoLuong;
    private Integer maxSoLuong;
    private LocalDate hanSuDungTu;
    private LocalDate hanSuDungDen;
    private String keyword;
    private Boolean conHang;       // true = còn hàng (>0)
}