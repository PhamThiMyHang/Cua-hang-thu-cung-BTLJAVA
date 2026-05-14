package com.cuahangthucung.repository.use;

import com.cuahangthucung.entity.use.entity.ChiTietDonHang;
import com.cuahangthucung.entity.use.entity.ChiTietDonHangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng CHITIETDONHANG - Khóa chính kép (MaDH, MaSP)
 */
@Repository
public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, ChiTietDonHangId>, JpaSpecificationExecutor<ChiTietDonHang> {

    // Lấy tất cả chi tiết của một đơn hàng
    List<ChiTietDonHang> findById_MaDH(String maDH);

    // Lấy tất cả chi tiết theo mã sản phẩm
    List<ChiTietDonHang> findById_MaSP(String maSP);

    // Xóa toàn bộ chi tiết của một đơn hàng
    void deleteById_MaDH(String maDH);

    // Tính tổng tiền của một đơn hàng
    @Query("SELECT SUM(ct.soLuong * ct.donGia) FROM ChiTietDonHang ct WHERE ct.id.maDH = :maDH")
    Double tinhTongTienDonHang(@Param("maDH") String maDH);

    // Đếm số đơn hàng đã bán sản phẩm cụ thể
    long countById_MaSP(String maSP);
}