package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.DonHang;
import com.cuahangthucung.entity.use.enums.TrangThai;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng DONHANG
 */
@Repository
public interface DonHangRepository extends JpaRepository<DonHang, String>, JpaSpecificationExecutor<DonHang> {

    // Tìm đơn hàng theo khách hàng
    List<DonHang> findByMaKH(String maKH);

    // Tìm đơn hàng theo nhân viên
    List<DonHang> findByMaNV(String maNV);

    // Tìm đơn hàng theo trạng thái
    List<DonHang> findByTrangThai(TrangThai trangThai);

    // Tìm đơn hàng theo ngày tạo cụ thể
    List<DonHang> findByNgayTao(LocalDate ngayTao);

    // Tìm đơn hàng trong khoảng thời gian
    List<DonHang> findByNgayTaoBetween(LocalDate tuNgay, LocalDate denNgay);

    // Tìm đơn hàng của khách theo trạng thái
    List<DonHang> findByMaKHAndTrangThai(String maKH, TrangThai trangThai);

    // Tìm đơn hàng của nhân viên theo trạng thái
    List<DonHang> findByMaNVAndTrangThai(String maNV, TrangThai trangThai);

    // Đếm đơn hàng theo trạng thái
    long countByTrangThai(TrangThai trangThai);

    // Đếm đơn hàng theo khách hàng
    long countByMaKH(String maKH);

    // Tính tổng doanh thu trong khoảng thời gian
    @Query("SELECT SUM(dh.tongTien) FROM DonHang dh WHERE dh.ngayTao BETWEEN :tuNgay AND :denNgay")
    Double tinhDoanhThuTheoKhoangThoiGian(@Param("tuNgay") LocalDate tuNgay, @Param("denNgay") LocalDate denNgay);

    // Sinh ID tự động
    @Query("SELECT dh FROM DonHang dh WHERE dh.maDH LIKE :prefix% ORDER BY dh.maDH DESC LIMIT 1")
    Optional<DonHang> findLastDonHangByPrefix(@Param("prefix") String prefix);
}