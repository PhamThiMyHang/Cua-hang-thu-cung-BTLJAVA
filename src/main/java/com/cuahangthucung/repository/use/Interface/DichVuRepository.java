package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.dto.use.dichvu.DichVuSummaryDTO;
import com.cuahangthucung.entity.use.entity.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DichVuRepository extends JpaRepository<DichVu, String>, JpaSpecificationExecutor<DichVu> {

    Optional<DichVu> findByTenDV(String tenDV);

    boolean existsByTenDV(String tenDV);

    List<DichVu> findByTenDVContainingIgnoreCase(String tenDV);

    @Query("SELECT dv FROM DichVu dv WHERE dv.gia >= :min AND dv.gia <= :max")
    List<DichVu> findByGiaRange(@Param("min") double min, @Param("max") double max);

    @Query("SELECT dv FROM DichVu dv WHERE dv.maDV LIKE :prefix% ORDER BY dv.maDV DESC LIMIT 1")
    Optional<DichVu> findLastDichVuByPrefix(@Param("prefix") String prefix);

    // ĐÃ SỬA: Bổ sung ".dichvu" vào đường dẫn package của DTO cho đúng cấu trúc thư mục
    @Query("SELECT new com.cuahangthucung.dto.use.dichvu.DichVuSummaryDTO(" +
            "COUNT(dv), " +
            "CAST(COALESCE(MIN(dv.gia), 0) AS big_decimal), " +
            "CAST(COALESCE(MAX(dv.gia), 0) AS big_decimal), " +
            "CAST(AVG(dv.gia) AS big_decimal)) " +
            "FROM DichVu dv")
    DichVuSummaryDTO getSummaryData();
}