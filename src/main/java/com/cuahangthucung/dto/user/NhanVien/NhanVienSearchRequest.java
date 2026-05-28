package com.cuahangthucung.dto.user.NhanVien;

import com.cuahangthucung.entity.user.enums.ChucVu;
import lombok.Data;

@Data
public class NhanVienSearchRequest {
    private String tenNV;
    private String sdt;
    private String gmail;            // Bổ sung tìm kiếm theo Gmail nhân viên
    private String keyword;          // Tìm theo tên, sdt, địa chỉ hoặc gmail
    private ChucVu chucVu;
    private String sortBy = "maNV";
    private String sortDir = "asc";
}