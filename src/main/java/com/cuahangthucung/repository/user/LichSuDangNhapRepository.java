package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface LichSuDangNhapRepository extends JpaRepository<LichSuDangNhap, Integer>, JpaSpecificationExecutor<LichSuDangNhap> {

    @Query("SELECT COUNT(l) FROM LichSuDangNhap l WHERE l.thoiGian >= :start")
    Long countRecentLogins(LocalDateTime start);
}