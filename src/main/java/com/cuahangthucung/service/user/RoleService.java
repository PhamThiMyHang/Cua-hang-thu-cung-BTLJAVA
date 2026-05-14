package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.service.base.BaseService;

import java.util.Optional;

public interface RoleService extends BaseService<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}