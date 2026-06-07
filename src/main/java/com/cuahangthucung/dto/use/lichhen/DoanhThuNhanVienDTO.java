package com.cuahangthucung.dto.use.lichhen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoanhThuNhanVienDTO {

    // Mã nhân viên (KTV)
    private Integer maNV;

    // Tên nhân viên
    private String tenNV;

    // Số lịch hẹn đã hoàn thành
    private Long soLichHoanThanh;

    // Tổng doanh thu mang về
    private BigDecimal doanhThu;
}