package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.KhachHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng KHACHHANG
 */
@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>, JpaSpecificationExecutor<KhachHang> {

    Optional<KhachHang> findByUserUserID(Integer userId);

    Optional<KhachHang> findBySdt(String sdt);

    boolean existsBySdt(String sdt);

    @Query("SELECT k FROM KhachHang k ORDER BY k.maKH DESC LIMIT 1")
    Optional<KhachHang> findLastKhachHang();

    /*  Pham Thi My Hang sua0*/
    // KhachHangRepository.java
    @Query("SELECT k FROM KhachHang k WHERE k.maKH_String LIKE :prefix% ORDER BY k.maKH_String DESC LIMIT 1")
    Optional<KhachHang> findLastKhachHangByPrefix(@Param("prefix") String prefix);
}