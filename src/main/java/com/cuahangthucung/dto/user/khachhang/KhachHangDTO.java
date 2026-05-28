package com.cuahangthucung.dto.user.khachhang;

import lombok.Data;

@Data
public class KhachHangDTO {
    private Integer maKH;
    private String tenKH;
    private String sdt;
    private String diaChi;
    private String loaiKH;
    private Integer diemTichLuy;
    private String gmail;            // Bổ sung gmail của khách hàng

    // Thông tin tài khoản liên kết
    private Integer userID;
    private String username;
    private String gmailUser;        // Bổ sung gmail của tài khoản liên kết
}