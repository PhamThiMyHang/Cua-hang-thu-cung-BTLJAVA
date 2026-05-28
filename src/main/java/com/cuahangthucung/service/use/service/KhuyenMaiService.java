package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiDTO;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiRequest;
import com.cuahangthucung.dto.use.khuyenmai.KhuyenMaiSearchRequest;
import com.cuahangthucung.entity.use.entity.KhuyenMai;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface KhuyenMaiService extends BaseService<KhuyenMai, String> {
    Page<KhuyenMaiDTO> search(KhuyenMaiSearchRequest request, Pageable pageable);
    List<KhuyenMaiDTO> findAllDTO();
    KhuyenMaiDTO findByIdDTO(String id);
    KhuyenMaiDTO saveRequest(KhuyenMaiRequest request);
}