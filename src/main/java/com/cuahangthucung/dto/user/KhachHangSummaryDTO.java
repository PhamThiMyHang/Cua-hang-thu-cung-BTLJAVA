package com.cuahangthucung.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class KhachHangSummaryDTO {
    private Long tongSoKhachHang;
    private Long soKhachVIP;
    private Long soKhachThuong;
    private Integer tongDiemTichLuy;
}