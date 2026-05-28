package com.cuahangthucung.service.use.service;

import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapDTO;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapRequest;
import com.cuahangthucung.dto.use.nhacungcap.NhaCungCapSearchRequest;
import com.cuahangthucung.entity.use.entity.NhaCungCap;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NhaCungCapService extends BaseService<NhaCungCap, String> {
    Page<NhaCungCapDTO> search(NhaCungCapSearchRequest request, Pageable pageable);
    List<NhaCungCapDTO> findAllDTO();
    NhaCungCapDTO findByIdDTO(String id);
    NhaCungCapDTO saveRequest(NhaCungCapRequest request);
}