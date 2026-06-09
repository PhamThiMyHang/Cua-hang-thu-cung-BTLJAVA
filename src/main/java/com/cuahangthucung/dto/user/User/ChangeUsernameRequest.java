package com.cuahangthucung.dto.user.User;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ChangeUsernameRequest {

    @NotBlank
    private String username;
}