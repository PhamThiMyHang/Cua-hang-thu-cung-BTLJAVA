package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.dichvu.DichVuDTO;
import com.cuahangthucung.dto.use.dichvu.DichVuRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSearchRequest;
import com.cuahangthucung.dto.use.dichvu.DichVuSummaryDTO;
import com.cuahangthucung.entity.use.entity.DichVu;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DichVuService extends BaseService<DichVu, String> {

    // ĐÃ SỬA: Thay đổi kiểu trả về thành Page và thêm Pageable
    Page<DichVuDTO> search(DichVuSearchRequest request, Pageable pageable);

    DichVuDTO saveRequest(DichVuRequest request);

    DichVuDTO findByIdDTO(String id);

    List<DichVuDTO> findAllDTO();

    DichVuSummaryDTO getSummary();

    String generateNextMaDV();

    boolean isTenDVExists(String tenDV);

    List<DichVuDTO> findByGiaRange(double giaMin, double giaMax);
}