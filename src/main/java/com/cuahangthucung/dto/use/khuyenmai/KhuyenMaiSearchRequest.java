package com.cuahangthucung.dto.use.khuyenmai;


import lombok.Data;

import java.time.LocalDate;

@Data
public class KhuyenMaiSearchRequest {
    private String keyword;
    private String maKM;
    private String tenKM;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private Boolean conHieuLuc;
}