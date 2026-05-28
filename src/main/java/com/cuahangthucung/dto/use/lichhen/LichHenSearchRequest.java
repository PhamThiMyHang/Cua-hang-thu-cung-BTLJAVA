package com.cuahangthucung.dto.use.lichhen;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LichHenSearchRequest {
    private String maLich;
    private String maKH;
    private String maPet;
    private String maNV;
    private String maDV;
    private LocalDateTime tuNgay;
    private LocalDateTime denNgay;
    private String trangThai;
    private String keyword;
}