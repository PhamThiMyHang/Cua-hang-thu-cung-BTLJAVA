package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.LichSuDangNhap;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface LichSuDangNhapService extends BaseService<LichSuDangNhap, Integer> {

    List<LichSuDangNhap> findByUserUserID(Integer userId);
    List<LichSuDangNhap> findTop10ByUserUserIDOrderByThoiGianDesc(Integer userId);
}