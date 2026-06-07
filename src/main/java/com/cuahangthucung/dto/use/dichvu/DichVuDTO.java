package com.cuahangthucung.dto.use.dichvu;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DichVuDTO {

    private String maDV;
    private String tenDV;
    private BigDecimal gia;
    private String moTa;

    // Thông tin bổ sung cho frontend
    private Long soLanSuDung;
    private Boolean dangHoatDong;

    // Thêm vào class DichVuDTO
    private String urlImg;
}