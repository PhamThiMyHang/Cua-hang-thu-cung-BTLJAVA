package com.cuahangthucung.repository.use.Interface;

import com.cuahangthucung.entity.use.entity.ChiTietDonHang;
import com.cuahangthucung.entity.use.entity.ChiTietDonHangId;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, ChiTietDonHangId>, JpaSpecificationExecutor<ChiTietDonHang> {

    // Tìm kiếm bằng cách duyệt qua thuộc tính đối tượng quan hệ
    List<ChiTietDonHang> findByDonHang_MaDH(String maDH);

    List<ChiTietDonHang> findBySanPham_MaSP(String maSP);

    @Modifying
    @Transactional
    void deleteByDonHang_MaDH(String maDH);

    // Thay thế ct.id.maDH bằng ct.donHang.maDH cho đúng chuẩn IdClass
    @Query("SELECT SUM(ct.soLuong * ct.donGia) FROM ChiTietDonHang ct WHERE ct.donHang.maDH = :maDH")
    BigDecimal tinhTongTienDonHang(@Param("maDH") String maDH);

    long countBySanPham_MaSP(String maSP);
}