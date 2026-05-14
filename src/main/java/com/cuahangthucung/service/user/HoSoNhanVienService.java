package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.HoSoNhanVien;
import com.cuahangthucung.service.base.BaseService;

import java.util.Optional;

public interface HoSoNhanVienService extends BaseService<HoSoNhanVien, Integer> {

    Optional<HoSoNhanVien> findByNhanVienMaNV(Integer maNV);
    boolean existsByNhanVienMaNV(Integer maNV);
}