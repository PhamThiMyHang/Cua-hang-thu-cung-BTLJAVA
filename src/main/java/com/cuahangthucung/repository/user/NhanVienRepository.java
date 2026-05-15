package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.entity.user.enums.ChucVu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Integer>, JpaSpecificationExecutor<NhanVien> {

    boolean existsBySdt(String sdt);

    @Query("SELECT COUNT(n) FROM NhanVien n")
    Long countTotalNhanVien();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.chucVu = 'STAFF'")
    Long countStaff();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.chucVu = 'KTV'")
    Long countKTV();

    @Query("SELECT COUNT(n) FROM NhanVien n WHERE n.user IS NOT NULL")
    Long countNhanVienCoTaiKhoan();
}