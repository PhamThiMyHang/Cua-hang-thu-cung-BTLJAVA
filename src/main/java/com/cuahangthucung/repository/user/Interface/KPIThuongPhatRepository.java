package com.cuahangthucung.repository.user.Interface;

import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface KPIThuongPhatRepository extends JpaRepository<KPIThuongPhat, Integer>, JpaSpecificationExecutor<KPIThuongPhat> {

    // ĐÃ SỬA LỖI: Dùng COALESCE để chuyển null thành 0, tránh NullPointerException khi tính toán
    @Query("SELECT COALESCE(SUM(k.thuong), 0) FROM KPIThuongPhat k WHERE k.thang = :thang")
    BigDecimal sumThuongByThang(@Param("thang") String thang);

    // ĐÃ SỬA LỖI: Dùng COALESCE để chuyển null thành 0, tránh NullPointerException khi tính toán
    @Query("SELECT COALESCE(SUM(k.phat), 0) FROM KPIThuongPhat k WHERE k.thang = :thang")
    BigDecimal sumPhatByThang(@Param("thang") String thang);

    boolean existsByNhanVien_MaNVAndThang(Integer maNV, String thang);
}