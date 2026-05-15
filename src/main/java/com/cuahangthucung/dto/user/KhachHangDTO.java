package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class KhachHangDTO {
    private Integer maKH;
    private String tenKH;
    private String sdt;
    private String diaChi;
    private String loaiKH;
    private Integer diemTichLuy;

    private Integer userID;
    private String username;
}