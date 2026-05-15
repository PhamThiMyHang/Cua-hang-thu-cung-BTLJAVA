package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class KPIThuongPhatSearchRequest {
    private Integer maNV;
    private String thang;
    private String keyword;
}