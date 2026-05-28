package com.cuahangthucung.dto.use.sanpham;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPhamSummaryDTO {
    private Long tongSoSanPham;
    private Long soHetHang;       // soLuong == 0
    private Long soSapHetHang;    // soLuong <= ngưỡng (mặc định 5)
    private BigDecimal tongGiaTriKho; // SUM(gia * soLuong)
}
