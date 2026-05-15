package com.cuahangthucung.dto.user;

import com.cuahangthucung.entity.user.enums.ChucVu;
import lombok.Data;

@Data
public class NhanVienSearchRequest {
    private String tenNV;
    private String sdt;
    private String keyword;
    private ChucVu chucVu;
    private String sortBy = "maNV";
    private String sortDir = "asc";
}