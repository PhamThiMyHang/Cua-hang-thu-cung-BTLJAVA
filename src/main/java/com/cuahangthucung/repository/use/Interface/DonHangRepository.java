package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.enums.TrangThai;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng DONHANG
 */
@Repository
public interface DonHangRepository extends JpaRepository<DonHang, String>, JpaSpecificationExecutor<DonHang> {

    // 1. ĐÃ SỬA: Đi xuyên qua đối tượng liên kết bằng dấu gạch dưới (_) và đổi tham số sang Integer
    List<DonHang> findByKhachHang_MaKH(Integer maKH);

    // 2. ĐÃ SỬA: Đi xuyên qua đối tượng liên kết và đổi tham số sang Integer
    List<DonHang> findByNhanVien_MaNV(Integer maNV);

    // Tìm đơn hàng theo trạng thái
    List<DonHang> findByTrangThai(TrangThai trangThai);

    // Tìm đơn hàng theo ngày tạo cụ thể
    List<DonHang> findByNgayTao(LocalDate ngayTao);

    // Tìm đơn hàng trong khoảng thời gian
    List<DonHang> findByNgayTaoBetween(LocalDate tuNgay, LocalDate denNgay);

    // 3. ĐÃ SỬA: Tìm đơn hàng của khách theo trạng thái (Đổi sang Integer)
    List<DonHang> findByKhachHang_MaKHAndTrangThai(Integer maKH, TrangThai trangThai);

    // 4. ĐÃ SỬA: Tìm đơn hàng của nhân viên theo trạng thái (Đổi sang Integer)
    List<DonHang> findByNhanVien_MaNVAndTrangThai(Integer maNV, TrangThai trangThai);

    // Đếm đơn hàng theo trạng thái
    long countByTrangThai(TrangThai trangThai);

    // 5. ĐÃ SỬA: Đếm đơn hàng theo khách hàng (Đổi sang Integer)
    long countByKhachHang_MaKH(Integer maKH);

    // Tính tổng doanh thu trong khoảng thời gian
    @Query("SELECT SUM(dh.tongTien) FROM DonHang dh WHERE dh.ngayTao BETWEEN :tuNgay AND :denNgay")
    Double tinhDoanhThuTheoKhoangThoiGian(@Param("tuNgay") LocalDate tuNgay, @Param("denNgay") LocalDate denNgay);

    // Sinh ID tự động
    @Query("SELECT dh FROM DonHang dh WHERE dh.maDH LIKE :prefix% ORDER BY dh.maDH DESC LIMIT 1")
    Optional<DonHang> findLastDonHangByPrefix(@Param("prefix") String prefix);

    // 6. BỔ SUNG TỐI ƯU: Truy vấn phân trang kèm FETCH JOIN để lấy luôn Tên KH và Tên NV chỉ trong 1 câu lệnh SQL duy nhất
    @Override
    @Query(value = "SELECT DISTINCT dh FROM DonHang dh " +
            "LEFT JOIN FETCH dh.khachHang " +
            "LEFT JOIN FETCH dh.nhanVien",
            countQuery = "SELECT COUNT(dh) FROM DonHang dh")
    Page<DonHang> findAll(Specification<DonHang> spec, Pageable pageable);
    @Query("SELECT COALESCE(SUM(dh.tongTien),0) FROM DonHang dh WHERE dh.trangThai = 'DONE'")
    BigDecimal tongDoanhThu();

    @Query("""
       SELECT COALESCE(SUM(dh.tongTien),0)
       FROM DonHang dh
       WHERE dh.trangThai = :trangThai
       """)
    BigDecimal tongDoanhThuTheoTrangThai(@Param("trangThai") TrangThai trangThai);
}