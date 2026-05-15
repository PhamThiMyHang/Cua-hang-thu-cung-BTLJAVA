package com.cuahangthucung.dto.pet;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChuongSummaryDTO {
    // 1. Con số tổng quát
    private Long tongSoChuong;
    private Long soChuongTrong;
    private Long soChuongDangCoPet; // Trạng thái 'Kín'
    private Long soChuongDangSuaChua;

    // 2. Tỷ lệ lấp đầy (Ví dụ: 75%)
    private Double tyLeLapDay;

    // 3. Thống kê theo loại (Ví dụ: VIP: 5 chuồng, Thường: 10 chuồng)
    // Dùng Map để linh hoạt, không cần fix cứng tên loại chuồng
    private Map<String, Long> thongKeTheoLoai;
}