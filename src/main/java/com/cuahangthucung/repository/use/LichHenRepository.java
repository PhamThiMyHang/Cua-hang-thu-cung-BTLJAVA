package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.LichHen;
import com.cuahangthucung.entity.use.enums.TrangThai;
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

    // Tìm lịch hẹn theo khách hàng
    List<LichHen> findByMaKH(String maKH);

    // Tìm lịch hẹn theo thú cưng
    List<LichHen> findByMaPet(String maPet);

    // Tìm lịch hẹn theo nhân viên
    List<LichHen> findByMaNV(String maNV);

    // Tìm lịch hẹn theo dịch vụ
    List<LichHen> findByDichVu_MaDV(String maDV);

    // Tìm lịch hẹn theo trạng thái
    List<LichHen> findByTrangThai(TrangThai trangThai);

    // Tìm lịch hẹn của khách hàng theo trạng thái
    List<LichHen> findByMaKHAndTrangThai(String maKH, TrangThai trangThai);

    // Tìm lịch hẹn của nhân viên theo trạng thái
    List<LichHen> findByMaNVAndTrangThai(String maNV, TrangThai trangThai);

    // Tìm lịch hẹn trong khoảng thời gian
    List<LichHen> findByThoiGianBetween(LocalDateTime tuThoiGian, LocalDateTime denThoiGian);

    // Đếm lịch hẹn theo trạng thái
    long countByTrangThai(TrangThai trangThai);

    // Đếm lịch hẹn theo nhân viên
    long countByMaNV(String maNV);

    // Đếm lịch hẹn theo khách hàng
    long countByMaKH(String maKH);

    // Sinh ID tự động
    @Query("SELECT lh FROM LichHen lh WHERE lh.maLich LIKE :prefix% ORDER BY lh.maLich DESC LIMIT 1")
    Optional<LichHen> findLastLichHenByPrefix(@Param("prefix") String prefix);
}