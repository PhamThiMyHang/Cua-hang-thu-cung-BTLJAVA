package com.cuahangthucung.dto.user;

import com.cuahangthucung.entity.user.enums.UserStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class UserRequest {
    private Integer userID; // dùng khi update

    @NotBlank(message = "Tên đăng nhập không được để trống")
    private String username;

    @NotBlank(message = "Mật khẩu không được để trống")
    private String password;

    private UserStatus status = UserStatus.ACTIVE;

    @NotNull(message = "Vai trò không được để trống")
    private Set<String> roleNames;   // Ví dụ: ["STAFF", "KTV"]
}