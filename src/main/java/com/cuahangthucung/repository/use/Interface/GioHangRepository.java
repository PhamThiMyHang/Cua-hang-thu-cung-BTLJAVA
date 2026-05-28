package com.cuahangthucung.repository.use.Interface;


import com.cuahangthucung.entity.use.entity.GioHang;
import com.cuahangthucung.entity.use.entity.GioHangId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, GioHangId>,
        JpaSpecificationExecutor<GioHang> {

    // Tìm tất cả sản phẩm trong một giỏ hàng cụ thể
    List<GioHang> findByIdMaGioHang(String maGioHang);

    // Tìm tất cả giỏ hàng của một người dùng
    List<GioHang> findByUserUserID(Integer userID);

    // Tìm một item cụ thể trong giỏ hàng
    Optional<GioHang> findByIdMaGioHangAndIdMaSP(String maGioHang, String maSP);

    // Kiểm tra sản phẩm đã có trong giỏ của user chưa
    boolean existsByUserUserIDAndIdMaSP(Integer userID, String maSP);

    // Đếm số lượng sản phẩm trong giỏ của user
    @Query("SELECT COALESCE(SUM(g.soLuong), 0) FROM GioHang g WHERE g.user.userID = :userID")
    Integer countSoLuongSanPhamTrongGio(@Param("userID") Integer userID);

    // Tính tổng tiền của một giỏ hàng
    @Query("SELECT COALESCE(SUM(g.soLuong * s.gia), 0) FROM GioHang g JOIN g.sanPham s WHERE g.id.maGioHang = :maGioHang")
    BigDecimal tinhTongTienGioHang(@Param("maGioHang") String maGioHang);

    // Tính tổng giá trị tất cả giỏ hàng (cho dashboard)
    @Query("SELECT COALESCE(SUM(g.soLuong * s.gia), 0) FROM GioHang g JOIN g.sanPham s")
    BigDecimal tinhTongGiaTriTatCaGioHang();

    // Đếm số user đang có giỏ hàng
    @Query("SELECT COUNT(DISTINCT g.user.userID) FROM GioHang g")
    Long countSoUserDangCoGioHang();
}