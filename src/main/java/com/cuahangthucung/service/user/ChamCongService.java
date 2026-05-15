package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.ChamCong;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface ChamCongService extends BaseService<ChamCong, Integer> {
    List<ChamCongDTO> search(ChamCongSearchRequest request);
    ChamCongDTO saveRequest(ChamCongRequest request);
    ChamCongDTO findByIdDTO(Integer id);
    List<ChamCongDTO> findAllDTO();
    ChamCongSummaryDTO getSummary(ChamCongSearchRequest request);
}