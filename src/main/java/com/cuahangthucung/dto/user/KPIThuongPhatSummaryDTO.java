package com.cuahangthucung.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KPIThuongPhatSummaryDTO {
    private String thang;
    private BigDecimal tongThuong;
    private BigDecimal tongPhat;
    private BigDecimal tongKet;
    private Long soNhanVienDuocKPI;
}