package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.KhuyenMai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng KHUYENMAI
 */
@Repository
public interface KhuyenMaiRepository extends JpaRepository<KhuyenMai, String>, JpaSpecificationExecutor<KhuyenMai> {

    // Tìm theo tên khuyến mãi chính xác
    Optional<KhuyenMai> findByTenKM(String tenKM);

    // Kiểm tra tên khuyến mãi đã tồn tại chưa
    boolean existsByTenKM(String tenKM);

    // Tìm theo tên gần đúng, không phân biệt hoa thường
    List<KhuyenMai> findByTenKMContainingIgnoreCase(String tenKM);

    // Tìm khuyến mãi có mức giảm trong khoảng [min, max]
    @Query("SELECT km FROM KhuyenMai km WHERE km.giamGia >= :min AND km.giamGia <= :max")
    List<KhuyenMai> findByGiamGiaRange(@Param("min") double min, @Param("max") double max);

    // Tìm khuyến mãi còn hiệu lực tính đến ngày truyền vào
    @Query("SELECT km FROM KhuyenMai km WHERE km.ngayBD <= :ngay AND km.ngayKT >= :ngay")
    List<KhuyenMai> findByConHieuLuc(@Param("ngay") LocalDate ngay);

    // Tìm khuyến mãi bắt đầu từ ngày cụ thể
    List<KhuyenMai> findByNgayBD(LocalDate ngayBD);

    // Tìm khuyến mãi kết thúc vào ngày cụ thể
    List<KhuyenMai> findByNgayKT(LocalDate ngayKT);

    // Sinh ID tự động
    @Query("SELECT km FROM KhuyenMai km WHERE km.maKM LIKE :prefix% ORDER BY km.maKM DESC LIMIT 1")
    Optional<KhuyenMai> findLastKhuyenMaiByPrefix(@Param("prefix") String prefix);
}