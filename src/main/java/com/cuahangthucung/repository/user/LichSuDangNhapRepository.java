package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng LICH_SU_DANG_NHAP
 */
@Repository
public interface LichSuDangNhapRepository extends JpaRepository<LichSuDangNhap, Integer>, JpaSpecificationExecutor<LichSuDangNhap> {

    List<LichSuDangNhap> findByUserUserID(Integer userId);

    List<LichSuDangNhap> findTop10ByUserUserIDOrderByThoiGianDesc(Integer userId);
}