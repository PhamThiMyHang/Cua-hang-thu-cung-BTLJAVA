package com.cuahangthucung.dto.user;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class KPIThuongPhatDTO {
    private Integer maKPI;
    private Integer maNV;
    private String tenNV;

    private String thang;           // YYYY-MM
    private BigDecimal thuong;
    private BigDecimal phat;
    private BigDecimal tongKet;     // thuong - phat
}