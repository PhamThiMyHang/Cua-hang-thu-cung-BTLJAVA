package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.PhieuNhapKho;
import com.cuahangthucung.entity.use.entity.PhieuNhapKhoId; // Đảm bảo import đúng class Id
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuNhapKhoRepository extends JpaRepository<PhieuNhapKho, PhieuNhapKhoId>, JpaSpecificationExecutor<PhieuNhapKho> {

    List<PhieuNhapKho> findByMaPhieu(String maPhieu);

    List<PhieuNhapKho> findBySanPham_MaSP(String maSP);

    List<PhieuNhapKho> findByNgayNhap(LocalDate ngayNhap);

    List<PhieuNhapKho> findByNgayNhapBetween(LocalDate tuNgay, LocalDate denNgay);

    @Query("SELECT SUM(pn.soLuong) FROM PhieuNhapKho pn WHERE pn.sanPham.maSP = :maSP")
    Long tinhTongSoLuongNhapTheoSP(@Param("maSP") String maSP);

    // Bỏ ".id" đi vì @IdClass phẳng hóa các trường trực tiếp trên Entity
    @Query("SELECT pn FROM PhieuNhapKho pn WHERE pn.maPhieu LIKE :prefix% ORDER BY pn.maPhieu DESC LIMIT 1")
    Optional<PhieuNhapKho> findLastPhieuNhapByPrefix(@Param("prefix") String prefix);
}