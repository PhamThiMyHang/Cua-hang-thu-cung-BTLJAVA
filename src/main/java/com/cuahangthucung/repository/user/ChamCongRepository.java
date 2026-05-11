package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng CHAMCONG
 */
@Repository
public interface ChamCongRepository extends JpaRepository<ChamCong, Integer>, JpaSpecificationExecutor<ChamCong> {

    Optional<ChamCong> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay);

    List<ChamCong> findByNhanVienMaNV(Integer maNV);

    List<ChamCong> findByNgayBetween(LocalDate fromDate, LocalDate toDate);
}