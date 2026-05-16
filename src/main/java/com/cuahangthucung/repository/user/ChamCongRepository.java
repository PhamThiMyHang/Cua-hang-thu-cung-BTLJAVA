package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.ChamCong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ChamCongRepository extends JpaRepository<ChamCong, Integer>, JpaSpecificationExecutor<ChamCong> {

    @Query("SELECT COUNT(c) FROM ChamCong c WHERE c.ngay BETWEEN :start AND :end")
    Long countByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

    @Query("SELECT COUNT(c) FROM ChamCong c WHERE c.nhanVien.maNV = :maNV AND c.ngay = :ngay")
    Long countByNhanVienAndNgay(@Param("maNV") Integer maNV, @Param("ngay") LocalDate ngay);

    // Tính tổng số giờ làm (Nếu trong Entity có field soGioLam)
    @Query("SELECT SUM(c.soGioLam) FROM ChamCong c WHERE c.ngay BETWEEN :start AND :end")
    Double sumSoGioLamByDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);
}