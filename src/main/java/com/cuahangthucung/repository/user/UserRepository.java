package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng USERS - Quản lý tài khoản đăng nhập
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.username LIKE :prefix% ORDER BY u.username DESC LIMIT 1")
    Optional<User> findLastUserByPrefix(@Param("prefix") String prefix);
}