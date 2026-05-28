package com.cuahangthucung.dto.user.hosonhanvien;

import lombok.Data;

@Data
public class HoSoNhanVienSearchRequest {
    private Integer maNV;
    private String keyword; // tìm theo tên NV, bằng cấp, trình độ
}