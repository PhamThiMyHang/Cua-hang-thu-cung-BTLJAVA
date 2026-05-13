package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.PhieuNhapKho;
import com.cuahangthucung.entity.use.entity.PhieuNhapKho.PhieuNhapKhoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Repository cho bảng PHIEUNHAPKHO - Khóa chính kép (MaPhieu, MaSP)
 */
@Repository
public interface PhieuNhapKhoRepository extends JpaRepository<PhieuNhapKho, PhieuNhapKhoId>, JpaSpecificationExecutor<PhieuNhapKho> {

    // Tìm phiếu nhập theo mã phiếu
    List<PhieuNhapKho> findById_MaPhieu(String maPhieu);

    // Tìm phiếu nhập theo sản phẩm
    List<PhieuNhapKho> findById_MaSP(String maSP);

    // Tìm phiếu nhập theo ngày nhập cụ thể
    List<PhieuNhapKho> findByNgayNhap(LocalDate ngayNhap);

    // Tìm phiếu nhập trong khoảng thời gian
    List<PhieuNhapKho> findByNgayNhapBetween(LocalDate tuNgay, LocalDate denNgay);

    // Tính tổng số lượng đã nhập theo sản phẩm
    @Query("SELECT SUM(pn.soLuong) FROM PhieuNhapKho pn WHERE pn.id.maSP = :maSP")
    Long tinhTongSoLuongNhapTheoSP(@Param("maSP") String maSP);

    // Sinh ID tự động (prefix theo tháng/năm, VD: PN2504)
    @Query("SELECT pn FROM PhieuNhapKho pn WHERE pn.id.maPhieu LIKE :prefix% ORDER BY pn.id.maPhieu DESC LIMIT 1")
    Optional<PhieuNhapKho> findLastPhieuNhapByPrefix(@Param("prefix") String prefix);
}