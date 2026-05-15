package com.cuahangthucung.dto.user;

import com.cuahangthucung.entity.user.enums.LoaiKH;
import lombok.Data;

@Data
public class KhachHangSearchRequest {
    private String tenKH;
    private String sdt;
    private String keyword;
    private LoaiKH loaiKH;
    private Integer diemTichLuyMin;
}