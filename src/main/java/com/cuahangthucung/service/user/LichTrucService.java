package com.cuahangthucung.service.user;

import com.cuahangthucung.dto.user.*;
import com.cuahangthucung.entity.user.entity.LichTruc;
import com.cuahangthucung.service.base.BaseService;
import java.util.List;

public interface LichTrucService extends BaseService<LichTruc, Integer> {
    List<LichTrucDTO> search(LichTrucSearchRequest request);
    LichTrucDTO saveRequest(LichTrucRequest request);
    LichTrucDTO findByIdDTO(Integer id);
    List<LichTrucDTO> findAllDTO();
}