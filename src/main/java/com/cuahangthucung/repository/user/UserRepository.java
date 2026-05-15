package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.User;
import com.cuahangthucung.entity.user.enums.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    // Thống kê cho UserSummaryDTO
    @Query("SELECT COUNT(u) FROM User u")
    Long countTotalUsers();

    @Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
    Long countByStatus(@Param("status") UserStatus status);

    @Query("SELECT COUNT(DISTINCT n) FROM NhanVien n WHERE n.user IS NOT NULL")
    Long countUsersHaveNhanVien();

    @Query("SELECT COUNT(DISTINCT k) FROM KhachHang k WHERE k.user IS NOT NULL")
    Long countUsersHaveKhachHang();
}