package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.LichTruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository cho bảng LICHTRUC
 */
@Repository
public interface LichTrucRepository extends JpaRepository<LichTruc, Integer>, JpaSpecificationExecutor<LichTruc> {

    List<LichTruc> findByNhanVienMaNV(Integer maNV);

    List<LichTruc> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay);

    List<LichTruc> findByNgayBetween(LocalDate fromDate, LocalDate toDate);
}