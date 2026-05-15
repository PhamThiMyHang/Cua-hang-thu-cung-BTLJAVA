package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class NhanVienDTO {
    private Integer maNV;
    private String tenNV;
    private String sdt;
    private String diaChi;
    private String chucVu;

    private Integer userID;
    private String username;

    private HoSoNhanVienDTO hoSo;
}