package com.cuahangthucung.dto.use.khuyenmai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KhuyenMaiDTO {

    private String maKM;
    private String tenKM;
    private BigDecimal giamGia;
    private LocalDate ngayBD;
    private LocalDate ngayKT;
    private Boolean conHieuLuc;
}