package com.cuahangthucung.user.entity;

import com.cuahangthucung.user.enums.Role;
import com.cuahangthucung.user.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Entity
@Table(name = "USERS")
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userID; // khóa chính

    @NotBlank
    @Column(nullable = false, unique = true)
    private String username; // tên đăng nhập

    @NotBlank
    private String password; // mật khẩu

    @Enumerated(EnumType.STRING)
    private Role role; // ADMIN, STAFF...

    @Enumerated(EnumType.STRING)
    private UserStatus status; // ACTIVE / INACTIVE
}