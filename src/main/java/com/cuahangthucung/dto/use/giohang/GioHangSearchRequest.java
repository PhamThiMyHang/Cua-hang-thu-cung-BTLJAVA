package com.cuahangthucung.dto.use.giohang;

import lombok.Data;

@Data
public class GioHangSearchRequest {
    private String maGioHang;
    private String maUser;
    private String maSP;
    private String keyword;
}