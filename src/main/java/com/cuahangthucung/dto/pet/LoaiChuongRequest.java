package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoaiChuongRequest {
    private String maLoaiChuong;
    private String tenLoai;
    private BigDecimal giaThue;
    private Integer soLuong;
}