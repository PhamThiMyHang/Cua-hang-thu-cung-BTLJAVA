package com.cuahangthucung.service.user.service;

import com.cuahangthucung.dto.user.KPIThuongPhat.KPIThuongPhatDTO;
import com.cuahangthucung.dto.user.KPIThuongPhat.KPIThuongPhatRequest;
import com.cuahangthucung.dto.user.KPIThuongPhat.KPIThuongPhatSearchRequest;
import com.cuahangthucung.dto.user.KPIThuongPhat.KPIThuongPhatSummaryDTO;
import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KPIThuongPhatService extends BaseService<KPIThuongPhat, Integer> {

    List<KPIThuongPhatDTO> search(KPIThuongPhatSearchRequest request);
    
    // Phiên bản có phân trang
    Page<KPIThuongPhatDTO> search(KPIThuongPhatSearchRequest request, Pageable pageable);

    KPIThuongPhatDTO saveRequest(KPIThuongPhatRequest request);
    KPIThuongPhatDTO findByIdDTO(Integer id);
    List<KPIThuongPhatDTO> findAllDTO();
    KPIThuongPhatSummaryDTO getSummary(String thang);
}