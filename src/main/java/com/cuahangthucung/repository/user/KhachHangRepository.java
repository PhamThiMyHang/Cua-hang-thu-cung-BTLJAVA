package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.entity.user.enums.LoaiKH;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface KhachHangRepository extends JpaRepository<KhachHang, Integer>, JpaSpecificationExecutor<KhachHang> {

    boolean existsBySdt(String sdt);
/* bỏ
    @Query("SELECT k FROM KhachHang k ORDER BY k.maKH DESC LIMIT 1")
    Optional<KhachHang> findLastKhachHang();
*/
    /*  Pham Thi My Hang sua0*/
    // Sửa lại hàm này: Lấy khách hàng có ID (Integer) cao nhất
    @Query("SELECT k FROM KhachHang k ORDER BY k.maKH DESC LIMIT 1")
    Optional<KhachHang> findLastKhachHang();

    @Query("SELECT COUNT(k) FROM KhachHang k")
    Long countTotalKhachHang();

    @Query("SELECT COUNT(k) FROM KhachHang k WHERE k.loaiKH = 'VIP'")
    Long countKhachVIP();

    @Query("SELECT SUM(k.diemTichLuy) FROM KhachHang k")
    Integer sumDiemTichLuy();
}