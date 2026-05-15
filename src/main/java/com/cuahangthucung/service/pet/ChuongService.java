package com.cuahangthucung.service.pet;
import com.cuahangthucung.dto.pet.*; // Import đúng package DTO của Chuong
import com.cuahangthucung.dto.pet.ChuongDTO;
import com.cuahangthucung.dto.pet.ChuongRequest;
import com.cuahangthucung.dto.pet.ChuongSearchRequest;
import com.cuahangthucung.dto.pet.ChuongSummaryDTO;
import com.cuahangthucung.entity.pet.entity.Chuong;
import com.cuahangthucung.service.base.BaseService;

import java.util.List;

public interface ChuongService extends BaseService<Chuong, String> {
    // Tìm kiếm theo bộ lọc Specification
    List<ChuongDTO> search(ChuongSearchRequest request);

    // Lưu từ Request (Dùng chung cho cả Add/Update)
    ChuongDTO saveRequest(ChuongRequest request);

    ChuongDTO findByIdDTO(String id);

    // Lấy thống kê tổng hợp
    ChuongSummaryDTO getSummary();
    List<ChuongDTO> findAllDTO();
}

