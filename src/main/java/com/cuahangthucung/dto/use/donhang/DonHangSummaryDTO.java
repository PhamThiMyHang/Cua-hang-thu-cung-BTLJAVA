package com.cuahangthucung.dto.use.donhang;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangSummaryDTO {
    private Long tongSoDonHang;
    private Long soPending;
    private Long soDone;
    private Long soCancel;

    private Long soConfirmed;

    private Long soInProgress;
    private BigDecimal doanhThuThang; // Doanh thu tháng hiện tại
    private BigDecimal tongDoanhThu;
}
