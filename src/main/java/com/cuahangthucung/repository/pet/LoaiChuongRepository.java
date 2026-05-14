package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.LoaiChuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface LoaiChuongRepository extends JpaRepository<LoaiChuong, String>, JpaSpecificationExecutor<LoaiChuong> {

    // 1. Tính giá thuê trung bình của các loại chuồng
    @Query("SELECT AVG(lc.giaThue) FROM LoaiChuong lc")
    BigDecimal getAverageGiaThue();

    // 2. Tính tổng sức chứa (tổng cột soLuong) của toàn hệ thống
    @Query("SELECT SUM(lc.soLuong) FROM LoaiChuong lc")
    Long getTotalSystemCapacity();

    // 3. Tìm tên loại chuồng có số lượng thiết kế lớn nhất
    @Query(value = "SELECT TenLoai FROM LOAICHUONG ORDER BY SoLuong DESC LIMIT 1", nativeQuery = true)
    Optional<String> getNameOfMostPopularType();

}