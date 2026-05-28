package com.cuahangthucung.dto.use.chitietdonhang;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ChiTietDonHangSearchRequest {
    private String maDH;
    private String maSP;
    private String tenSP;
    private String keyword;
    private LocalDate tuNgay;
    private LocalDate denNgay;
}