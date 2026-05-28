package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.PhieuXuatKho;
import com.cuahangthucung.entity.use.entity.PhieuXuatKhoId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuXuatKhoRepository extends JpaRepository<PhieuXuatKho, PhieuXuatKhoId>, JpaSpecificationExecutor<PhieuXuatKho> {

    // Sửa lỗi tìm kiếm theo cấu trúc IdClass (trường đối tượng là sanPham)
    List<PhieuXuatKho> findByMaPhieu(String maPhieu);

    List<PhieuXuatKho> findBySanPham_MaSP(String maSP);

    // Sửa lỗi tìm theo thuộc tính của object liên kết NhanVien
    List<PhieuXuatKho> findByNhanVien_MaNV(Integer maNV);

    List<PhieuXuatKho> findByNoiNhanContainingIgnoreCase(String noiNhan);

    List<PhieuXuatKho> findByNgayXuat(LocalDate ngayXuat);

    List<PhieuXuatKho> findByNgayXuatBetween(LocalDate tuNgay, LocalDate denNgay);

    @Query("SELECT SUM(px.soLuong) FROM PhieuXuatKho px WHERE px.sanPham.maSP = :maSP")
    Long tinhTongSoLuongXuatTheoSP(@Param("maSP") String maSP);

    @Query("SELECT COUNT(px) FROM PhieuXuatKho px WHERE px.nhanVien.maNV = :maNV")
    long countByMaNV(@Param("maNV") String maNV);

    @Query("SELECT px FROM PhieuXuatKho px WHERE px.maPhieu LIKE :prefix% ORDER BY px.maPhieu DESC LIMIT 1")
    Optional<PhieuXuatKho> findLastPhieuXuatByPrefix(@Param("prefix") String prefix);
}