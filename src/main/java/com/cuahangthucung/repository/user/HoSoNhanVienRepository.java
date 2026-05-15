package com.cuahangthucung.repository.user;

import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HoSoNhanVienRepository extends JpaRepository<HoSoNhanVien, Integer>, JpaSpecificationExecutor<HoSoNhanVien> {

    boolean existsByNhanVien_MaNV(Integer maNV);

    @Query("SELECT h FROM HoSoNhanVien h WHERE h.nhanVien.maNV = :maNV")
    HoSoNhanVien findByMaNV(Integer maNV);
}