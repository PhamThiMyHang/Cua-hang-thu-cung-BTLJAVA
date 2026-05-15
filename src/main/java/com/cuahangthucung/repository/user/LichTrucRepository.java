package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.LichTruc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LichTrucRepository extends JpaRepository<LichTruc, Integer>, JpaSpecificationExecutor<LichTruc> {

    // Kiểm tra lịch trực đã tồn tại (tránh trùng ca)
    boolean existsByNhanVien_MaNVAndNgayAndCaLamViec(Integer maNV, LocalDate ngay, String caLamViec);

    // Tìm lịch trực theo nhân viên và khoảng thời gian
    List<LichTruc> findByNhanVien_MaNVAndNgayBetween(Integer maNV, LocalDate tuNgay, LocalDate denNgay);

    @Query("SELECT COUNT(lt) FROM LichTruc lt WHERE lt.ngay = :ngay")
    Long countByNgay(@Param("ngay") LocalDate ngay);
}