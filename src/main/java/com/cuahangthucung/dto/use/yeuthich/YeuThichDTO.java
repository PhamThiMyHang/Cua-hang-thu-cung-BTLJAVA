package com.cuahangthucung.dto.use.yeuthich;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class YeuThichDTO {

    private String maUser;
    private String maSP;
    private String tenSP;
    private BigDecimal gia;
    private String hinhAnh;
    private LocalDateTime ngayThem;   // Nếu bạn có trường thời gian
    private String hinhAnhUser;

}