package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.NhaCungCap;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng NHACUNGCAP
 */
@Repository
public interface NhaCungCapRepository extends JpaRepository<NhaCungCap, String>, JpaSpecificationExecutor<NhaCungCap> {

    // Tìm theo tên nhà cung cấp chính xác
    Optional<NhaCungCap> findByTenNCC(String tenNCC);

    // Tìm theo số điện thoại
    Optional<NhaCungCap> findBySdt(String sdt);

    // Kiểm tra sdt đã tồn tại chưa
    boolean existsBySdt(String sdt);

    // Kiểm tra tên đã tồn tại chưa
    boolean existsByTenNCC(String tenNCC);

    // Tìm theo tên gần đúng, không phân biệt hoa thường
    List<NhaCungCap> findByTenNCCContainingIgnoreCase(String tenNCC);

    // Tìm theo địa chỉ gần đúng
    List<NhaCungCap> findByDiaChiContainingIgnoreCase(String diaChi);

    // Sinh ID tự động
    @Query("SELECT n FROM NhaCungCap n WHERE n.maNCC LIKE :prefix% ORDER BY n.maNCC DESC LIMIT 1")
    Optional<NhaCungCap> findLastNhaCungCapByPrefix(@Param("prefix") String prefix);
}