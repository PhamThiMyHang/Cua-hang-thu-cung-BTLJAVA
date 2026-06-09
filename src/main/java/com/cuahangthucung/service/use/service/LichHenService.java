package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.lichhen.*;
import com.cuahangthucung.entity.use.entity.LichHen;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

import java.util.List;

public interface LichHenService extends BaseService<LichHen, String> {
    Page<LichHenDTO> search(LichHenSearchRequest request, Pageable pageable);
    List<LichHenDTO> findAllDTO();
    LichHenDTO findByIdDTO(String id);
    LichHenDTO saveRequest(LichHenRequest request);
    BigDecimal tongDoanhThu();

    List<DoanhThuNhanVienDTO> thongKeDoanhThuNhanVien();
    DoanhThuNhanVienDTO thongKeDoanhThuNhanVien(Integer maNV);
    LichHenSummaryDTO getSummary();
}