package com.cuahangthucung.entity.user.entity;

import com.cuahangthucung.entity.user.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "USER_ROLES")
@Data
public class UserRole {

    @EmbeddedId
    private UserRoleId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userID")
    @JoinColumn(name = "UserID")
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleID")
    @JoinColumn(name = "RoleID")
    @ToString.Exclude
    private Role role;   // Role là Enum
}