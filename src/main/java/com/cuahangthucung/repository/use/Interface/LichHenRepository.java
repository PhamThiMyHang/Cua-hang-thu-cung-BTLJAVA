package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.LichHen;
import com.cuahangthucung.entity.use.enums.TrangThai;
import com.cuahangthucung.dto.use.lichhen.LichHenSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng LICHHEN
 */
@Repository
public interface LichHenRepository extends JpaRepository<LichHen, String>, JpaSpecificationExecutor<LichHen> {

    // ĐÃ SỬA: Đi xuyên qua đối tượng liên kết và đồng bộ kiểu Integer
    List<LichHen> findByKhachHang_MaKH(Integer maKH);

    List<LichHen> findByPet_MaPet(String maPet);

    List<LichHen> findByNhanVien_MaNV(Integer maNV);

    List<LichHen> findByDichVu_MaDV(String maDV);

    List<LichHen> findByTrangThai(TrangThai trangThai);

    // ĐÃ SỬA: Tìm kiếm kết hợp theo Trạng thái & Mã số Integer
    List<LichHen> findByKhachHang_MaKHAndTrangThai(Integer maKH, TrangThai trangThai);

    List<LichHen> findByNhanVien_MaNVAndTrangThai(Integer maNV, TrangThai trangThai);

    List<LichHen> findByThoiGianBetween(LocalDateTime tuThoiGian, LocalDateTime denThoiGian);

    long countByTrangThai(TrangThai trangThai);

    long countByNhanVien_MaNV(Integer maNV);

    long countByKhachHang_MaKH(Integer maKH);

    // Sinh ID tự động
    @Query("SELECT lh FROM LichHen lh WHERE lh.maLich LIKE :prefix% ORDER BY lh.maLich DESC LIMIT 1")
    Optional<LichHen> findLastLichHenByPrefix(@Param("prefix") String prefix);

    // ĐÃ BỔ SUNG: Truy vấn phân trang tối ưu, nạp nhanh thông tin tên Khách, tên Pet, tên NV, tên Dịch vụ
    @Override
    @Query(value = "SELECT DISTINCT lh FROM LichHen lh " +
            "LEFT JOIN FETCH lh.khachHang " +
            "LEFT JOIN FETCH lh.pet " +
            "LEFT JOIN FETCH lh.nhanVien " +
            "LEFT JOIN FETCH lh.dichVu",
            countQuery = "SELECT COUNT(lh) FROM LichHen lh")
    Page<LichHen> findAll(Specification<LichHen> spec, Pageable pageable);

    // ĐÃ BỔ SUNG: Viết câu truy vấn gộp tính toán thống kê tổng quan (Dashboard)
    @Query("SELECT new com.cuahangthucung.dto.use.lichhen.LichHenSummaryDTO(" +
            "COUNT(lh), " +
            "SUM(CASE WHEN lh.trangThai = 'PENDING' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN lh.trangThai = 'CONFIRMED' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN lh.trangThai = 'IN_PROGRESS' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN lh.trangThai = 'DONE' THEN 1 ELSE 0 END), " +
            "SUM(CASE WHEN lh.trangThai = 'CANCEL' THEN 1 ELSE 0 END)) " +
            "FROM LichHen lh")
    LichHenSummaryDTO layThongKeTongQuanLichHen();
}