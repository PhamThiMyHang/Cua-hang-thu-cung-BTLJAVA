package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.service.base.BaseService;

import java.time.LocalDate;
import java.util.List;

public interface LichTrucService extends BaseService<LichTruc, Integer> {

    List<LichTruc> findByNhanVienMaNV(Integer maNV);
    List<LichTruc> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay);
    List<LichTruc> findByNgayBetween(LocalDate fromDate, LocalDate toDate);
}