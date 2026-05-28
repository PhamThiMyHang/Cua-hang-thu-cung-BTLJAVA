package com.cuahangthucung.repository.user.Interface;

import com.cuahangthucung.entity.user.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>, JpaSpecificationExecutor<NhanVien> {

    boolean existsBySdt(String sdt);

    // ==================== BỔ SUNG DÒNG NÀY ====================
    boolean existsByGmail(String gmail);

    // Lấy nhân viên có ID lớn nhất
    @Query("SELECT n FROM NhanVien n ORDER BY n.maNV DESC LIMIT 1")
    Optional<NhanVien> findLastNhanVien();

    // Các hàm thống kê cho NhanVienSummaryDTO
    @Query("SELECT COUNT(n) FROM NhanVien n")
    Long countTotalNhanVien();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.chucVu = 'STAFF'")
    Long countStaff();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.chucVu = 'KTV'")
    Long countKTV();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.user IS NOT NULL")
    Long countNhanVienCoTaiKhoan();
}