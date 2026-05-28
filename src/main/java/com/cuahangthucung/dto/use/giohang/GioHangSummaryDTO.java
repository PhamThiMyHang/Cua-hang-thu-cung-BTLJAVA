package com.cuahangthucung.dto.use.giohang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GioHangSummaryDTO {

    private Long tongSoGioHang;
    private Long tongSoSanPhamTrongGio;
    private BigDecimal tongGiaTriTatCaGioHang;
    private Long tongSoUserDangCoGio;
    private BigDecimal giaTriGioHangTrungBinh;
}