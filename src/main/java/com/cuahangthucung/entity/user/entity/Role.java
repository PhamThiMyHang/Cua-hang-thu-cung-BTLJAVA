package com.cuahangthucung.entity.user.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "ROLES")
@Data
public class Role {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleID")
    private Integer roleID;

    @Column(name = "RoleName", nullable = false, unique = true, length = 20)
    private String roleName;   // ADMIN, STAFF, KTV, CUSTOMER

    // Có thể thêm constructor tiện lợi
    public Role(String roleName) {
        this.roleName = roleName;
    }
    public Role() {}
    
}