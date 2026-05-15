package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.KPIThuongPhat;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface KPIThuongPhatService extends BaseService<KPIThuongPhat, Integer> {
    List<KPIThuongPhatDTO> search(KPIThuongPhatSearchRequest request);
    KPIThuongPhatDTO saveRequest(KPIThuongPhatRequest request);
    KPIThuongPhatDTO findByIdDTO(Integer id);
    List<KPIThuongPhatDTO> findAllDTO();
    KPIThuongPhatSummaryDTO getSummary(String thang);
}