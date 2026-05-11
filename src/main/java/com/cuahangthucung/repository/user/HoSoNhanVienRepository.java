package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng HOSO_NHANVIEN - Hồ sơ nhân viên
 */
@Repository
public interface HoSoNhanVienRepository extends JpaRepository<HoSoNhanVien, Integer>, JpaSpecificationExecutor<HoSoNhanVien> {

    Optional<HoSoNhanVien> findByNhanVienMaNV(Integer maNV);

    boolean existsByNhanVienMaNV(Integer maNV);
}