package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoaiChuongDTO {
    private String maLoaiChuong;
    private String tenLoai;
    private BigDecimal giaThue;
    private Integer soLuong;

    // (Tùy chọn) Thêm thông tin bổ trợ để dễ quản lý
    // Số lượng chuồng thực tế đang trống của loại này
    private Long soChuongConTrong;
}