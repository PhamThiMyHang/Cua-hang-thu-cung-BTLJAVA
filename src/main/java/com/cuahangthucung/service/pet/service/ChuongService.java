package com.cuahangthucung.service.pet.service;
import com.cuahangthucung.dto.pet.chuong.ChuongDTO;
import com.cuahangthucung.dto.pet.chuong.ChuongRequest;
import com.cuahangthucung.dto.pet.chuong.ChuongSearchRequest;
import com.cuahangthucung.dto.pet.chuong.ChuongSummaryDTO;
import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface ChuongService extends BaseService<Chuong, String> {
    // Tìm kiếm theo bộ lọc Specification
    List<ChuongDTO> search(ChuongSearchRequest request);

    // Lưu từ Request (Dùng chung cho cả Add/Update)
    ChuongDTO saveRequest(ChuongRequest request);

    ChuongDTO findByIdDTO(String id);

    // Lấy thống kê tổng hợp                                                            mn
    ChuongSummaryDTO getSummary();
    List<ChuongDTO> findAllDTO();
    ChuongDTO convertToDTO(Chuong chuong);
}

