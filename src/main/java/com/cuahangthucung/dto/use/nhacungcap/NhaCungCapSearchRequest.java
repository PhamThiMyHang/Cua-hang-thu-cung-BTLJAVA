package com.cuahangthucung.dto.use.nhacungcap;

import lombok.Data;

@Data
public class NhaCungCapSearchRequest {
    private String keyword;        // tìm theo tên hoặc sdt
    private String maNCC;
    private String tenNCC;
    private String sdt;
}