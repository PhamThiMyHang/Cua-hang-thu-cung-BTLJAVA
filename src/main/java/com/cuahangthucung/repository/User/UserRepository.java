package com.cuahangthucung.repository.User;

import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.entity.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    // =========================================================
    // LOGIN BASIC (không load roles → chỉ dùng khi không cần quyền)
    // =========================================================
    Optional<User> findByUsername(String username);


    // =========================================================
    // CHECK TRÙNG USERNAME (dùng khi đăng ký)
    // =========================================================
    boolean existsByUsername(String username);


    // =========================================================
    // LOGIN CHUẨN (ACTIVE + ROLES)
    // → Dùng cho Spring Security để tránh LazyInitializationException
    // =========================================================
    @Query("""
        SELECT u FROM User u 
        LEFT JOIN FETCH u.roles 
        WHERE u.username = :username AND u.status = :status
        """)
    Optional<User> findActiveUserWithRoles(@Param("username") String username,
                                           @Param("status") UserStatus status);


    // =========================================================
    // LOAD USER + ROLES (không check status)
    // → Dùng khi cần load quyền riêng (admin, internal logic)
    // =========================================================
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.roles WHERE u.username = :username")
    Optional<User> findByUsernameWithRoles(@Param("username") String username);


    // =========================================================
    // LẤY USER MỚI NHẤT
    // → Dùng để sinh mã (USER001, USER002...)
    // ⚠️ CHÚ Ý: phải đúng tên field entity (userId, KHÔNG phải UserID)
    // =========================================================
    Optional<User> findTopByOrderByUserIdDesc();


    // =========================================================
    // NOTE:
    // - Không cần thêm findBy... khác nếu dùng GenericSpecification
    // - Dùng Specification để filter động (username, role, status...)
    // =========================================================
}