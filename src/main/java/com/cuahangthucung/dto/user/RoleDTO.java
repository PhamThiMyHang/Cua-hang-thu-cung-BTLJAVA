package com.cuahangthucung.dto.user;

import lombok.Data;

@Data
public class RoleDTO {
    private Integer roleID;
    private String roleName;   // ADMIN, STAFF, KTV, CUSTOMER
}