package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.service.base.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LichTrucService extends BaseService<LichTruc, Integer> {

    List<LichTrucDTO> search(LichTrucSearchRequest request);
    
    // Phiên bản có phân trang + sắp xếp
    Page<LichTrucDTO> search(LichTrucSearchRequest request, Pageable pageable);

    LichTrucDTO saveRequest(LichTrucRequest request);
    LichTrucDTO findByIdDTO(Integer id);
    List<LichTrucDTO> findAllDTO();
}