package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.UserRole;
import com.cuahangthucung.entity.user.entity.UserRoleId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng USER_ROLES - Bảng trung gian User và Role
 */
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, UserRoleId>, JpaSpecificationExecutor<UserRole> {

    List<UserRole> findByUserUserID(Integer userId);

    List<UserRole> findByRoleRoleID(Integer roleId);   // Role là Enum nên có thể cần điều chỉnh sau

    void deleteByUserUserID(Integer userId);

    boolean existsByUserUserIDAndRoleRoleID(Integer userId, Integer roleId);
}