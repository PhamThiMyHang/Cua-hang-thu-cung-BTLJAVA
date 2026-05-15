package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class UserSearchRequest {
    private String username;
    private String status;
    private String roleName;
    private String keyword; // tìm theo username hoặc tên NV/KH
}