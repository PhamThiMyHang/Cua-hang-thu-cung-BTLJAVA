package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.DichVu;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UseRepository extends JpaRepository<DichVu, String>,
                                           JpaSpecificationExecutor<DichVu> {

    // =====================================
    // BASIC QUERY 
    // =====================================

    Optional<DichVu> findByTenDV(String tenDV);

    boolean existsByTenDV(String tenDV);

    List<DichVu> findByTenDVContainingIgnoreCase(String tenDV);


    // =====================================
    // CUSTOM QUERY 
    // =====================================

    @Query("SELECT dv FROM DichVu dv WHERE dv.gia >= :min AND dv.gia <= :max")
    List<DichVu> findByGiaRange(Double min, Double max);


    // =====================================
    // GENERATE ID 
    // =====================================

    /**
     * Lấy dịch vụ có mã lớn nhất (phục vụ generate ID)
     * Ví dụ: DV001 → DV002
     */
    DichVu findTopByMaDVStartingWithOrderByMaDVDesc(String prefix);


    /**
     * Nếu muốn lấy tất cả theo prefix
     */
    List<DichVu> findByMaDVStartingWith(String prefix);

}