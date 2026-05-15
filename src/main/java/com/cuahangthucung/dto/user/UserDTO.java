package com.cuahangthucung.dto.user;

import lombok.Data;
import java.util.Set;

@Data
public class UserDTO {
    private Integer userID;
    private String username;
    private String status;                    // ACTIVE / INACTIVE
    private Set<String> roles;                // Danh sách tên role

    // Thông tin liên kết
    private Integer maNV;
    private String tenNV;
    private Integer maKH;
    private String tenKH;
}