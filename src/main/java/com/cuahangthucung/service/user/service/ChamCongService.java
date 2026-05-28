package com.cuahangthucung.service.user.service;

import com.cuahangthucung.dto.user.chamcong.ChamCongDTO;
import com.cuahangthucung.dto.user.chamcong.ChamCongRequest;
import com.cuahangthucung.dto.user.chamcong.ChamCongSearchRequest;
import com.cuahangthucung.dto.user.chamcong.ChamCongSummaryDTO;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ChamCongService extends BaseService<ChamCong, Integer> {

    List<ChamCongDTO> search(ChamCongSearchRequest request);
    
    // Phiên bản có phân trang
    Page<ChamCongDTO> search(ChamCongSearchRequest request, Pageable pageable);

    ChamCongDTO saveRequest(ChamCongRequest request);
    ChamCongDTO findByIdDTO(Integer id);
    List<ChamCongDTO> findAllDTO();
    ChamCongSummaryDTO getSummary(ChamCongSearchRequest request);
    ChamCongDTO convertToDTO(ChamCong entity);
}