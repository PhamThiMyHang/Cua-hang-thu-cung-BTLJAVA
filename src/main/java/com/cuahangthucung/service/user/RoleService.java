package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.Role;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface RoleService extends BaseService<Role, Integer> {

    Optional<Role> findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
    
    // Thêm phân trang nếu cần
    Page<Role> findAll(Pageable pageable);
}