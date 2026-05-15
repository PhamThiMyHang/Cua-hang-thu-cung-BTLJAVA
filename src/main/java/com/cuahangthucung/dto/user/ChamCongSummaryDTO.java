package com.cuahangthucung.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChamCongSummaryDTO {
    private Long tongSoNgayChamCong;
    private Long soNgayDiLam;
    private Double tyLeDiLam;           // ví dụ: 95.5%
    private Integer tongSoNhanVienChamCong;
}