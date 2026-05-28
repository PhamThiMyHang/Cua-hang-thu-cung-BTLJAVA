package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng SANPHAM
 */
@Repository
public interface SanPhamRepository extends JpaRepository<SanPham, String>, JpaSpecificationExecutor<SanPham> {

    // Tìm theo tên sản phẩm chính xác
    Optional<SanPham> findByTenSP(String tenSP);

    // Kiểm tra tên sản phẩm đã tồn tại chưa
    boolean existsByTenSP(String tenSP);

    // Tìm theo tên gần đúng, không phân biệt hoa thường
    List<SanPham> findByTenSPContainingIgnoreCase(String tenSP);

    // Tìm theo nhà cung cấp
    List<SanPham> findByNhaCungCap_MaNCC(String maNCC);

    // Tìm theo vị trí kho gần đúng
    List<SanPham> findByViTriContainingIgnoreCase(String viTri);

    // Tìm sản phẩm còn hàng (soLuong > 0)
    List<SanPham> findBySoLuongGreaterThan(int soLuong);

    // Tìm sản phẩm hết hàng (soLuong = 0)
    List<SanPham> findBySoLuong(int soLuong);

    // Tìm sản phẩm có giá trong khoảng [min, max]
    @Query("SELECT sp FROM SanPham sp WHERE sp.gia >= :min AND sp.gia <= :max")
    List<SanPham> findByGiaRange(@Param("min") double min, @Param("max") double max);

    // Tìm sản phẩm sắp hết hàng (soLuong <= nguong)
    @Query("SELECT sp FROM SanPham sp WHERE sp.soLuong <= :nguong ORDER BY sp.soLuong ASC")
    List<SanPham> findSapHetHang(@Param("nguong") int nguong);

    // Đếm số sản phẩm theo nhà cung cấp
    long countByNhaCungCap_MaNCC(String maNCC);

    // Lấy 10 sản phẩm đắt nhất
    List<SanPham> findTop10ByOrderByGiaDesc();

    // Sinh ID tự động
    @Query("SELECT sp FROM SanPham sp WHERE sp.maSP LIKE :prefix% ORDER BY sp.maSP DESC LIMIT 1")
    Optional<SanPham> findLastSanPhamByPrefix(@Param("prefix") String prefix);

    /* --- BỔ SUNG CÁC HÀM THỐNG KÊ KHO --- */

    // 1. Tổng số lượng dòng sản phẩm trong hệ thống
    @Query("SELECT COUNT(sp) FROM SanPham sp")
    Long countTotalSanPham();

    // 2. Đếm số sản phẩm đã hoàn toàn hết hàng (soLuong = 0)
    @Query("SELECT COUNT(sp) FROM SanPham sp WHERE sp.soLuong = 0")
    Long countHetHang();

    // 3. Đếm số sản phẩm sắp hết hàng theo một ngưỡng linh hoạt (soLuong <= nguong và soLuong > 0)
    @Query("SELECT COUNT(sp) FROM SanPham sp WHERE sp.soLuong <= :nguong AND sp.soLuong > 0")
    Long countSapHetHang(@Param("nguong") int nguong);

    // 4. Tính tổng giá trị của toàn bộ kho hàng: SUM(gia * soLuong)
    // Sử dụng COALESCE để phòng trường hợp kho trống không có sản phẩm nào thì trả về 0 thay vì null
    @Query("SELECT COALESCE(SUM(sp.gia * sp.soLuong), 0) FROM SanPham sp")
    BigDecimal tinhTongGiaTriKho();
}