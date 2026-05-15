package com.cuahangthucung.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiChuongSummaryDTO {
    private Long tongSoLoaiChuong;
    private BigDecimal giaThueTrungBinh;
    private String loaiChuongPhoBienNhat; // Tên loại có nhiều chuồng nhất
    private Long tongSucChuaHeThong; // Tổng cột soLuong của tất cả các loại
}