package com.cuahangthucung.dto.user.User;

import lombok.Data;

@Data
public class UserSearchRequest {
    private String username;
    private String gmail;         // Bổ sung tìm kiếm đích danh theo Gmail
    private String status;
    private String roleName;
    private String keyword;       // Tìm theo username, gmail, hoặc tên NV/KH
}