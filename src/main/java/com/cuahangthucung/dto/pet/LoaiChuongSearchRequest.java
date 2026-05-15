package com.cuahangthucung.dto.pet;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class LoaiChuongSearchRequest {
    private String tenLoai;
    private BigDecimal giaThueMin;
    private BigDecimal giaThueMax;
}