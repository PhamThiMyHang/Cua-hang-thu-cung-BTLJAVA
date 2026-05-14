package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.service.base.BaseService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ChamCongService extends BaseService<ChamCong, Integer> {

    Optional<ChamCong> findByNhanVienMaNVAndNgay(Integer maNV, LocalDate ngay);
    List<ChamCong> findByNhanVienMaNV(Integer maNV);
    List<ChamCong> findByNgayBetween(LocalDate fromDate, LocalDate toDate);
}