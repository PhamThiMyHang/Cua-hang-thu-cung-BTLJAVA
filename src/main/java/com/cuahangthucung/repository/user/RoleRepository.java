package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng ROLES và Enum Role
 * Vì Role là Enum nên một số method Derived Query có thể hạn chế
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {

    // Tìm theo tên role (ADMIN, STAFF, KTV, CUSTOMER)
    Optional<Role> findByRoleName(String roleName);

    // Kiểm tra role đã tồn tại theo tên
    boolean existsByRoleName(String roleName);
}