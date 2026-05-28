package com.cuahangthucung.dto.use.dichvu;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class DichVuSearchRequest {

    private String keyword;     // tìm theo tên hoặc mã
    private String maDV;
    private String tenDV;
    private BigDecimal minGia;
    private BigDecimal maxGia;
    private Boolean dangHoatDong;
}