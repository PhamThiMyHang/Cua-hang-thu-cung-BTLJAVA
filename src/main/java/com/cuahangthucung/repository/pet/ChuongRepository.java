
package com.cuahangthucung.repository.pet;

import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.entity.pet.enums.TrangThaiChuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChuongRepository extends JpaRepository<Chuong, String>, JpaSpecificationExecutor<Chuong> {
    // Tất cả các chức năng tìm kiếm (1, 2, 3) và tính toán (4, 5)
    // đều sẽ được xử lý thông qua Specification tại tầng Service.
    // 1. Đếm tổng số chuồng theo trạng thái (TRONG, KIN, SUA_CHUA)
    @Query("SELECT COUNT(c) FROM Chuong c WHERE c.trangThai = :status")
    Long countByTrangThai(@Param("status") TrangThaiChuong status);

    // 2. Thống kê số lượng chuồng theo từng Loại Chuồng (VIP, Thường...)
    // Trả về List các mảng Object: [Tên loại, Số lượng]
    @Query("SELECT c.loaiChuong.tenLoai, COUNT(c) FROM Chuong c GROUP BY c.loaiChuong.tenLoai")
    List<Object[]> countByLoaiChuongGrouped();

    // 3. (Tùy chọn) Tìm các chuồng còn trống thuộc một loại cụ thể
    @Query("SELECT c FROM Chuong c WHERE c.trangThai = 'TRONG' AND c.loaiChuong.maLoaiChuong = :maLoai")
    List<Chuong> findAvailableByLoai(@Param("maLoai") String maLoai);
}
