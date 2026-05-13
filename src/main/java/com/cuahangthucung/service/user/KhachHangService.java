package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.KhachHang;
import com.cuahangthucung.service.base.BaseService;

import java.util.Optional;

public interface KhachHangService extends BaseService<KhachHang, Integer> {

    Optional<KhachHang> findByUserUserID(Integer userId);
    Optional<KhachHang> findBySdt(String sdt);
    boolean existsBySdt(String sdt);
    String generateNextMaKH();
}