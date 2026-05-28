package com.cuahangthucung.dto.user.User;

import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Integer userID;
    private String username;
    private String gmail;                     // Bổ sung gmail của User
    private String password;
    private String status;                    // ACTIVE / INACTIVE
    private Set<String> roles;                // Danh sách tên role

    // Thông tin liên kết Nhân viên
    private Integer maNV;
    private String tenNV;
    private String gmailNV;                   // Bổ sung gmail của Nhân viên

    // Thông tin liên kết Khách hàng
    private Integer maKH;
    private String tenKH;
    private String gmailKH;                   // Bổ sung gmail của Khách hàng
}