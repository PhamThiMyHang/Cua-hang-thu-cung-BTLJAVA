package com.cuahangthucung.dto.user.khachhang;

import com.cuahangthucung.entity.user.enums.LoaiKH;
import lombok.Data;

@Data
public class KhachHangSearchRequest {
    private String tenKH;
    private String sdt;
    private String gmail;            // Bổ sung tìm kiếm theo Gmail khách hàng
    private String keyword;          // Tìm theo tên, sdt, địa chỉ hoặc gmail
    private LoaiKH loaiKH;
    private Integer diemTichLuyMin;
}