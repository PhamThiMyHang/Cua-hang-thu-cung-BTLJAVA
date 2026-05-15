package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng NHANVIEN
 */
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>, JpaSpecificationExecutor<NhanVien> {

    Optional<NhanVien> findByUserUserID(Integer userId);

    Optional<NhanVien> findBySdt(String sdt);

    boolean existsBySdt(String sdt);
    // Lấy nhân viên có ID lớn nhất
    @Query("SELECT n FROM NhanVien n ORDER BY n.maNV DESC LIMIT 1")
    Optional<NhanVien> findLastNhanVien();
/*
    @Query("SELECT n FROM NhanVien n ORDER BY n.maNV DESC LIMIT 1")
    Optional<NhanVien> findLastNhanVien();
 */

}