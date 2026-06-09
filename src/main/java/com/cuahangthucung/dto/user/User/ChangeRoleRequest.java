package com.cuahangthucung.dto.user.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeRoleRequest {

    @NotBlank
    private String roleName;
}