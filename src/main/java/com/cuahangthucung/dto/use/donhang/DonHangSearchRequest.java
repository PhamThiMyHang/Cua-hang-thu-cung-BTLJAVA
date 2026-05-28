package com.cuahangthucung.dto.use.donhang;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DonHangSearchRequest {

    private String maDH;
    private String maKH;
    private String tenKH;
    private String maNV;
    private String trangThai;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private BigDecimal minTongTien;
    private BigDecimal maxTongTien;
    private String keyword;        // tìm theo mã đơn hoặc tên khách
}