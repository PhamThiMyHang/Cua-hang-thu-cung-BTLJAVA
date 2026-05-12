package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng ROLES
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    /**
     * Tìm Role theo tên (ADMIN, STAFF, KTV, CUSTOMER)
     */
    Optional<Role> findByRoleName(String roleName);

    /**
     * Kiểm tra Role đã tồn tại theo tên
     */
    boolean existsByRoleName(String roleName);
}