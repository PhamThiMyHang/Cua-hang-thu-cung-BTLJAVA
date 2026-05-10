package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository cho bảng KPI_THUONGPHAT
 */
@Repository
public interface KPIThuongPhatRepository extends JpaRepository<KPIThuongPhat, Integer>, JpaSpecificationExecutor<KPIThuongPhat> {

    List<KPIThuongPhat> findByNhanVienMaNV(Integer maNV);

    List<KPIThuongPhat> findByNhanVienMaNVAndThang(Integer maNV, String thang);
}