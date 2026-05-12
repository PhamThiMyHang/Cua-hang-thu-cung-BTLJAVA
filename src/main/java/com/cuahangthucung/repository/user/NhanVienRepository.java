package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository cho bảng NHANVIEN
 */
@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>, JpaSpecificationExecutor<NhanVien> {

    Optional<NhanVien> findByUserUserID(Integer userId);

    Optional<NhanVien> findBySdt(String sdt);

    boolean existsBySdt(String sdt);

    @Query("SELECT n FROM NhanVien n WHERE n.maNV LIKE :prefix% ORDER BY n.maNV DESC LIMIT 1")
    Optional<NhanVien> findLastNhanVienByPrefix(@Param("prefix") String prefix);
}