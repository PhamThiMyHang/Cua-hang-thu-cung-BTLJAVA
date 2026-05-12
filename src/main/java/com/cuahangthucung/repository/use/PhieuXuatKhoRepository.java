package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.PhieuXuatKho;
import com.cuahangthucung.entity.use.entity.PhieuXuatKho.PhieuXuatKhoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng PHIEUXUATKHO - Khóa chính kép (MaPhieu, MaSP)
 */
@Repository
public interface PhieuXuatKhoRepository extends JpaRepository<PhieuXuatKho, PhieuXuatKhoId>, JpaSpecificationExecutor<PhieuXuatKho> {

    // Tìm phiếu xuất theo mã phiếu
    List<PhieuXuatKho> findById_MaPhieu(String maPhieu);

    // Tìm phiếu xuất theo sản phẩm
    List<PhieuXuatKho> findById_MaSP(String maSP);

    // Tìm phiếu xuất theo nhân viên
    List<PhieuXuatKho> findByMaNV(String maNV);

    // Tìm phiếu xuất theo nơi nhận gần đúng
    List<PhieuXuatKho> findByNoiNhanContainingIgnoreCase(String noiNhan);

    // Tìm phiếu xuất theo ngày xuất cụ thể
    List<PhieuXuatKho> findByNgayXuat(LocalDate ngayXuat);

    // Tìm phiếu xuất trong khoảng thời gian
    List<PhieuXuatKho> findByNgayXuatBetween(LocalDate tuNgay, LocalDate denNgay);

    // Tính tổng số lượng đã xuất theo sản phẩm
    @Query("SELECT SUM(px.soLuong) FROM PhieuXuatKho px WHERE px.id.maSP = :maSP")
    Long tinhTongSoLuongXuatTheoSP(@Param("maSP") String maSP);

    // Đếm số phiếu xuất theo nhân viên
    long countByMaNV(String maNV);

    // Sinh ID tự động
    @Query("SELECT px FROM PhieuXuatKho px WHERE px.id.maPhieu LIKE :prefix% ORDER BY px.id.maPhieu DESC LIMIT 1")
    Optional<PhieuXuatKho> findLastPhieuXuatByPrefix(@Param("prefix") String prefix);
}