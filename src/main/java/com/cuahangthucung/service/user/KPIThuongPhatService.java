package com.cuahangthucung.service.user;

import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface KPIThuongPhatService extends BaseService<KPIThuongPhat, Integer> {

    List<KPIThuongPhat> findByNhanVienMaNV(Integer maNV);
    List<KPIThuongPhat> findByNhanVienMaNVAndThang(Integer maNV, String thang);
}