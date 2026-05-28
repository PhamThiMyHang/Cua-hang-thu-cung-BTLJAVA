package com.cuahangthucung.dto.use.khuyenmai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhuyenMaiSummaryDTO {
    private Long tongSoKhuyenMai;
    private Long soConHieuLuc;   // Khuyến mãi đang còn hiệu lực
    private Long soHetHieuLuc;   // Khuyến mãi đã hết hạn
}
