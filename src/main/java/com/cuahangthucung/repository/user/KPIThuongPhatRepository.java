package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface KPIThuongPhatRepository extends JpaRepository<KPIThuongPhat, Integer>, JpaSpecificationExecutor<KPIThuongPhat> {

    @Query("SELECT SUM(k.thuong) FROM KPIThuongPhat k WHERE k.thang = :thang")
    BigDecimal sumThuongByThang(@Param("thang") String thang);

    @Query("SELECT SUM(k.phat) FROM KPIThuongPhat k WHERE k.thang = :thang")
    BigDecimal sumPhatByThang(@Param("thang") String thang);

    boolean existsByNhanVien_MaNVAndThang(Integer maNV, String thang);
}