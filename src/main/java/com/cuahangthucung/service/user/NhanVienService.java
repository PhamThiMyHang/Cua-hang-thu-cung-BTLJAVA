package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.NhanVien;
import com.cuahangthucung.service.base.BaseService;

import java.util.Optional;

public interface NhanVienService extends BaseService<NhanVien, Integer> {

    Optional<NhanVien> findByUserUserID(Integer userId);
    Optional<NhanVien> findBySdt(String sdt);
    boolean existsBySdt(String sdt);
    String generateNextMaNV();
}