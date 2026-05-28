package com.cuahangthucung.dto.user.Role;

import lombok.Data;

@Data
public class RoleDTO {
    private Integer roleID;
    private String roleName;   // ADMIN, STAFF, KTV, CUSTOMER
}