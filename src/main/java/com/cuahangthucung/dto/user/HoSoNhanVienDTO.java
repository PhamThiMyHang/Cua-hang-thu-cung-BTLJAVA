package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class HoSoNhanVienDTO {
    private Integer maHoSo;
    private Integer maNV;
    private String tenNV;        // flatten tiện hiển thị

    private String bangCap;
    private String kinhNghiem;
    private String trinhDo;
}