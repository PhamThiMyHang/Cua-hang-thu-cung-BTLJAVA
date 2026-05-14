package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.DichVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng DICHVU
 */
@Repository
public interface DichVuRepository extends JpaRepository<DichVu, String>, JpaSpecificationExecutor<DichVu> {

    // Tìm theo tên dịch vụ chính xác
    Optional<DichVu> findByTenDV(String tenDV);

    // Kiểm tra tên dịch vụ đã tồn tại chưa
    boolean existsByTenDV(String tenDV);

    // Tìm theo tên gần đúng, không phân biệt hoa thường
    List<DichVu> findByTenDVContainingIgnoreCase(String tenDV);

    // Tìm dịch vụ có giá trong khoảng [min, max]
    @Query("SELECT dv FROM DichVu dv WHERE dv.gia >= :min AND dv.gia <= :max")
    List<DichVu> findByGiaRange(@Param("min") double min, @Param("max") double max);

    // Sinh ID tự động: lấy dịch vụ có mã lớn nhất theo prefix
    @Query("SELECT dv FROM DichVu dv WHERE dv.maDV LIKE :prefix% ORDER BY dv.maDV DESC LIMIT 1")
    Optional<DichVu> findLastDichVuByPrefix(@Param("prefix") String prefix);
}